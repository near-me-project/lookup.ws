package near.me.lookup.service.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import near.me.lookup.shared.JsonMapper;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class RabbitClient {

    public static final String INFO_SERVICE_QUEUE = "info-service-queue";
    public static final String SOCIAL_NETWORK_QUEUE_ADD_LOCATION_EVENT = "social_network_queue_adding_location_event";

    private final ConnectionFactory factory;

    public RabbitClient() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
    }

    public <T> void sendEventToQueue(T model, String queue) {
        CompletableFuture.runAsync(() -> send(model, queue));
    }

    protected <T> void send(T model, String queue) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            System.out.println("RABBITMQ: Sending event to queue [ " + queue + " ]. " + JsonMapper.parseAsString(model));

            channel.basicPublish("", queue, null, JsonMapper.parse(model));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
