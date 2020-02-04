package org.example.demo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.apache.flink.api.common.serialization.AbstractDeserializationSchema;
import org.example.demo.model.Alert;

import java.io.IOException;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

class PlayCountEventDeserializationSchema extends AbstractDeserializationSchema<Alert> {
    static final long serialVersionUID = 1L;

    public Alert deserialize(byte[] message) throws IOException {

        return jacksonObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JodaModule())
                .readValue(new String(message), Alert.class);
    }
}