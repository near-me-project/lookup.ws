package near.me.lookup.service.clients;

import circuit.breaker.SilentResponse;
import near.me.common.LocationInfoRequestModel;
import near.me.lookup.config.RabbitConfig;
import near.me.lookup.service.messaging.RabbitClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceClient extends ServiceClient {
    private RabbitClient rabbitClient;

    @Autowired
    public InfoServiceClient(Environment environment, RabbitClient rabbitClient) {
        super(environment);
        this.environment = environment;
        this.rabbitClient = rabbitClient;
    }

    public void allocateNewPlace(LocationInfoRequestModel locationInfoRequestModel) {
        restClient.async.POST(super.getInfoServiceUrl(), locationInfoRequestModel).thenAccept((response) -> putMessageIntoQueue(response, locationInfoRequestModel));
    }

    private void putMessageIntoQueue(SilentResponse response, LocationInfoRequestModel locationInfoRequestModel) {
        if (response.getStatusCode().orElse(0) != 200)
            rabbitClient.sendEvent(locationInfoRequestModel, RabbitConfig.INFO_SERVICE_QUEUE);
    }
}
