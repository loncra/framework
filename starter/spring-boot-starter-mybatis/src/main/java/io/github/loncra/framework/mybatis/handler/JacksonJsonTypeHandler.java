package io.github.loncra.framework.mybatis.handler;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.exception.SystemException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * jackson json type handler 实现
 *
 * @param <T> json 实体类型 class
 *
 * @author maurice.chen
 */
public class JacksonJsonTypeHandler<T> extends BaseTypeHandler<T> {

    /**
     * JSON 实体类型
     */
    private final Class<T> type;

    /**
     * 构造函数
     *
     * @param type JSON 实体类型
     */
    public JacksonJsonTypeHandler(Class<T> type) {
        this.type = type;
    }

    /**
     * 设置非空参数到 PreparedStatement
     *
     * @param ps PreparedStatement
     * @param i 参数索引
     * @param parameter 参数值
     * @param jdbcType JDBC 类型
     * @throws SQLException SQL 异常
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        if (Collection.class.isAssignableFrom(parameter.getClass())) {
            Collection<?> collection = CastUtils.cast(parameter);
            List<Object> nameValueEnums = collection
                    .stream()
                    .map(NameValueEnumTypeHandler::getEnumValue)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (!nameValueEnums.isEmpty()) {
                ps.setString(i, SystemException.convertSupplier(() -> CastUtils.getObjectMapper().writeValueAsString(nameValueEnums)));
            } else {
                ps.setString(i, SystemException.convertSupplier(() -> CastUtils.getObjectMapper().writeValueAsString(parameter)));
            }
        } else {
            Object nameValueEnum = NameValueEnumTypeHandler.getEnumValue(parameter);
            if (Objects.nonNull(nameValueEnum)) {
                ps.setObject(i, nameValueEnum);
            } else {
                ps.setString(i, SystemException.convertSupplier(() -> CastUtils.getObjectMapper().writeValueAsString(parameter)));
            }
        }
    }



    /**
     * 从 ResultSet 中根据列名获取可空结果
     *
     * @param rs ResultSet
     * @param columnName 列名
     * @return 结果值
     * @throws SQLException SQL 异常
     */
    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        final String json = rs.getString(columnName);
        return getJsonValue(json);
    }

    /**
     * 从 ResultSet 中根据列索引获取可空结果
     *
     * @param rs ResultSet
     * @param columnIndex 列索引
     * @return 结果值
     * @throws SQLException SQL 异常
     */
    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        final String json = rs.getString(columnIndex);
        return getJsonValue(json);
    }

    /**
     * 从 CallableStatement 中根据列索引获取可空结果
     *
     * @param cs CallableStatement
     * @param columnIndex 列索引
     * @return 结果值
     * @throws SQLException SQL 异常
     */
    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        final String json = cs.getString(columnIndex);
        return getJsonValue(json);
    }

    /**
     * 从 JSON 字符串获取对象值
     *
     * @param json JSON 字符串
     * @return 对象值，如果 JSON 为空则返回 null
     */
    public T getJsonValue(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        } else if (Object.class.equals(type)) {
            return CastUtils.cast(json);
        } else {
            return SystemException.convertSupplier(() -> CastUtils.getObjectMapper().readValue(json, type));
        }
    }
}
