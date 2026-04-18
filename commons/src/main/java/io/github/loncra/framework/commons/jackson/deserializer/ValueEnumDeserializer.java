package io.github.loncra.framework.commons.jackson.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import io.github.loncra.framework.commons.CastUtils;
import io.github.loncra.framework.commons.enumerate.ValueEnum;
import io.github.loncra.framework.commons.exception.SystemException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 值枚举的反序列化实现
 *
 * @param <T> 继承自 {@link ValueEnum} 的泛型类型
 *
 * @author maurice.chen
 */
@SuppressWarnings("rawtypes")
public class ValueEnumDeserializer<T extends ValueEnum> extends JsonDeserializer<T> implements ContextualDeserializer {

    private Class<?> targetType;

    @Override
    public T deserialize(
            JsonParser p,
            DeserializationContext ctxt
    ) throws IOException {
        JsonNode jsonNode = p.getCodec().readTree(p);

        String nodeValue = NameValueEnumDeserializer.getNodeValue(jsonNode);

        List<ValueEnum> valueEnums = Arrays
                .stream(targetType.getEnumConstants())
                .map(v -> CastUtils.cast(v, ValueEnum.class))
                .toList();

        Optional<ValueEnum> optional = valueEnums
                .stream()
                .filter(v -> v.getValue().toString().equals(nodeValue))
                .findFirst();

        if (optional.isEmpty()) {
            optional = valueEnums
                    .stream()
                    .filter(v -> v.toString().equals(nodeValue))
                    .findFirst();
        }

        if (optional.isEmpty()) {
            ValueEnum ve = ValueEnum.ofEnum(CastUtils.cast(targetType), nodeValue, true);
            optional = Optional.ofNullable(ve);
        }

        ValueEnum result = optional
                .orElseThrow(() -> new SystemException("在类型 [" + targetType + "] 枚举里找不到值为 [" + nodeValue + "] 的类型"));

        return CastUtils.cast(result);
    }

    @Override
    public JsonDeserializer<?> createContextual(
            DeserializationContext ctxt,
            BeanProperty property
    ) throws JsonMappingException {
        this.targetType = NameValueEnumDeserializer.getCurrentTargetType(ctxt, property);
        return this;
    }
}
