package near.me.lookup.service.messaging;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import near.me.common.LocationInfoRequestModel;
import near.me.lookup.shared.JsonMapper;
import org.springframework.stereotype.Service;


@Service
public class RabbitClient {

    private final ConnectionFactory factory;

    public RabbitClient() {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
    }

    public void sendEvent(LocationInfoRequestModel model, String queue) {

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            System.out.println("RABBITMQ: Sending event to queue [ " + queue + " ]. " + JsonMapper.parseAsString(model));

            channel.basicPublish("", queue, null, JsonMapper.parse(model));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
