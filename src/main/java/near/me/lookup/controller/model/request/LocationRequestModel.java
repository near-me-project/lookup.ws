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
}
