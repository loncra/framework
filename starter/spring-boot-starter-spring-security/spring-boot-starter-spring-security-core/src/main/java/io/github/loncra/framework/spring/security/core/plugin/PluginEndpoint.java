package io.github.loncra.framework.spring.security.core.plugin;

import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.exception.ServiceException;
import io.github.loncra.framework.commons.exception.SystemException;
import io.github.loncra.framework.commons.tree.Tree;
import io.github.loncra.framework.commons.tree.TreeUtils;
import io.github.loncra.framework.security.entity.RoleAuthority;
import io.github.loncra.framework.security.plugin.Plugin;
import io.github.loncra.framework.security.plugin.PluginInfo;
import io.github.loncra.framework.security.plugin.TargetObject;
import io.github.loncra.framework.spring.security.core.authentication.config.PluginProperties;
import io.github.loncra.framework.spring.web.endpoint.EnumerateEndpoint;
import io.github.loncra.framework.spring.web.mvc.SpringMvcUtils;
import jakarta.annotation.PostConstruct;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.SpelNode;
import org.springframework.expression.spel.ast.MethodReference;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.lang.NonNull;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ClassUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * 插件信息终端
 *
 * @author maurice
 */
@Endpoint(id = PluginEndpoint.DEFAULT_PLUGIN_KEY_NAME)
public class PluginEndpoint {

    private final static Logger LOGGER = LoggerFactory.getLogger(PluginEndpoint.class);

    public static final String DEFAULT_IS_AUTHENTICATED_METHOD_NAME = "isAuthenticated";

    public static final String DEFAULT_HAS_ANY_ROLE_METHOD_NAME = "hasAnyRole";

    public static final String DEFAULT_HAS_ROLE_METHOD_NAME = "hasRole";

    public static final List<String> DEFAULT_SUPPORT_SECURITY_METHOD_NAME = Arrays.asList(
            "hasAuthority",
            "hasAnyAuthority",
            DEFAULT_HAS_ROLE_METHOD_NAME,
            DEFAULT_HAS_ANY_ROLE_METHOD_NAME,
            DEFAULT_IS_AUTHENTICATED_METHOD_NAME
    );

    public static final List<String> DEFAULT_ROLE_PREFIX_METHOD_NAME = Arrays.asList(
            DEFAULT_HAS_ANY_ROLE_METHOD_NAME,
            DEFAULT_HAS_ROLE_METHOD_NAME
    );

    /**
     * 缓存值
     */
    private static final Map<String, Object> CACHE = new LinkedHashMap<>();

    /**
     * 默认的插件节点名称
     */
    public final static String DEFAULT_PLUGIN_KEY_NAME = "plugin";

    /**
     * 信息奉献者集合
     */
    private final List<InfoContributor> infoContributors = new ArrayList<>();

