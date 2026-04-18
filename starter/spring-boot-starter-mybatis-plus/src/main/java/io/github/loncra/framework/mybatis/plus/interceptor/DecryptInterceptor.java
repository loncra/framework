package io.github.loncra.framework.mybatis.plus.interceptor;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.crypto.algorithm.Base64;
import io.github.loncra.framework.crypto.algorithm.CodecUtils;
import io.github.loncra.framework.mybatis.plus.CryptoNullClass;
import io.github.loncra.framework.mybatis.plus.CryptoService;
import io.github.loncra.framework.mybatis.plus.DecryptService;
import io.github.loncra.framework.mybatis.plus.annotation.DecryptProperties;
import io.github.loncra.framework.mybatis.plus.annotation.Decryption;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Strings;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;


/**
 * 解密内部拦截器实现
 *
 * @author maurice.chen
 */
@Intercepts(
        @Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
        )
)
public class DecryptInterceptor implements Interceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DecryptInterceptor.class);

    private final ApplicationContext applicationContext;

    /**
     * 构造函数
     *
     * @param applicationContext Spring 应用上下文
     */
    public DecryptInterceptor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * 拦截查询方法，对查询结果进行解密处理
     *
     * @param invocation 方法调用信息
     *
     * @return 解密后的查询结果
     *
     * @throws Throwable 可能抛出的异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object o = invocation.proceed();
        if (List.class.isAssignableFrom(o.getClass())) {
            List<Object> list = CastUtils.cast(o);
            Map<Class<?>, Map<CryptoService, List<Field>>> cacheEntity = new LinkedHashMap<>();
            for (Object item : list) {
                Map<CryptoService, List<Field>> cryptoServiceListMap = cacheEntity.computeIfAbsent(item.getClass(), k -> new LinkedHashMap<>());
                if (MapUtils.isEmpty(cryptoServiceListMap)) {
                    cryptoServiceListMap.putAll(getCryptoFields(item.getClass()));
                }
                decrypt(item, cryptoServiceListMap);
            }
        }
        else {
            Map<CryptoService, List<Field>> cryptoServiceListMap = getCryptoFields(o.getClass());
            decrypt(o, cryptoServiceListMap);
        }
        return o;
    }

    /**
     * 解密实体对象的字段值
     *
     * @param entity 实体对象
     * @param fields 需要解密的字段映射（以加解密服务为键，字段列表为值）
     */
    public void decrypt(
            Object entity,
            Map<CryptoService, List<Field>> fields
    ) {
        for (Map.Entry<CryptoService, List<Field>> entry : fields.entrySet()) {
            DecryptService decryptService = CastUtils.cast(entry.getKey(), DecryptService.class);
            for (Field field : entry.getValue()) {
                field.setAccessible(true);
                Object value = ReflectionUtils.getField(field, entity);
                if (Objects.isNull(value) || !Base64.isBase64(CodecUtils.toBytes(value.toString()))) {
                    continue;
                }
                try {
                    String text = decryptService.decrypt(value.toString());
                    ReflectionUtils.setField(field, entity, text);
                }
                catch (Exception e) {
                    LOGGER.warn("解密 [{}] 的 {} 字段出现异常:{},数据将不做任何处理", entity.getClass(), field.getName(), e.getMessage());
                }
            }

        }
    }

    /**
     * 获取解密服务实例
     * <p>优先从 Spring 容器中获取指定 beanName 的实例，如果不存在则尝试获取 serviceClass 类型的实例，最后尝试实例化 serviceClass</p>
     *
     * @param beanName     Spring Bean 名称
     * @param serviceClass 解密服务类
     *
     * @return 解密服务实例
     */
    public DecryptService getDecryptService(
            String beanName,
            Class<? extends DecryptService> serviceClass
    ) {

        DecryptService encryptService = null;
        if (Objects.nonNull(applicationContext)) {
            encryptService = EncryptInnerInterceptor.getCryptoService(applicationContext, serviceClass, beanName);
        }

        if (Objects.isNull(encryptService) && serviceClass != CryptoNullClass.class) {
            encryptService = BeanUtils.instantiateClass(serviceClass);
        }

        return encryptService;
    }

    /**
     * 获取实体类中需要解密的字段映射
     * <p>扫描实体类及其字段上的 {@link Decryption} 和 {@link DecryptProperties} 注解，获取需要解密的字段</p>
     *
     * @param entityClass 实体类
     *
     * @return 加解密服务与字段列表的映射
     */
    public Map<CryptoService, List<Field>> getCryptoFields(Class<?> entityClass) {
        Map<CryptoService, List<Field>> result = new LinkedHashMap<>();
        if (Objects.isNull(entityClass)) {
            return result;
        }
        List<Field> fields = new LinkedList<>();
        ReflectionUtils.doWithFields(entityClass, fields::add);
        Set<DecryptProperties> decrypts = AnnotatedElementUtils.findAllMergedAnnotations(entityClass, DecryptProperties.class);

        if (CollectionUtils.isNotEmpty(decrypts)) {
            for (DecryptProperties properties : decrypts) {
                DecryptService decryptService = getDecryptService(properties.beanName(), properties.serviceClass());
                List<Field> fieldList = result.computeIfAbsent(decryptService, k -> new LinkedList<>());
                fields
                        .stream()
                        .filter(f -> ArrayUtils.contains(properties.value(), f.getName()))
                        .filter(f -> fieldList.stream().noneMatch(l -> Strings.CS.equals(l.getName(), f.getName())))
                        .forEach(fieldList::add);
            }
        }

        for (Field field : fields) {
            Decryption decryption = AnnotatedElementUtils.findMergedAnnotation(field, Decryption.class);
            if (Objects.isNull(decryption)) {
                continue;
            }
            DecryptService decryptService = getDecryptService(decryption.beanName(), decryption.serviceClass());
            List<Field> fieldList = result.computeIfAbsent(decryptService, k -> new LinkedList<>());
            fieldList.add(field);
        }

        return result;

    }
}
