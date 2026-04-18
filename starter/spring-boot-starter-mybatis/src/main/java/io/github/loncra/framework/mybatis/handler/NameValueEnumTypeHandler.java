package io.github.loncra.framework.mybatis.handler;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.EnumUtils;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.enumerate.ValueEnum;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 带名称或值的枚举 type handler 实现
 *
 * @param <E> 枚举类型
 *
 * @author maurice.chen
 */
public class NameValueEnumTypeHandler<E extends Enum<E>> extends EnumTypeHandler<E> {

    /**
     * 枚举类型
     */
    private final Class<E> type;

    /**
     * 构造函数
     *
     * @param type 枚举类型
     */
    public NameValueEnumTypeHandler(Class<E> type) {
        super(type);
        this.type = type;
    }

    /**
     * 设置非空参数到 PreparedStatement
     *
     * @param ps       PreparedStatement
     * @param i        参数索引
     * @param type     枚举值
     * @param jdbcType JDBC 类型
     *
     * @throws SQLException SQL 异常
     */
    @Override
    public void setNonNullParameter(
            PreparedStatement ps,
            int i,
            E type,
            JdbcType jdbcType
    ) throws SQLException {
        Object value = getEnumValue(type);

        if (Objects.isNull(value)) {
            super.setNonNullParameter(ps, i, type, jdbcType);
            return;
        }

        if (jdbcType == null) {

            String stringValue = value.toString();
            if (!Number.class.isAssignableFrom(value.getClass()) && !String.class.isAssignableFrom(value.getClass())) {
                Enum<E> e = CastUtils.cast(type);
                stringValue = String.valueOf(e.ordinal());
            }

            ps.setString(i, stringValue);
        }
        else {
            ps.setObject(i, value, jdbcType.TYPE_CODE);
        }
    }

    /**
     * 获取枚举实际值
     *
     * @param value 当前值
     *
     * @return 枚举值
     */
    public static Object getEnumValue(Object value) {

        if (Objects.isNull(value)) {
            return null;
        }

        if (ValueEnum.class.isAssignableFrom(value.getClass())) {
            ValueEnum<?> valueEnum = CastUtils.cast(value);
            return EnumUtils.getValueByStrategyAnnotation(valueEnum);
        }
        else if (NameEnum.class.isAssignableFrom(value.getClass())) {
            NameEnum nameEnum = CastUtils.cast(value);
            return nameEnum.toString();
        }

        return null;
    }

    /**
     * 从 ResultSet 中根据列名获取可空结果
     *
     * @param rs         ResultSet
     * @param columnName 列名
     *
     * @return 枚举值
     *
     * @throws SQLException SQL 异常
     */
    @Override
    public E getNullableResult(
            ResultSet rs,
            String columnName
    ) throws SQLException {
        Object s = rs.getObject(columnName);
        E value = CastUtils.cast(getValue(s, this.type));

        if (Objects.isNull(value)) {
            value = super.getNullableResult(rs, columnName);
        }

        return value;

    }

    /**
     * 从 ResultSet 中根据列索引获取可空结果
     *
     * @param rs ResultSet
     * @param i  列索引
     *
     * @return 枚举值
     *
     * @throws SQLException SQL 异常
     */
    @Override
    public E getNullableResult(
            ResultSet rs,
            int i
    ) throws SQLException {
        Object s = rs.getObject(i);

        E value = CastUtils.cast(getValue(s, this.type));

        if (Objects.isNull(value)) {
            value = super.getNullableResult(rs, i);
        }

        return value;
    }

    /**
     * 从 CallableStatement 中根据列索引获取可空结果
     *
     * @param cs CallableStatement
     * @param i  列索引
     *
     * @return 枚举值
     *
     * @throws SQLException SQL 异常
     */
    @Override
    public E getNullableResult(
            CallableStatement cs,
            int i
    ) throws SQLException {
        Object s = cs.getObject(i);

        E value = CastUtils.cast(getValue(s, this.type));

        if (Objects.isNull(value)) {
            value = super.getNullableResult(cs, i);
        }

        return value;
    }

    /**
     * 获取枚举值
     *
     * @param s    数据库值
     * @param type 枚举类型
     *
     * @return 枚举值
     */
    public static Object getValue(
            Object s,
            Class<?> type
    ) {

        if (Objects.isNull(s)) {
            return null;
        }

        if (ValueEnum.class.isAssignableFrom(type)) {

            Method method = Objects.requireNonNull(
                    getValueEnumMethod(type),
                    "在接口 ValueEnum 中，找不到 " + ValueEnum.METHOD_NAME + " 方法."
            );
            Class<?> returnType = method.getReturnType();

            Object castValue = CastUtils.cast(s, returnType);
            if (Objects.nonNull(castValue)) {
                s = castValue;
            }

            return CastUtils.cast(ValueEnum.ofEnum(CastUtils.cast(type), s, true));
        }
        else if (NameEnum.class.isAssignableFrom(type)) {
            return CastUtils.cast(NameEnum.ofEnum(CastUtils.cast(type), s.toString(), true));
        }

        return null;
    }

    /**
     * 获取 ValueEnum 接口的 getValue 方法
     *
     * @param type 枚举类型
     *
     * @return 方法对象，如果找不到则返回 null
     */
    private static Method getValueEnumMethod(Class<?> type) {
        try {
            return type.getMethod(ValueEnum.METHOD_NAME);
        }
        catch (NoSuchMethodException e) {
            return null;
        }
    }

}
