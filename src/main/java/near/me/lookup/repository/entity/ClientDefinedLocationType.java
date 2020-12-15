package near.me.lookup.repository.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDefinedLocationType {
    private String type;
    private String clientDefinedLocationType;

    public ClientDefinedLocationType(String clientDefinedLocationType) {
        this.clientDefinedLocationType = clientDefinedLocationType;
    }
}
