package io.github.loncra.framework.canal.domain;

import io.github.loncra.framework.commons.domain.metadata.ProtocolMetadata;
import io.github.loncra.framework.commons.enumerate.basic.YesOrNo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * canal 行变更通知接口
 *
 * @author maurice.chen
 */
public interface CanalRowDataChangeNotice extends Serializable, ProtocolMetadata {

    /**
     * 获取表名称
     *
     * @return 表名称
     */
    String getTableName();

    /**
     * 获取数据库名称
     *
     * @return 数据库名称
     */
    String getDatabaseName();

    /**
     * 获取字段映射，用于对象数据库表生成要发送数据结构使用
     *
     * @return 字段映射
     */
    Map<String, Map<String, String>> getFieldMappings();

    /**
     * 获取是否仅仅需要 regularExpressions 匹配的数据消息
     *
     * @return 是否仅仅需要 regularExpressions 匹配的数据消息
     */
    YesOrNo getFilterRegularExpressionsMessage();

    /**
     * 获取过滤数据库表的正则表达式
     *
     * @return 过滤数据库表的正则表达式
     */
    List<String> getRegularExpressions();
}
