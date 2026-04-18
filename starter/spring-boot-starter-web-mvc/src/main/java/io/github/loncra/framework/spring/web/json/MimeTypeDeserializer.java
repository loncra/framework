package io.github.loncra.framework.spring.web.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;

public class MimeTypeDeserializer extends JsonDeserializer<MimeType> {

    @Override
    public MimeType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        if (node == null || node.isNull() || node.isMissingNode()) {
            return null;
        }
        if (node.isTextual()) {
            String text = node.asText();
            if (text.isEmpty()) {
                return null;
            }
            return MimeTypeUtils.parseMimeType(text);
        }
        if (!node.isObject()) {
            ctxt.reportInputMismatch(MimeType.class, "Expected object or string for MimeType, got %s", node.getNodeType());
        }
        JsonNode typeNode = node.get("type");
        JsonNode subtypeNode = node.get("subtype");
        if (typeNode == null || !typeNode.isTextual() || subtypeNode == null || !subtypeNode.isTextual()) {
            ctxt.reportInputMismatch(MimeType.class, "MimeType object requires textual \"type\" and \"subtype\"");
        }
        return new MimeType(typeNode.asText(), subtypeNode.asText());
    }
}
