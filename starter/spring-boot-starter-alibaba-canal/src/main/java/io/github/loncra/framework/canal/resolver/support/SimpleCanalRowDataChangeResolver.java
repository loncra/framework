package io.github.loncra.framework.canal.resolver.support;

import com.alibaba.otter.canal.protocol.FlatMessage;
import io.github.loncra.framework.canal.domain.CanalMessage;
import io.github.loncra.framework.canal.domain.CanalRowDataChangeNotice;
import io.github.loncra.framework.canal.domain.entity.CanalRowDataChangeAckMessage;
import io.github.loncra.framework.canal.resolver.CanalRowDataChangeResolver;
import io.github.loncra.framework.canal.service.CanalRowDataChangeNoticeService;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.enumerate.basic.YesOrNo;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * canal 行数据变更通知解析器
 *
 * @author maurice.chen
 */
public class SimpleCanalRowDataChangeResolver implements CanalRowDataChangeResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleCanalRowDataChangeResolver.class);

    /**
     * canal 行谁变更通知服务
     */
    private CanalRowDataChangeNoticeService canalRowDataChangeNoticeService;

    public SimpleCanalRowDataChangeResolver(CanalRowDataChangeNoticeService canalRowDataChangeNoticeService) {
        this.canalRowDataChangeNoticeService = canalRowDataChangeNoticeService;
    }

    public SimpleCanalRowDataChangeResolver() {
    }

    @Override
    public void change(CanalMessage message) {
        // 获取消息里的目标结构，最终结果为:数据库名.表明
        List<String> destinations = new LinkedList<>();
        for (FlatMessage flatMessage : message.getFlatMessageList()) {
            String destination = flatMessage.getDatabase() + CastUtils.DOT + flatMessage.getTable();
            if (destinations.contains(destination)) {
                continue;
            }
            destinations.add(destination);
        }

        // 通过数据库名称.表名称查询启用的通知实体
        List<CanalRowDataChangeNotice> result = canalRowDataChangeNoticeService.findEnableByDestinations(destinations);

        List<CanalRowDataChangeAckMessage> recordList = new LinkedList<>();

        // 循环构造所有要发送的消息记录
        for (CanalRowDataChangeNotice notification : result) {
            // 克隆一次新的对象内容，防止某些通知将 message 原始数据修改。
            CanalMessage temp = CastUtils.of(message, CanalMessage.class);
            if (Objects.isNull(temp)) {
                LOGGER.warn("克隆 [{}] 数据成 temp 时出现 null 情况", message);
                continue;
            }

            temp.setFlatMessageList(temp.getFlatMessageList().stream().map(f -> CastUtils.of(f, FlatMessage.class)).collect(Collectors.toList()));

            // 如果需要过滤仅需要的数据，过滤数据
            if (YesOrNo.Yes.equals(notification.getFilterRegularExpressionsMessage())) {
                List<FlatMessage> filterMessage = temp
                        .getFlatMessageList()
                        .stream()
                        .filter(f -> notification.getRegularExpressions().stream().anyMatch(n -> Pattern.matches(n, f.getDatabase() + CastUtils.DOT + f.getTable())))
                        .collect(Collectors.toList());

                temp.setFlatMessageList(filterMessage);
            }

            // 如果需要进行字段映射，映射字段
            if (MapUtils.isNotEmpty(notification.getFieldMappings())) {
                List<FlatMessage> flatMessages = temp
                        .getFlatMessageList()
                        .stream()
                        .filter(f -> notification.getFieldMappings().containsKey(f.getDatabase() + CastUtils.DOT + f.getTable()))
                        .collect(Collectors.toList());

                canalRowDataChangeNoticeService.mappingField(flatMessages, notification.getFieldMappings());
            }

            recordList.addAll(canalRowDataChangeNoticeService.createAckMessage(notification, temp));

        }

        recordList
                .stream()
                .map(canalRowDataChangeNoticeService::saveAckMessage)
                .forEach(canalRowDataChangeNoticeService::sendAckMessage);
    }

    public CanalRowDataChangeNoticeService getCanalRowDataChangeNoticeService() {
        return canalRowDataChangeNoticeService;
    }

    public void setCanalRowDataChangeNoticeService(CanalRowDataChangeNoticeService canalRowDataChangeNoticeService) {
        this.canalRowDataChangeNoticeService = canalRowDataChangeNoticeService;
    }
}
