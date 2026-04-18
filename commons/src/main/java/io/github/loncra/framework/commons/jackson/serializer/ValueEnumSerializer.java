package io.github.loncra.framework.commons.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.enumerate.ValueEnum;

import java.io.IOException;

/**
 * 值的枚举序列化实现
 *
 * @author maurice.chen
 */
@SuppressWarnings("rawtypes")
public class ValueEnumSerializer extends JsonSerializer<ValueEnum> {

    @Override
    public void serialize(
            ValueEnum value,
            JsonGenerator gen,
            SerializerProvider serializers
    ) throws IOException {
        Object enumValue = value.getValue();

        gen.writeStartObject();

        gen.writeStringField(NameEnum.FIELD_NAME, value.toString());
        gen.writeObjectField(ValueEnum.FIELD_NAME, enumValue);

        gen.writeEndObject();
    }
}
