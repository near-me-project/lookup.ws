package near.me.lookup.service;


import near.me.lookup.controller.model.request.IncomeAdviceRequestDto;
import near.me.lookup.controller.model.request.LocationRequestModel;

import java.util.List;

public interface AdviceService {
    List<LocationRequestModel> processAdviceRequest(IncomeAdviceRequestDto event);
}
