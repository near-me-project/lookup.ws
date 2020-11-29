package near.me.common;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeAdviceRequestEvent {

    private String clientId;
    private String latitude;
    private String longitude;
    private String radius;

}
