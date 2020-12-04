package near.me.lookup.service.clients;

import circuit.breaker.RestClient;
import circuit.breaker.UriBuilder;
import org.springframework.core.env.Environment;

public abstract class ServiceClient {

    protected Environment environment;
    protected RestClient restClient;

    public ServiceClient(Environment environment) {
        this.environment = environment;
        this.restClient = new RestClient();
    }

    protected String getInfoServiceUrl() {
        return restClient.GET(new UriBuilder().setPath(environment.getProperty("discovery.url")+"/pathTo").addParameter("client", "info-ws"))
                .executeSilent()
                .getContentAsString()
                .orElse("nothing");
    }
}
