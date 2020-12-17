package near.me.lookup.service.domain;

import lombok.*;
import near.me.lookup.repository.entity.LocationType;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequestDto {

    private String locationId;
    private String clientId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String city;
    private String country;
    private LocationType locationType;
    private String clientDefinedLocationType;
    private String description;
}
