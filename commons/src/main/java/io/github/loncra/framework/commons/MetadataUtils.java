package io.github.loncra.framework.commons;

import io.github.loncra.framework.commons.annotation.Description;
import io.github.loncra.framework.commons.annotation.Metadata;
import io.github.loncra.framework.commons.annotation.MetadataElements;
import io.github.loncra.framework.commons.domain.metadata.DescriptionMetadata;
import io.github.loncra.framework.commons.domain.metadata.TreeDescriptionMetadata;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 元数据工具类
 *
 * @author maurice.chen
 */
public abstract class MetadataUtils {

    /**
     * 左括号
     */
    public static final String LEFT_BRACKET = "(";

    /**
     * 右括号
     */
    public static final String RIGHT_BRACKET = ")";

    /**
     * 转换为描述元数据集合
     *
     * @param descriptionClasses 描述类集合
     *
     * @return 描述元数据集合
     */
    @SuppressWarnings("null")
    public static List<TreeDescriptionMetadata> convertDescriptionMetadata(List<Class<?>> descriptionClasses) {
        return descriptionClasses.stream()
                .filter(Objects::nonNull)
                .map(MetadataUtils::convertDescriptionMetadata)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 转换为描述元数据
     *
     * @param descriptionClass 描述类
     *
     * @return 描述元数据
     */
    @SuppressWarnings("null")
    public static TreeDescriptionMetadata convertDescriptionMetadata(Class<?> descriptionClass) {
        if (descriptionClass == null) {
            return null;
        }

        Description description = AnnotationUtils.findAnnotation(descriptionClass, Description.class);
        if (Objects.isNull(description)) {
            return null;
        }

        TreeDescriptionMetadata metadata = TreeDescriptionMetadata.of(
                StringUtils.defaultIfEmpty(description.name(), descriptionClass.getSimpleName()),
                description.value()
        );
        metadata.setSort(description.sort());
        metadata.setType(descriptionClass.getName());
        metadata.setSource(Class.class.getName());

        List<TreeDescriptionMetadata> children = new LinkedList<>();

        // 使用 Spring 的 ReflectionUtils.doWithFields 收集所有字段
        List<Field> fields = new LinkedList<>();
        ReflectionUtils.doWithFields(descriptionClass, fields::add);
        fields.stream()
                .map(MetadataUtils::convertDescriptionMetadata)
                .filter(Objects::nonNull)
                .peek(f -> f.setParentId(metadata.getId()))
                .forEach(children::add);

        MethodUtils.getMethodsListWithAnnotation(descriptionClass, Description.class)
                .stream()
                .map(MetadataUtils::convertDescriptionMetadata)
                .filter(Objects::nonNull)
                .peek(f -> f.setParentId(metadata.getId()))
                .forEach(children::add);

        metadata.setChildren(children.stream().sorted(Comparator.comparing(DescriptionMetadata::getSort)).collect(Collectors.toCollection(LinkedList::new)));

        return metadata;
    }

    /**
     * 将方法转换为描述元数据
     *
     * @param method 方法
     *
     * @return 描述元数据
     */
    @SuppressWarnings("null")
    public static TreeDescriptionMetadata convertDescriptionMetadata(Method method) {
        if (method == null) {
            return null;
        }

        Description description = AnnotationUtils.findAnnotation(method, Description.class);

        if (Objects.isNull(description)) {
            return null;
        }

        Class<?> type = method.getReturnType();

        TreeDescriptionMetadata metadata = TreeDescriptionMetadata.of(
                StringUtils.defaultIfEmpty(description.name(), method.getName() + LEFT_BRACKET + RIGHT_BRACKET),
                description.value()
        );

        metadata.setSort(description.sort());
        metadata.setType(type.getName());
        metadata.setSource(Method.class.getName());

        return metadata;
    }

    /**
     * 转换为描述元数据
     *
     * @param field 字段
     *
     * @return 描述元数据集合
     */
    @SuppressWarnings("null")
    public static TreeDescriptionMetadata convertDescriptionMetadata(Field field) {
        if (field == null) {
            return null;
        }

        Description description = AnnotationUtils.findAnnotation(field, Description.class);

        if (Objects.isNull(description)) {
            description = AnnotationUtils.findAnnotation(field.getType(), Description.class);
        }

        if (Objects.isNull(description)) {
            return null;
        }

        Class<?> type = field.getType();

        TreeDescriptionMetadata metadata = TreeDescriptionMetadata.of(
                StringUtils.defaultIfEmpty(description.name(), field.getName()),
                description.value()
        );
        metadata.setSort(description.sort());
        metadata.setType(type.getName());
        metadata.setSource(Field.class.getName());

        if (ObjectUtils.isPrimitive(type)) {
            return metadata;
        }

        Class<?> nextType = type;
        if (Collection.class.isAssignableFrom(type)) {
            // 使用 Spring 的 ResolvableType 获取集合的泛型类型
            ResolvableType resolvableType = ResolvableType.forField(field);
            nextType = resolvableType.asCollection().resolveGeneric(0);
            if (nextType != null && nextType.isInterface()) {
                return metadata;
            }
        }

        if (nextType != null) {
            // 使用 Spring 的 ReflectionUtils.doWithFields 收集所有字段
            List<Field> fields = new LinkedList<>();
            ReflectionUtils.doWithFields(nextType, fields::add);
            fields.stream()
                    .map(MetadataUtils::convertDescriptionMetadata)
                    .filter(Objects::nonNull)
                    .peek(f -> f.setParentId(metadata.getId()))
                    .forEach(f -> metadata.getChildren().add(f));
        }

        return metadata;
    }

    /**
     * 将 {@link Metadata} 数组转换为 Map
     *
     * @param metadatas Metadata 数组
     *
     * @return 元数据 Map，key 为 Metadata 的 key，value 为 Metadata 的 value
     */
    public static Map<String, String> toMap(Metadata[] metadatas) {
        Map<String, String> result = new LinkedHashMap<>();

        if (metadatas == null || metadatas.length == 0) {
            return result;
        }

        Arrays.stream(metadatas)
                .filter(Objects::nonNull)
                .forEach(metadata -> result.put(metadata.key(), metadata.value()));

        return result;
    }

    /**
     * 解析带有 {@link MetadataElements} 或 {@link Metadata} 注解的元素，收集所有 {@link Metadata} 注解并返回 Map
     *
     * @param targetClass 目标类
     *
     * @return 元数据 Map，key 为 Metadata 的 key，value 为 Metadata 的 value
     */
    public static Map<String, String> parseMetadata(Class<?> targetClass) {
        Map<String, String> result = new LinkedHashMap<>();

        if (targetClass == null) {
            return result;
        }

        // 解析类上的注解
        collectMetadataFromElement(targetClass, result);

        // 解析字段上的注解
        ReflectionUtils.doWithFields(targetClass,
                                     field -> collectMetadataFromElement(field, result));

        // 解析方法上的注解
        // 由于 collectMetadataFromElement 内部使用 MergedAnnotations 会自动处理容器注解，
        // 遍历所有方法即可，没有注解的方法会返回空流，不影响性能
        ReflectionUtils.doWithMethods(targetClass,
                                      method -> collectMetadataFromElement(method, result),
                                      org.springframework.util.ReflectionUtils.USER_DECLARED_METHODS);

        return result;
    }


    /**
     * 从注解元素上收集元数据注解
     *
     * @param element 注解元素（可以是 Class、Field、Method 等）
     * @param result  结果 Map
     */
    @SuppressWarnings("null")
    private static void collectMetadataFromElement(
            AnnotatedElement element,
            Map<String, String> result
    ) {
        if (element == null) {
            return;
        }
        // 使用 MergedAnnotations API 获取所有 @Metadata 注解（包括可重复注解和容器注解）
        MergedAnnotations.from(element)
                .stream(Metadata.class)
                .map(MergedAnnotation::synthesize)
                .forEach(m -> result.put(m.key(), m.value()));
    }
}

