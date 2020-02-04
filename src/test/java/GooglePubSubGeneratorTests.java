import Model.Alert;
import Model.AlertMatch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;
import org.example.demo.model.AlertDestination;
import org.example.demo.model.AlertDestinationType;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GooglePubSubGeneratorTests {

    public static final int GENERATED_MESSAGES = 4;

    @Test
    public void generatePubSubMessages() throws IOException, ExecutionException, InterruptedException {

        String project = System.getenv("GCP_PROJECT");
        String topic = System.getenv("PUBSUB_TOPIC");

        ProjectTopicName topicName = ProjectTopicName.of(project, topic);
        Publisher publisher = null;
        List<ApiFuture<String>> messageIdFutures = new ArrayList<>();

        try {
            publisher = Publisher.newBuilder(topicName).build();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JodaModule());


            List<String> messages = new ArrayList<>();

            for (int i = 0; i < GENERATED_MESSAGES; i++) {
                messages.add(objectMapper.writeValueAsString(generateRandomAlertMatch()));
            }

            for (String message : messages) {
                ByteString data = ByteString.copyFromUtf8(message);
                PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

                ApiFuture<String> messageIdFuture = publisher.publish(pubsubMessage);
                messageIdFutures.add(messageIdFuture);
            }
        } finally {
            List<String> messageIds = ApiFutures.allAsList(messageIdFutures).get();

            for (String messageId : messageIds) {
                System.out.println("published with message ID: " + messageId);
            }

            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }

    private AlertMatch generateRandomAlertMatch() {

        Random r = new Random();

        AlertDestination alertDestination1 = new AlertDestination(
                AlertDestinationType.values()[r.nextInt(AlertDestinationType.values().length)],
                Arrays.asList("destination"),
                "schedule1"
        );
        AlertDestination alertDestination2 = new AlertDestination(
                AlertDestinationType.values()[r.nextInt(AlertDestinationType.values().length)],
                Arrays.asList("destination"),
                "schedule1"
        );

        Alert alert = new Alert("1", "2",
                Arrays.asList("user" + r.nextInt(4) + 1,
                        "user" + r.nextInt(4) + 1),
                true, 3, Arrays.asList(alertDestination1));

        return new AlertMatch(alert, "url1", 1);
    }
}

