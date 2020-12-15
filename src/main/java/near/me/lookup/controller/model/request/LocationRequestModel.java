package near.me.lookup.controller.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequestModel {

    private String clientId;
    private String latitude;
    private String longitude;
    private String city;
    private String country;
    private String locationType;
    private String clientDefinedLocationType;
    private String description;
}
