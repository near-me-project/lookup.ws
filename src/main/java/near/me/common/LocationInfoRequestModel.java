package near.me.common;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationInfoRequestModel {

    private String locationId;
    private String clientId;
    private String latitude;
    private String longitude;
}
