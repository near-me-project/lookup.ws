package near.me.lookup.repository.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @TextIndexed
    private String description;
    private LocalDateTime createdAt;
}
