package near.me.lookup.service.domain;

import lombok.*;

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
}
