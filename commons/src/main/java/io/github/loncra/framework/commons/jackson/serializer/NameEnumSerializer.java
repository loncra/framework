package io.github.loncra.framework.commons.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.enumerate.ValueEnum;

import java.io.IOException;

/**
 * 名称枚举序列化实现
 *
 * @author maurice.chen
 */
public class NameEnumSerializer extends JsonSerializer<NameEnum> {

    @Override
    public void serialize(
            NameEnum value,
            JsonGenerator gen,
            SerializerProvider serializers
    ) throws IOException {
        gen.writeStartObject();

        gen.writeStringField(NameEnum.FIELD_NAME, value.toString());
        gen.writeStringField(ValueEnum.FIELD_NAME, value.getName());

        gen.writeEndObject();
    }
}
