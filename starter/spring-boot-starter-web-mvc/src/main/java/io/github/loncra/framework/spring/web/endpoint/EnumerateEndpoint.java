package io.github.loncra.framework.spring.web.endpoint;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.enumerate.NameValueEnum;
import io.github.loncra.framework.commons.enumerate.ValueEnum;
import io.github.loncra.framework.commons.exception.ServiceException;
import io.github.loncra.framework.spring.web.config.SpringWebMvcProperties;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 获取枚举键值对终端
 *
 * @author maurice
 */
@Endpoint(id = "enumerate")
public class EnumerateEndpoint {

    private final static Logger LOGGER = LoggerFactory.getLogger(EnumerateEndpoint.class);

    /**
     * 默认的枚举字段名称
     */
    public final static String DEFAULT_ENUM_KEY_NAME = "enum";
    /**
     * 缓存值
     */
    private static final Map<String, Object> CACHE = new LinkedHashMap<>();

    /**
     * 信息贡献者集合
     */
    private final List<InfoContributor> infoContributors = new ArrayList<>();

    /**
     * Spring 资源解析器
     */
    private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    /**
     * Spring 元数据读取工厂
     */
    private final MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

    /**
     * 并发锁
     */
    private final Lock lock = new ReentrantLock();

    /**
     * 配置信息
     */
    private final SpringWebMvcProperties properties;

    /**
     * 创建一个获取枚举键值对终端
     *
     * @param infoContributors 信息贡献者实现
     */
    public EnumerateEndpoint(
            List<InfoContributor> infoContributors,
            SpringWebMvcProperties properties
    ) {
        this.infoContributors.addAll(infoContributors);
        this.properties = properties;
    }

    public static Map<String, Object> getInfoContributorsMap(List<InfoContributor> infoContributors) {
        Info.Builder builder = new Info.Builder();

        for (InfoContributor contributor : infoContributors) {
            contributor.contribute(builder);
        }

        Info build = builder.build();

        Map<String, Object> info = new LinkedHashMap<>();

        Map<String, Object> details = build.getDetails();

        if (MapUtils.isNotEmpty(details)) {
            info.putAll(details);
        }

        return info;
    }

    /**
     * 获取枚举信息
     *
     * @return 枚举信息映射
     */
    @ReadOperation
    public Map<String, Object> enumerate() {
        // 如果缓存没有，就去扫描遍历
        lock.lock();

        try {

            if (CACHE.isEmpty()) {
                Map<String, Object> info = enumerateEndpoint();
                CACHE.putAll(info);
            }

            return CACHE;
        }
        finally {
            lock.unlock();
        }

    }

    public Map<String, Object> enumerateEndpoint() {
        Map<String, Map<String, Object>> enumMap = resolveEnumerate();
        Map<String, Object> info = getInfoContributorsMap(this.infoContributors);
        info.put(DEFAULT_ENUM_KEY_NAME, enumMap);
        return info;
    }

    /**
     * 解析枚举信息
     *
     * @return 枚举信息映射
     */
    private <T> Map<String, Map<String, Object>> resolveEnumerate() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("开始解析 info.enum 信息");
        }

        Map<String, Map<String, Object>> result = new LinkedHashMap<>();

        // 扫描所有继承 NameEnum 的类
        Set<Class<T>> classes = resolvePlaceholders();
        // 如果找不到，什么都不做
        if (CollectionUtils.isEmpty(classes)) {
            return result;
        }

        for (Class<T> c : classes) {
            if (!c.isEnum()) {
                continue;
            }
            Map<String, Object> map;
            if (NameValueEnum.class.isAssignableFrom(c)) {
                map = new LinkedHashMap<>();
                Map<String, String> nameMap = NameEnum.ofMap(CastUtils.cast(c));
                Map<String, String> valueMap = ValueEnum.ofMap(CastUtils.cast(c));
                nameMap.forEach((k,v) -> map.put(nameMap.get(k), valueMap.get(k)));
            }
            else if (NameEnum.class.isAssignableFrom(c)) {
                Map<String, String> nameMap = NameEnum.ofMap(CastUtils.cast(c));
                map = new LinkedHashMap<>(nameMap);
            }
            else if (ValueEnum.class.isAssignableFrom(c)) {
                map = ValueEnum.ofMap(CastUtils.cast(c));
            }
            else {
                map = new LinkedHashMap<>();
            }

            if (MapUtils.isNotEmpty(map)) {
                result.put(c.getSimpleName(), map);
            }
        }

        return result;
    }

    /**
     * 解析占位符并获取枚举类集合
     *
     * @return 枚举类集合
     */
    private <T> Set<Class<T>> resolvePlaceholders() {
        Set<Class<T>> classes = new HashSet<>();

        if (CollectionUtils.isEmpty(properties.getEnumerateEndpointBasePackages())) {
            return classes;
        }

        for (String basePackage : properties.getEnumerateEndpointBasePackages()) {
            classes.addAll(resolvePlaceholders(basePackage));
        }

        return classes;
    }

    public <T> Set<Class<T>> resolvePlaceholders(
            String basePackage
    ) {

        Set<Class<T>> classes = new HashSet<>();
        String classPath = ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/**/*.class";
        TypeFilter nameEnumFilter = new AssignableTypeFilter(NameEnum.class);
        TypeFilter valueEnumFilter = new AssignableTypeFilter(ValueEnum.class);

        try {
            Resource[] resources = this.resourcePatternResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + classPath);

            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    if (nameEnumFilter.match(metadataReader, metadataReaderFactory) || valueEnumFilter.match(metadataReader, metadataReaderFactory)) {
                        classes.add(CastUtils.cast(Class.forName(metadataReader.getClassMetadata().getClassName())));
                    }
                }
            }
        }
        catch (Exception e) {
            throw new ServiceException(e);
        }

        return classes;
    }


}
