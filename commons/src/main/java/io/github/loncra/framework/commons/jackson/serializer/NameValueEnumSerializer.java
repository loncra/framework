package io.github.loncra.framework.commons.jackson.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.github.loncra.framework.commons.EnumUtils;
import io.github.loncra.framework.commons.enumerate.NameEnum;
import io.github.loncra.framework.commons.enumerate.NameValueEnum;
import io.github.loncra.framework.commons.enumerate.ValueEnum;

import java.io.IOException;

/**
 * 名称和值的枚举序列化实现
 *
 * @author maurice.chen
 */
@SuppressWarnings("rawtypes")
public class NameValueEnumSerializer extends JsonSerializer<NameValueEnum> {

    @Override
    public void serialize(
            NameValueEnum value,
            JsonGenerator gen,
            SerializerProvider serializers
    ) throws IOException {
        Object enumValue = EnumUtils.getValueByStrategyAnnotation(value);

        gen.writeStartObject();

        gen.writeStringField(NameEnum.FIELD_NAME, value.getName());
        gen.writeObjectField(ValueEnum.FIELD_NAME, enumValue);

        gen.writeEndObject();
    }
}
