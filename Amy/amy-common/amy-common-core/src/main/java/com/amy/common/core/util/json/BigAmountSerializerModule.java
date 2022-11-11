package com.amy.common.core.util.json;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.math.BigDecimal;


/**
 * Never forget why you started.
 *
 * @author weile
 * @date 2022-05-20 22:25:04
 */
public class BigAmountSerializerModule extends SimpleModule {

    private static final long serialVersionUID = 1L;
    private final boolean twoFraction;

    public BigAmountSerializerModule(boolean twoFraction) {
        super("BigAmountJsonSerializerModule", Version.unknownVersion());
        this.addSerializer(BigDecimal.class, new BigDecimalSerializer());
        this.addDeserializer(BigDecimal.class, new BigDecimalDeserializer());
        this.twoFraction = twoFraction;
    }

    public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

        @Override
        public void serialize(BigDecimal value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            if (value == null) {
                jgen.writeNull();
            } else {
                if (twoFraction) {
                    value = value.setScale(2, BigDecimal.ROUND_HALF_UP);
                }
                jgen.writeString(value.toString());
            }
        }
    }

    public class BigDecimalDeserializer extends JsonDeserializer<BigDecimal> {

        @Override
        public BigDecimal deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
            if (jp.getCurrentToken() == JsonToken.VALUE_NULL) {
                return null;
            } else if (jp.getCurrentToken() == JsonToken.VALUE_STRING) {
                if (jp.getText() == null) {
                    return null;
                }
                String text = jp.getText().trim();
                return new BigDecimal(text);
            } else if (jp.getCurrentToken() == JsonToken.VALUE_NUMBER_FLOAT) {
                return new BigDecimal(jp.getFloatValue());
            } else if (jp.getCurrentToken() == JsonToken.VALUE_NUMBER_INT) {
                return new BigDecimal(jp.getIntValue());
            } else {
                throw new JsonParseException("cannot convert to big decimal", jp.getCurrentLocation());
            }
        }
    }
}
