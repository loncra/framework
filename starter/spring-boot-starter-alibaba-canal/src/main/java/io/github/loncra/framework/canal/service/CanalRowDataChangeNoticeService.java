package io.github.loncra.framework.canal.service;

import com.alibaba.otter.canal.protocol.FlatMessage;
import io.github.loncra.framework.canal.domain.CanalMessage;
import io.github.loncra.framework.canal.domain.CanalRowDataChangeNotice;
import io.github.loncra.framework.canal.domain.entity.CanalRowDataChangeAckMessage;
import io.github.loncra.framework.commons.CastUtils;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

/**
 * canal 行数据变更通知服务
 *
 * @author maurice.chen
 */
public interface CanalRowDataChangeNoticeService {

    /**
     * HTTP 实体字段名
     */
    String HTTP_ENTITY_FIELD = "httpEntity";

    /**
     * 根据目标集合获取 canal 行数据变更实体集合
     *
     * @param destinations 数据库名.表明的目标信息
     *
     * @return canal 行数据变更通知实体集合
     */
    List<CanalRowDataChangeNotice> findEnableByDestinations(List<String> destinations);

    /**
     * 创建 canal 行数据变更通知记录实体
     *
     * @param notice  通知实体
     * @param message canal 消息
     *
     * @return canal 行变更通知记录实体集合
     */
    default List<CanalRowDataChangeAckMessage> createAckMessage(
            CanalRowDataChangeNotice notice,
            CanalMessage message
    ) {
        return new ArrayList<>(CanalRowDataChangeAckMessage.of(notice, message));
    }

    /**
     * 保存 canal 行数据变更通知实体
     *
     * @param ackMessage 确认消息接口
     *
     * @return 确认消息接口
     */
    CanalRowDataChangeAckMessage saveAckMessage(CanalRowDataChangeAckMessage ackMessage);

    /**
     * 发送 canal 行数据变更通知
     *
     * @param ackMessage 确认消息接口
     */
    void sendAckMessage(CanalRowDataChangeAckMessage ackMessage);

    /**
     * 映射字段，用于将表字段映射成其他字段名
     *
     * @param flatMessages  canal 表字段消息
     * @param fieldMappings 要映射的表和字段内容
     */
    default void mappingField(
            List<FlatMessage> flatMessages,
            Map<String, Map<String, String>> fieldMappings
    ) {
        for (FlatMessage flatMessage : flatMessages) {
            if (!fieldMappings.containsKey(flatMessage.getDatabase() + CastUtils.DOT + flatMessage.getTable())) {
                continue;
            }

            Map<String, String> fields = fieldMappings.get(flatMessage.getDatabase() + CastUtils.DOT + flatMessage.getTable());
            List<Map<String, String>> newData = mappingNewField(flatMessage.getData(), fields);
            if (CollectionUtils.isNotEmpty(newData)) {
                flatMessage.setData(newData);
            }

            List<Map<String, String>> newOldData = mappingNewField(flatMessage.getOld(), fields);
            if (CollectionUtils.isNotEmpty(newOldData)) {
                flatMessage.setOld(newOldData);
            }

            List<String> newPkNames = new LinkedList<>();

            for (String pkName : flatMessage.getPkNames()) {
                newPkNames.add(fields.getOrDefault(pkName, pkName));
            }
            flatMessage.setPkNames(newPkNames);
        }
    }

    /**
     * 映射新字段信息
     *
     * @param dataList canal 表数据信息
     * @param fields   字段信息
     *
     * @return 新的行数据变更结构
     */
    default List<Map<String, String>> mappingNewField(
            List<Map<String, String>> dataList,
            Map<String, String> fields
    ) {
        List<Map<String, String>> newData = new LinkedList<>();
        if (CollectionUtils.isEmpty(dataList)) {
            return newData;
        }

        for (Map<String, String> data : dataList) {
            Map<String, String> newValue = new LinkedHashMap<>();
            for (Map.Entry<String, String> entry : data.entrySet()) {
                // 获取 entry.getKey()，如果没有对应的字段映射，就用 entry.getKey() 值
                String field = fields.getOrDefault(entry.getKey(), entry.getKey());
                newValue.put(Objects.toString(field, entry.getKey()), entry.getValue());
            }
            newData.add(newValue);
        }

        return newData;
    }

}
