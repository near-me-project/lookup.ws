package near.me.lookup.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "locations")
public class Location {

    @Id
    private String id;

    private String locationId;

    private String clientId;
    private BigDecimal latitude;
    private BigDecimal longitude;

    @Indexed
    private String country;
    @Indexed
    private String city;

    private String clientDefinedLocationType;
    private LocationType locationType;
    private String description;
    private LocalDate createdAt;
}
