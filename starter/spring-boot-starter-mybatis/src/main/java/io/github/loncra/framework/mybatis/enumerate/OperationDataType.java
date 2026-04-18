package io.github.loncra.framework.mybatis.enumerate;

import io.github.loncra.framework.commons.enumerate.NameValueEnum;
import org.apache.ibatis.mapping.SqlCommandType;

/**
 * 操作数据类型枚举
 *
 * @author maurice.chen
 */
public enum OperationDataType implements NameValueEnum<SqlCommandType> {

    /**
     * 新增
     */
    INSERT(SqlCommandType.INSERT, "新增"),

    /**
     * 更新
     */
    UPDATE(SqlCommandType.UPDATE, "更新"),

    /**
     * 删除
     */
    DELETE(SqlCommandType.DELETE, "删除"),

    ;

    /**
     * 操作类型值
     */
    private final SqlCommandType value;

    /**
     * 操作类型名称
     */
    private final String name;

    /**
     * 构造函数
     *
     * @param value 操作类型值
     * @param name  操作类型名称
     */
    OperationDataType(
            SqlCommandType value,
            String name
    ) {
        this.value = value;
        this.name = name;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public SqlCommandType getValue() {
        return value;
    }
}
