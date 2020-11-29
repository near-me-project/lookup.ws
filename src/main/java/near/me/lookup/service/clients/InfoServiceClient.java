package near.me.lookup.service.clients;


import circuit.breaker.RestClient;
import circuit.breaker.UriBuilder;
import near.me.common.LocationInfoRequestModel;
import near.me.lookup.config.RabbitConfig;
import near.me.lookup.service.messaging.RabbitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


@Service
public class InfoServiceClient {

    private Environment environment;
    private RestClient restClient;
    private RabbitClient rabbitClient;

    @Autowired
    public InfoServiceClient(Environment environment, RabbitClient rabbitClient) {
        this.environment = environment;
        this.restClient = new RestClient();
        this.rabbitClient = rabbitClient;
    }

    public void allocateNewPlace(LocationInfoRequestModel locationInfoRequestModel) {
        String infoServiceUrl = restClient
                .GET(new UriBuilder().setPath(environment.getProperty("discovery.url")).addParameter("client","info-ws"))
                .executeSilent()
                .getResponse(String.class)
                .orElse("nothing");

        restClient.POST(infoServiceUrl, locationInfoRequestModel).ifFail(() -> putMessageIntoQueue(locationInfoRequestModel));
    }

    private void putMessageIntoQueue(LocationInfoRequestModel locationInfoRequestModel) {
        rabbitClient.sendEvent(locationInfoRequestModel, RabbitConfig.INFO_SERVICE_QUEUE);
    }
}
