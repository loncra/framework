package io.github.loncra.framework.commons.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.exception.SystemException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 名称枚举的反序列化实现
 *
 * @param <T> 继承自 {@link NameEnum} 的泛型类型
 *
 * @author maurice.chen
 */
public class NameEnumDeserializer<T extends NameEnum> extends JsonDeserializer<T> implements ContextualDeserializer {

    private Class<?> targetType;

    @Override
    public T deserialize(
            JsonParser p,
            DeserializationContext ctxt
    ) throws IOException {
        JsonNode jsonNode = p.getCodec().readTree(p);

        String nodeValue = NameValueEnumDeserializer.getNodeValue(jsonNode);
        List<NameEnum> valueEnums = Arrays
                .stream(targetType.getEnumConstants())
                .map(v -> CastUtils.cast(v, NameEnum.class))
                .toList();

        Optional<NameEnum> optional = valueEnums
                .stream()
                .filter(v -> v.getName().equals(nodeValue))
                .findFirst();

        if (optional.isEmpty()) {
            optional = valueEnums
                    .stream()
                    .filter(v -> v.toString().equals(nodeValue))
                    .findFirst();
        }

        NameEnum result = optional
                .orElseThrow(() -> new SystemException("在类型 [" + targetType + "] 枚举里找不到值为 [" + nodeValue + "] 的类型"));

        return CastUtils.cast(result);
    }

    @Override
    public JsonDeserializer<?> createContextual(
            DeserializationContext ctxt,
            BeanProperty property
    ) {
        this.targetType = NameValueEnumDeserializer.getCurrentTargetType(ctxt, property);
        return this;
    }
}
