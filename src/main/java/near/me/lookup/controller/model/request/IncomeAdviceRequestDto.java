package near.me.lookup.controller.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class IncomeAdviceRequestDto {
    private String clientId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Double radius;
}
