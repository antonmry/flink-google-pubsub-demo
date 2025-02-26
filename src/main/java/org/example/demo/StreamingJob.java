package org.example.demo;

import java.util.Map;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.gcp.pubsub.PubSubSource;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.example.demo.model.Alert;
import org.example.demo.model.AlertMatch;

public class StreamingJob {

    public static void main(String[] args) throws Exception {
        // Set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(4000);

        // Set up a Cloud Pub/Sub topic as input

        // deserializer controls how Apache Flink deserializes the message
        // returns from Cloud Pub/Sub (a byte string) to a PubSubEvent
        // (custom Java class). See PubSubEvent.java for definition.
        PlayCountEventDeserializationSchema deserializer = new PlayCountEventDeserializationSchema();

        Map<String, String> envVars = System.getenv();
        String projectId = envVars.get("GCP_PROJECT");
        String subscription = envVars.get("PUBSUB_SUBSCRIPTION");

        SourceFunction<AlertMatch> pubSubSource = PubSubSource.newBuilder()
                .withDeserializationSchema(deserializer)
                .withProjectName(projectId)
                .withSubscriptionName(subscription)
                .build();

        DataStream<AlertMatch> dataStream = env.addSource(pubSubSource);

        dataStream.print();

        env.execute("Collecting play counts");
    }
}
