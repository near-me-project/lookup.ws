package near.me.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import near.me.lookup.controller.model.request.LocationRequestModel;


import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class IncomeAdviceResponseEvent {

    private List<LocationRequestModel> foundLocations;

}
