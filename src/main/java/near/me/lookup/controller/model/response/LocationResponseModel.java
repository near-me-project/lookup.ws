package near.me.lookup.controller.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import near.me.lookup.repository.entity.LocationType;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class LocationResponseModel {

    private String locationId;
    private String clientId;
    private String latitude;
    private String longitude;
    private String country;
    private String city;
    private LocationType locationType;
    private String description;
    private String clientDefinedLocationType;
}
