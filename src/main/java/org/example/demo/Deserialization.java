package org.example.demo;

import java.io.IOException;

import org.apache.flink.api.common.serialization.AbstractDeserializationSchema;

import static com.fasterxml.jackson.module.kotlin.ExtensionsKt.jacksonObjectMapper;

class PlayCountEventDeserializationSchema extends AbstractDeserializationSchema<PubSubEvent> {
    static final long serialVersionUID = 1L;

    public PubSubEvent deserialize(byte[] message) throws IOException {
        return jacksonObjectMapper().readValue(new String(message), PubSubEvent.class);
    }
}