    /**
     * spring 资源解析器
     */
    private final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    /**
     * spring 元数据读取工厂
     */
    private final MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);

    /**
     * spring security 方法表达式具柄
     */
    private final MethodSecurityExpressionHandler mse = new DefaultMethodSecurityExpressionHandler();


    /**
     * 找不到父类的插件信息
     */
    private final Map<String, List<Method>> missingParentMap = new LinkedHashMap<>();

    /**
     * 并发锁
     */
    private final Lock lock = new ReentrantLock();

    private final PluginProperties properties;

    public PluginEndpoint(
            List<InfoContributor> infoContributors,
            PluginProperties properties
    ) {
        this.infoContributors.addAll(infoContributors);
        this.properties = properties;
    }

    public PluginEndpoint(PluginProperties properties) {
        this.properties = properties;
    }

    @ReadOperation
    public Map<String, Object> plugin() {
        // 如果缓存没有，就去扫描遍历
        lock.lock();

        try {

            if (CACHE.isEmpty()) {
                Map<String, Object> info = pluginEndpoint();
                CACHE.putAll(info);
            }

            return CACHE;
        }
        finally {
            lock.unlock();
        }
    }

    public Map<String, Object> pluginEndpoint() {
        Map<String, Object> info = EnumerateEndpoint.getInfoContributorsMap(this.infoContributors);
        List<Tree<String, PluginInfo>> pluginList = resolvePlugin();
        info.put(DEFAULT_PLUGIN_KEY_NAME, pluginList);
        return info;
    }

    @PostConstruct
    public void init() {
        for (Map.Entry<String, PluginInfo> entry : properties.getParent().entrySet()) {
            entry.getValue().setId(entry.getKey());
        }
    }

    /**
     * 解析资源信息
     */
    public List<Tree<String, PluginInfo>> resolvePlugin() {
        LOGGER.info("开始解析 info.plugin 信息");

        List<Tree<String, PluginInfo>> result = new ArrayList<>();

        // 扫描所有带 Controller 注解的类
        Set<Object> targetSet = resolvePlaceholders();
        // 如果找不到，什么都不做
        if (CollectionUtils.isEmpty(targetSet)) {
            return result;
        }

        List<PluginInfo> pluginInfoList = getPluginInfos(targetSet);

        properties
                .getParent()
                .values()
                .stream()
                .filter(p -> properties.getGenerateSources().stream().anyMatch(s -> p.getSources().contains(s)))
                .filter(p -> Objects.isNull(p.getParent()))
                .forEach(p -> p.setParent(PluginInfo.DEFAULT_ROOT_PARENT_NAME));
                /*.filter(p -> StringUtils.isBlank(p.getType()))
                .forEach(p -> p.setType(ResourceType.Menu.toString()));*/

        pluginInfoList.addAll(properties.getParent().values());

        LOGGER.info("找到 {} 条记录信息", CACHE.size());

        result = TreeUtils.buildTree(pluginInfoList);

        return result;
    }

    @NonNull
    public List<PluginInfo> getPluginInfos(Set<Object> targetSet) {
        List<PluginInfo> pluginInfoList = new ArrayList<>();

        // 循环解析类中的方法
        for (Object target : targetSet) {

            if (target instanceof Class<?> classTarget) {
                Plugin plugin = AnnotationUtils.findAnnotation(classTarget, Plugin.class);
                if (Objects.isNull(plugin)) {
                    continue;
                }

                List<String> sources = Arrays.asList(plugin.sources());
                if (properties.getGenerateSources().stream().noneMatch(sources::contains)) {
                    continue;
                }

                PluginInfo parent = createPluginInfo(plugin, classTarget);
                // 如果该 plugin 配置没有 id 值，就直接用类名做 id 值
                if (StringUtils.isBlank(parent.getId())) {
                    parent.setId(DigestUtils.md5DigestAsHex(classTarget.getName().getBytes(StandardCharsets.UTF_8)));
                }

                pluginInfoList.add(parent);
                Method[] methods = classTarget.isInterface() ? classTarget.getMethods() : classTarget.getDeclaredMethods();
                List<Method> methodList = Arrays.asList(methods);
                TargetObject targetObject = new TargetObject(target, methodList);
                // 遍历方法级别的 plugin 注解。并添加到 parent 中
                List<PluginInfo> pluginInfos = buildPluginInfo(targetObject, parent);
                pluginInfoList.addAll(pluginInfos);

            }
            else if (target instanceof Method method) {
                Plugin plugin = AnnotationUtils.findAnnotation(method, Plugin.class);
                if (Objects.isNull(plugin)) {
                    continue;
                }

                PluginInfo temp = null;
                if (StringUtils.isNotBlank(plugin.parent())) {
                    temp = properties
                            .getParent()
                            .values()
                            .stream()
                            .filter(p -> p.getId().equals(plugin.parent()))
                            .findFirst()
                            .orElse(null);
                    if (Objects.isNull(temp)) {
                        temp = pluginInfoList
                                .stream()
                                .filter(p -> p.getId().equals(plugin.parent()))
                                .findFirst()
                                .orElse(null);
                    }
                    if (Objects.isNull(temp)) {
                        List<Method> methods = missingParentMap.computeIfAbsent(plugin.parent(), s -> new ArrayList<>());
                        methods.add(method);
                        continue;
                    }
                }

                TargetObject targetObject = new TargetObject(method, Collections.singletonList(method));
                List<PluginInfo> pluginInfos = buildPluginInfo(targetObject, temp);
                pluginInfoList.addAll(pluginInfos);
            }
            else {
                throw new SystemException("Plugin 注解只支持 Class 或 Method 类型, ");
            }

        }
        return pluginInfoList;
    }

    public PluginInfo createPluginInfo(
            Plugin plugin,
            Class<?> target
    ) {

        PluginInfo parent = new PluginInfo(plugin);

        // 如果类头存在 RequestMapping 注解，需要将该注解的 value 合并起来
        RequestMapping mapping = AnnotationUtils.findAnnotation(target, RequestMapping.class);
        if (mapping != null) {
            List<String> uri = new ArrayList<>();
            for (String value : mapping.value()) {
                // 添加 /** 通配符，作用是为了可能某些需要带参数过来
                String url = Strings.CS.appendIfMissing(value, SpringMvcUtils.ANT_PATH_MATCH_ALL);
                // 删除.（逗号）后缀的所有内容，作用是可能有些配置是
                // 有.html 和 .json的类似配置，但其实一个就够了
                uri.add(RegExUtils.replacePattern((CharSequence) url, "\\{.*\\}", StringUtils.EMPTY));
            }
            parent.setValue(StringUtils.join(uri, CastUtils.COMMA));

        }

        return parent;
    }

    /**
     * 遍历方法级别的 {@link Plugin} 信息，并将值合并的 {@link PluginInfo#getChildren()} 中
     *
     * @param targetObject 目标对象
     * @param parent       根节点信息
     */
    private List<PluginInfo> buildPluginInfo(
            TargetObject targetObject,
            PluginInfo parent
    ) {

        List<PluginInfo> result = new ArrayList<>();

        if (Objects.nonNull(parent) && properties.getGenerateSources().stream().noneMatch(s -> parent.getSources().contains(s))) {
            return result;
        }

        for (Method method : targetObject.getMethodList()) {
            // 如果找不到 PluginInfo 注解，什么都不做
            Plugin plugin = AnnotationUtils.findAnnotation(method, Plugin.class);
            if (plugin == null) {
                continue;
            }

            // 获取请求 url 值
            List<String> values = getRequestValues(targetObject.getTarget(), method, parent);
            if (values.isEmpty()) {
                continue;
            }

            PluginInfo target = new PluginInfo(plugin);
            // 如果方法级别的 plugin 信息没有 id，就用方法名称做 id
            if (StringUtils.isBlank(target.getId())) {
                target.setId(DigestUtils.md5DigestAsHex(method.toString().getBytes(StandardCharsets.UTF_8)));
            }

            if (StringUtils.isBlank(target.getParent()) && Objects.nonNull(parent)) {
                target.setParent(parent.getId());
            }

            if (CollectionUtils.isEmpty(target.getSources()) && Objects.nonNull(parent)) {
                target.setSources(parent.getSources());
            }

            target.setValue(StringUtils.join(values, CastUtils.COMMA));
            List<String> authorize = getSecurityAuthorize(method);

            if (!authorize.isEmpty()) {
                target.setAuthority(StringUtils.join(authorize, CastUtils.COMMA));
            }

            result.add(target);

            findMissingParentThenBuildPluginInfo(targetObject, target, result);

        }

        findMissingParentThenBuildPluginInfo(targetObject, parent, result);

        return result;
    }

    private void findMissingParentThenBuildPluginInfo(
            TargetObject targetObject,
            PluginInfo parent,
            List<PluginInfo> result
    ) {
        if (!missingParentMap.containsKey(parent.getId())) {
            return;
        }

        List<Method> subMethods = missingParentMap.get(parent.getId());
        TargetObject subObject = new TargetObject(targetObject, subMethods);
        missingParentMap.remove(parent.getId());
        result.addAll(buildPluginInfo(subObject, parent));
    }

    private List<String> getSecurityAuthorize(Method method) {

        // 获取 RequestMapping 的 value 信息
        List<String> values = new ArrayList<>();

        PreAuthorize preAuthorize = AnnotationUtils.findAnnotation(method, PreAuthorize.class);
        if (preAuthorize != null) {
            String v = preAuthorize.value();
            List<String> methodValues = getAuthorityMethodValue(v);
            values.addAll(methodValues);
        }

        PostAuthorize postAuthorize = AnnotationUtils.findAnnotation(method, PostAuthorize.class);
        if (postAuthorize != null) {
            String v = postAuthorize.value();
            List<String> methodValues = getAuthorityMethodValue(v);
            values.addAll(methodValues);
        }

        return values;
    }

    private List<String> getAuthorityMethodValue(String value) {

        Expression expression = mse.getExpressionParser().parseExpression(value);
        SpelExpression spelExpression = (SpelExpression) expression;
        SpelNode spelNode = spelExpression.getAST();

        return getAuthorityMethodValue(spelNode);
    }

    private List<String> getAuthorityMethodValue(SpelNode spelNode) {

        List<String> result = new ArrayList<>();

        if (MethodReference.class.isAssignableFrom(spelNode.getClass())) {

            MethodReference mr = (MethodReference) spelNode;
            if (DEFAULT_SUPPORT_SECURITY_METHOD_NAME.contains(mr.getName())) {

                if (mr.getName().equals(DEFAULT_IS_AUTHENTICATED_METHOD_NAME)) {
                    result.add(DEFAULT_IS_AUTHENTICATED_METHOD_NAME);
                }
                else {
                    for (int i = 0; i < mr.getChildCount(); i++) {
                        String value = mr.getChild(i).toString().replace("'", StringUtils.EMPTY);

                        if (DEFAULT_ROLE_PREFIX_METHOD_NAME.contains(mr.getName())) {
                            value = RoleAuthority.DEFAULT_ROLE_PREFIX + value;
                        }

                        result.add(value);
                    }
                }

                return result;
            }
        }

        for (int i = 0; i < spelNode.getChildCount(); i++) {
            SpelNode child = spelNode.getChild(i);
            result.addAll(getAuthorityMethodValue(child));
        }

        return result;
    }

    /**
     * 获取多个请求 uri 信息，并用 ，（逗号）分割
     *
     * @param target      目标对象
     * @param targetValue 目标 url 值
     * @param parent      父类 plugin
     *
     * @return 多个请求 uri 信息，并用 ，（逗号）分割
     */
    private String getRequestValueString(
            Object target,
            String targetValue,
            PluginInfo parent
    ) {

        List<String> uri = new ArrayList<>();

        List<String> parentValueList = new LinkedList<>();

        if (StringUtils.isEmpty(StringUtils.trimToEmpty(parent.getValue()))) {
            if (Method.class.isAssignableFrom(target.getClass()) && StringUtils.isEmpty(parent.getValue())) {
                Method method = CastUtils.cast(target);
                RequestMapping requestMapping = AnnotationUtils.findAnnotation(method.getDeclaringClass(), RequestMapping.class);
                if (Objects.nonNull(requestMapping)) {
                    parentValueList = Arrays.stream(requestMapping.value()).map(s -> Strings.CS.appendIfMissing(s, AntPathMatcher.DEFAULT_PATH_SEPARATOR)).collect(Collectors.toList());
                }
                else {
                    return Strings.CS.appendIfMissing(targetValue, SpringMvcUtils.ANT_PATH_MATCH_ALL);
                }
            }
            else {
                return Strings.CS.appendIfMissing(targetValue, SpringMvcUtils.ANT_PATH_MATCH_ALL);
            }
        }
        else if (TargetObject.class.isAssignableFrom(target.getClass())) {
            TargetObject targetObject = CastUtils.cast(target);
            if (Method.class.isAssignableFrom(targetObject.getTarget().getClass())) {
                return Strings.CS.appendIfMissing(targetValue, SpringMvcUtils.ANT_PATH_MATCH_ALL);
            }
        }

        if (StringUtils.isNotEmpty(parent.getValue())) {
            String prefix = Strings.CS.appendIfMissing(Strings.CS.removeEnd(parent.getValue(), "**"), AntPathMatcher.DEFAULT_PATH_SEPARATOR);
            if (CollectionUtils.isEmpty(parentValueList)) {
                parentValueList.add(prefix);
            }
            else {
                parentValueList = parentValueList
                        .stream()
                        .map(s -> Strings.CS.prependIfMissing(Strings.CS.removeStart(s, AntPathMatcher.DEFAULT_PATH_SEPARATOR), prefix))
                        .collect(Collectors.toList());
            }
        }

        for (String parentValue : parentValueList) {
            for (String value : StringUtils.split(targetValue)) {
                String url = Strings.CS.appendIfMissing(parentValue, value + SpringMvcUtils.ANT_PATH_MATCH_ALL);
                uri.add(RegExUtils.removeAll(url, "\\{.*\\}"));
            }
        }

        return StringUtils.join(uri, CastUtils.COMMA);
    }

    /**
     * 获取方法名请求值
     *
     * @param target 构建目标
     * @param method 方法
     * @param parent 父类节点
     *
     * @return 请求值集合
     */
    private List<String> getRequestValues(
            Object target,
            Method method,
            PluginInfo parent
    ) {

        // 获取 RequestMapping 的 value 信息
        List<String> values = new ArrayList<>();
        // 如果找不到 RequestMapping 注解，什么都不做
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        if (Objects.nonNull(requestMapping)) {
            values = Arrays.asList(requestMapping.value());
        }

        // 如果为空值，表示可能是 GetMapping 注解
        if (CollectionUtils.isEmpty(values)) {
            // 如果找不到 GetMapping 注解，什么都不做
            GetMapping annotation = AnnotationUtils.findAnnotation(method, GetMapping.class);
            if (Objects.nonNull(annotation)) {
                values = Arrays.asList(annotation.value());
            }
        }

        // 如果为空值，表示可能是 PostMapping 注解
        if (CollectionUtils.isEmpty(values)) {
            // 如果找不到 PostMapping 注解，什么都不做
            PostMapping annotation = AnnotationUtils.findAnnotation(method, PostMapping.class);
            if (Objects.nonNull(annotation)) {
                values = Arrays.asList(annotation.value());
            }
        }

        // 如果为空值，表示可能是 PutMapping 注解
        if (CollectionUtils.isEmpty(values)) {
            // 如果找不到 PutMapping 注解，什么都不做
            PutMapping annotation = AnnotationUtils.findAnnotation(method, PutMapping.class);
            if (Objects.nonNull(annotation)) {
                values = Arrays.asList(annotation.value());
            }
        }

        // 如果为空值，表示可能是 DeleteMapping 注解
        if (CollectionUtils.isEmpty(values)) {
            // 如果找不到 PutMapping 注解，什么都不做
            DeleteMapping annotation = AnnotationUtils.findAnnotation(method, DeleteMapping.class);
            if (Objects.nonNull(annotation)) {
                values = Arrays.asList(annotation.value());
            }
        }

        // 如果为空值，表示注解没命名，直接用方法名
        if (CollectionUtils.isEmpty(values)) {
            values = Collections.singletonList(method.getName());
        }

        return values.stream()
                .map(v -> Objects.isNull(parent) ? v : getRequestValueString(target, v, parent))
                .collect(Collectors.toList());
    }

    /**
     * 扫描包含 Controller 注解的所有类
     *
     * @return 包含 Controller 注解的所有类
     */
    public Set<Object> resolvePlaceholders() {
        Set<Object> target = new HashSet<>();

        if (CollectionUtils.isEmpty(properties.getBasePackages())) {
            return target;
        }

        for (String basePackage : properties.getBasePackages()) {
            String classPath = ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/**/*.class";
            TypeFilter filter = new AnnotationTypeFilter(Plugin.class);

            try {
                Resource[] resources = this.resourcePatternResolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + classPath);
                for (Resource resource : resources) {

                    if (!resource.isReadable()) {
                        continue;
                    }

                    MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
                    Class<?> targetClass = Class.forName(metadataReader.getClassMetadata().getClassName());

                    if (filter.match(metadataReader, metadataReaderFactory)) {
                        target.add(targetClass);
                    }
                    else {
                        Method[] methods = targetClass.getDeclaredMethods();
                        for (Method method : methods) {
                            Plugin plugin = AnnotationUtils.findAnnotation(method, Plugin.class);
                            if (Objects.nonNull(plugin)) {
                                target.add(method);
                            }
                        }
                    }
                }

            }
            catch (Exception e) {
                throw new ServiceException(e);
            }
        }

        return target;
    }

}
