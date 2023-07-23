package com.example.manageasset.infrastructure.shared.jsons;

import com.example.manageasset.domain.shared.exceptions.InvalidDataException;
import com.example.manageasset.domain.shared.models.Millisecond;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class MillisecondJson {
    public static class Serializer extends StdSerializer<Millisecond> {
        protected Serializer() {
            this(null);
        }


        public Serializer(Class<Millisecond> t) {
            super(t);
        }

        @Override
        public void serialize(Millisecond value,
                              JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeNumber(value.asLong());
        }
    }

    public static class Deserializer extends StdDeserializer<Millisecond> {
        public Deserializer() {
            this(null);
        }

        protected Deserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Millisecond deserialize(com.fasterxml.jackson.core.JsonParser jsonParser,
                                       DeserializationContext deserializationContext) throws
                IOException {
            try {
                return new Millisecond(jsonParser.getLongValue());
            } catch (InvalidDataException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
