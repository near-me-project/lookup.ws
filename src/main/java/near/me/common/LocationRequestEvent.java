package near.me.common;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequestEvent {

    private String clientId;
    private String latitude;
    private String longitude;
    private String city;
    private String country;
}
