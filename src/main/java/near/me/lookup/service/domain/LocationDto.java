package near.me.lookup.service.domain;


import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class LocationDto {

    private String locationId;
    private String clientId;
    private BigDecimal latitude;
    private BigDecimal longitude;

}
