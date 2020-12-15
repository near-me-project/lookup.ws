package near.me.lookup.service.domain;


import lombok.*;
import near.me.lookup.repository.entity.LocationType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class LocationDto {

    private String locationId;
    private String clientId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String country;
    private String city;
    private LocationType locationType;
    private String description;
    private LocalDate createdAt;
    private String clientDefinedLocationType;

}
