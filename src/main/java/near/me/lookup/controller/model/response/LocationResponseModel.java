package near.me.lookup.controller.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationResponseModel {

    private String locationId;
    private String clientId;
    private String latitude;
    private String longitude;
}
