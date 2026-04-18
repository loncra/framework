package io.github.loncra.framework.mybatis.plus.interceptor;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.HttpRequestParameterMapUtils;
import io.github.loncra.framework.commons.MetadataUtils;
import io.github.loncra.framework.commons.NamingUtils;
import io.github.loncra.framework.crypto.algorithm.Base64;
import io.github.loncra.framework.crypto.algorithm.CodecUtils;
import io.github.loncra.framework.mybatis.plus.CryptoNullClass;
import io.github.loncra.framework.mybatis.plus.CryptoService;
import io.github.loncra.framework.mybatis.plus.DecryptService;
import io.github.loncra.framework.mybatis.plus.EncryptService;
import io.github.loncra.framework.mybatis.plus.annotation.EncryptProperties;
import io.github.loncra.framework.mybatis.plus.annotation.Encryption;
import io.github.loncra.framework.mybatis.plus.service.BasicService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 加密内部拦截器实现
 *
 * @author maurice.chen
 */
public class EncryptInnerInterceptor implements InnerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DecryptInterceptor.class);

    /**
     * 支持的 SQL 命令类型列表
     */
    public static final List<SqlCommandType> SUPPORT_COMMANDS = Arrays.asList(SqlCommandType.UPDATE, SqlCommandType.INSERT);

    /**
     * 参数值对名称
     */
    public static final String PARAM_VALUE_PAIRS_NAME = "paramNameValuePairs";

    /**
     * 条件分割正则表达式
     */
    public static final String CONDITIONAL_SEGMENTATION_REX = "\\bAND\\b|\\bOR\\b";

    /**
     * 是否支持包装器模式
     */
    private boolean wrapperMode = false;

    /**
     * Spring 应用上下文
     */
    private ApplicationContext applicationContext;

    /**
     * 构造函数
     */
    public EncryptInnerInterceptor() {
    }

    /**
     * 构造函数
     *
     * @param wrapperMode 是否支持包装器模式
     */
    public EncryptInnerInterceptor(boolean wrapperMode) {
        this.wrapperMode = wrapperMode;
    }

    /**
     * 构造函数
     *
     * @param wrapperMode        是否支持包装器模式
     * @param applicationContext Spring 应用上下文
     */
    public EncryptInnerInterceptor(
            boolean wrapperMode,
            ApplicationContext applicationContext
    ) {
        this.wrapperMode = wrapperMode;
        this.applicationContext = applicationContext;
    }

    /**
     * 在更新操作之前进行加密处理
     *
     * @param executor  执行器
     * @param ms        MappedStatement
     * @param parameter 参数对象
     */
    @Override
    public void beforeUpdate(
            Executor executor,
            MappedStatement ms,
            Object parameter
    ) {
        if (!SUPPORT_COMMANDS.contains(ms.getSqlCommandType())) {
            return;
        }

        if (parameter instanceof Map) {
            Map<String, Object> map = CastUtils.cast(parameter);
            encrypt(map, ms);
        }
        else {

            Map<CryptoService, List<Field>> fields = this.getCryptoFields(parameter.getClass(), ms.getSqlCommandType());
            if (MapUtils.isEmpty(fields)) {
                return;
            }
            doEncrypt(parameter, fields);
        }
    }

    /**
     * 在查询操作之前进行加密处理（用于查询条件中的加密字段）
     *
     * @param executor      执行器
     * @param ms            MappedStatement
     * @param parameter     参数对象
     * @param rowBounds     行边界
     * @param resultHandler 结果处理器
     * @param boundSql      绑定的 SQL
     *
     * @throws SQLException SQL 异常
     */
    @Override
    public void beforeQuery(
            Executor executor,
            MappedStatement ms,
            Object parameter,
            RowBounds rowBounds,
            ResultHandler resultHandler,
            BoundSql boundSql
    ) throws SQLException {

        if (!Map.class.isAssignableFrom(parameter.getClass())) {
            return;
        }

        Map<String, Object> map = CastUtils.cast(parameter);
        Object ew = map.getOrDefault(Constants.WRAPPER, null);
        if (Objects.isNull(ew)) {
            return;
        }
        AbstractWrapper<?, ?, ?> queryWrapper = CastUtils.cast(ew);
        String sqlSegment = queryWrapper.getExpression().getNormal().getSqlSegment();
        if (StringUtils.isEmpty(sqlSegment)) {
            return;
        }
        String sql = StringUtils.substringBetween(sqlSegment, MetadataUtils.LEFT_BRACKET, MetadataUtils.RIGHT_BRACKET);
        List<String> conditional = Arrays.asList(sql.split(CONDITIONAL_SEGMENTATION_REX));

        Class<?> entityClass = BasicService.getEntityClass(ms.getId());

        Map<CryptoService, List<Field>> cryptoServiceListMap = getCryptoFields(entityClass, ms.getSqlCommandType());
        encryptAndReplaceParamNameValuePairs(queryWrapper, conditional, cryptoServiceListMap);
    }

    /**
     * 加密 Map 参数中的实体对象
     *
     * @param map 参数映射
     * @param ms  MappedStatement
     */
    public void encrypt(
            Map<String, Object> map,
            MappedStatement ms
    ) {
        Object et = map.getOrDefault(Constants.ENTITY, null);
        if (Objects.nonNull(et)) {

            Map<CryptoService, List<Field>> fields = this.getCryptoFields(et.getClass(), ms.getSqlCommandType());
            if (MapUtils.isEmpty(fields)) {
                return;
            }
            this.doEncrypt(et, fields);
        }
        else if (wrapperMode && map.entrySet().stream().anyMatch(t -> Objects.equals(t.getKey(), Constants.WRAPPER))) {
            // update(LambdaUpdateWrapper) or update(UpdateWrapper)
            this.doEncrypt(map, ms);
        }
    }

    /**
     * 执行加密操作
     *
     * @param entity 实体对象
     * @param fields 需要加密的字段映射（以加解密服务为键，字段列表为值）
     */
    public void doEncrypt(
            Object entity,
            Map<CryptoService, List<Field>> fields
    ) {
        for (Map.Entry<CryptoService, List<Field>> entry : fields.entrySet()) {
            EncryptService encryptService = CastUtils.cast(entry.getKey(), EncryptService.class);
            for (Field field : entry.getValue()) {
                field.setAccessible(true);
                Object value = ReflectionUtils.getField(field, entity);
                if (Objects.isNull(value) || Base64.isBase64(CodecUtils.toBytes(value.toString()))) {
                    continue;
                }
                try {
                    String text = encryptService.encrypt(value.toString());
                    ReflectionUtils.setField(field, entity, text);
                }
                catch (Exception e) {
                    LOGGER.warn("加密 [{}] 的 {} 字段出现异常:{},数据将不做任何处理", entity.getClass(), field.getName(), e.getMessage());
                }
            }

        }
    }

    /**
     * 对 Map 参数执行加密操作（用于包装器模式）
     *
     * @param map 参数映射
     * @param ms  MappedStatement
     */
    public void doEncrypt(
            Map<String, Object> map,
            MappedStatement ms
    ) {
        Object ew = map.get(Constants.WRAPPER);

        if (Objects.isNull(ew) || !AbstractWrapper.class.isAssignableFrom(ew.getClass())) {
            return;
        }
        Class<?> entityClass = BasicService.getEntityClass(ms.getId());
        AbstractWrapper<?, ?, ?> updateWrapper = CastUtils.cast(ew);
        Map<CryptoService, List<Field>> fields = this.getCryptoFields(entityClass, ms.getSqlCommandType());
        if (MapUtils.isEmpty(fields)) {
            return;
        }

        List<String> sqlSet = Arrays.asList(StringUtils.splitByWholeSeparator(updateWrapper.getSqlSet(), CastUtils.COMMA));

        encryptAndReplaceParamNameValuePairs(updateWrapper, sqlSet, fields);

    }

    /**
     * 加密并替换参数名称值对
     *
     * @param wrapper        查询包装器
     * @param sqlExpressions SQL 表达式列表
     * @param fields         需要加密的字段映射
     */
    public void encryptAndReplaceParamNameValuePairs(
            AbstractWrapper<?, ?, ?> wrapper,
            List<String> sqlExpressions,
            Map<CryptoService, List<Field>> fields
    ) {
        Field paramField = ReflectionUtils.findField(wrapper.getClass(), PARAM_VALUE_PAIRS_NAME);
        Map<String, Object> paramNameValuePairs = new LinkedHashMap<>();
        if (paramField != null) {
            paramField.setAccessible(true);
            paramNameValuePairs = CastUtils.cast(ReflectionUtils.getField(paramField, wrapper));
        }
        if (MapUtils.isEmpty(paramNameValuePairs)) {
            return;
        }

        for (Map.Entry<CryptoService, List<Field>> entry : fields.entrySet()) {
            List<String> sqlEncryptionFieldList = entry
                    .getValue()
                    .stream()
                    .map(f -> NamingUtils.castCamelCaseToSnakeCase(f.getName()))
                    .flatMap(s -> findSqlSetField(s, sqlExpressions).stream())
                    .toList();

            for (String encryptionField : sqlEncryptionFieldList) {
                String el = StringUtils.substringAfter(encryptionField, HttpRequestParameterMapUtils.EQ);
                String value = StringUtils.substringBetween(el, StandardBeanExpressionResolver.DEFAULT_EXPRESSION_PREFIX, StandardBeanExpressionResolver.DEFAULT_EXPRESSION_SUFFIX);
                String varName = StringUtils.substringAfterLast(value, CastUtils.DOT);

                EncryptService encryptService = CastUtils.cast(entry.getKey());
                Object paramValue = paramNameValuePairs.get(varName);
                if (Objects.isNull(paramValue)) {
                    continue;
                }
                String plainText = paramValue.toString();
                if (StringUtils.isEmpty(plainText) || Base64.isBase64(plainText)) {
                    continue;
                }
                paramNameValuePairs.put(varName, encryptService.encrypt(plainText));
            }
        }
    }

    /**
     * 查找 SQL SET 字段
     *
     * @param field  字段名
     * @param sqlSet SQL SET 表达式列表
     *
     * @return 匹配的 SQL SET 表达式列表
     */
    public List<String> findSqlSetField(
            String field,
            List<String> sqlSet
    ) {
        return sqlSet.stream().filter(set -> Strings.CS.contains(set, field)).collect(Collectors.toList());
    }

    /**
     * 获取加密服务实例
     *
     * @param beanName     Spring Bean 名称
     * @param serviceClass 加密服务类
     *
     * @return 加密服务实例
     */
    public EncryptService getEncryptService(
            String beanName,
            Class<? extends EncryptService> serviceClass
    ) {
        EncryptService encryptService = null;
        if (Objects.nonNull(applicationContext)) {
            encryptService = getCryptoService(applicationContext, serviceClass, beanName);
        }

        if (Objects.isNull(encryptService) && serviceClass != CryptoNullClass.class) {
            encryptService = BeanUtils.instantiateClass(serviceClass);
        }

        return encryptService;
    }

    /**
     * 从 Spring 容器中获取加解密服务实例
     *
     * @param applicationContext Spring 应用上下文
     * @param cryptoService      加解密服务类型
     * @param beanName           Spring Bean 名称
     * @param <T>                加解密服务类型
     *
     * @return 加解密服务实例
     */
    public static <T extends CryptoService> T getCryptoService(
            ApplicationContext applicationContext,
            Class<T> cryptoService,
            String beanName
    ) {

        T result = null;

        if (StringUtils.isNotEmpty(beanName) && CryptoNullClass.class != cryptoService) {
            result = applicationContext.getBean(beanName, cryptoService);
        }
        else if (StringUtils.isNotEmpty(beanName)) {
            Object bean = applicationContext.getBean(beanName);
            if (!DecryptService.class.isAssignableFrom(bean.getClass())) {
                return null;
            }
            result = CastUtils.cast(bean);
        }
        else if (CryptoNullClass.class != cryptoService) {
            result = applicationContext.getBean(cryptoService);
        }

        return result;
    }

    /**
     * 获取实体类中需要加密的字段映射
     *
     * @param entityClass 实体类
     * @param commandType SQL 命令类型
     *
     * @return 加解密服务与字段列表的映射
     */
    private Map<CryptoService, List<Field>> getCryptoFields(
            Class<?> entityClass,
            SqlCommandType commandType
    ) {
        Map<CryptoService, List<Field>> result = new LinkedHashMap<>();
        if (Objects.isNull(entityClass)) {
            return result;
        }
        List<Field> fields = new LinkedList<>();
        ReflectionUtils.doWithFields(entityClass, fields::add);

        Set<EncryptProperties> encrypts = AnnotatedElementUtils.findAllMergedAnnotations(entityClass, EncryptProperties.class);

        if (CollectionUtils.isNotEmpty(encrypts)) {
            for (EncryptProperties properties : encrypts) {
                EncryptService encryptService = getEncryptService(properties.beanName(), properties.serviceClass());
                List<Field> fieldList = result.computeIfAbsent(encryptService, k -> new LinkedList<>());
                fields
                        .stream()
                        .filter(f -> ArrayUtils.contains(properties.value(), f.getName()))
                        .filter(f -> fieldList.stream().noneMatch(l -> Strings.CS.equals(l.getName(), f.getName())))
                        .forEach(fieldList::add);
            }
        }

        for (Field field : fields) {
            Encryption encryption = AnnotatedElementUtils.findMergedAnnotation(field, Encryption.class);
            if (Objects.isNull(encryption)) {
                continue;
            }

            if (isFieldNeverStrategy(field, commandType)) {
                continue;
            }

            EncryptService encryptService = getEncryptService(encryption.beanName(), encryption.serviceClass());
            List<Field> fieldList = result.computeIfAbsent(encryptService, k -> new LinkedList<>());
            fieldList.add(field);
        }
        return result;

    }

    /**
     * 判断字段是否为 NEVER 策略（不参与插入或更新）
     *
     * @param field       字段
     * @param commandType SQL 命令类型
     *
     * @return true 如果是 NEVER 策略，否则 false
     */
    private boolean isFieldNeverStrategy(
            Field field,
            SqlCommandType commandType
    ) {
        TableField tableField = AnnotatedElementUtils.findMergedAnnotation(field, TableField.class);

        if (Objects.isNull(tableField)) {
            return false;
        }

        if (SqlCommandType.INSERT.equals(commandType) && FieldStrategy.NEVER.equals(tableField.insertStrategy())) {
            return true;
        }

        return SqlCommandType.INSERT.equals(commandType) && FieldStrategy.NEVER.equals(tableField.updateStrategy());
    }
}
