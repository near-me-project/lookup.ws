package near.me.lookup.controller;


import near.me.lookup.controller.model.request.IncomeAdviceRequestDto;
import near.me.lookup.controller.model.request.LocationRequestModel;
import near.me.lookup.controller.model.response.LocationModel;
import near.me.lookup.service.AdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class NearLocationFinderController {

    @Autowired
    private AdviceService adviceService;

    @GetMapping(path = "/location/nearby/{clientId}")
    public List<LocationModel> getNearbyLocations(@PathVariable("clientId") String clientId,
                                                  @RequestParam("latitude") String latitude,
                                                  @RequestParam("longitude") String longitude,
                                                  @RequestParam(value = "radius", required = false, defaultValue = "10.0") String radius) {

        System.out.println("Received request with data - clientId: [" + clientId + "]  latitude: [" + latitude + "]  longitude: [" + longitude + "]");

        final IncomeAdviceRequestDto message = IncomeAdviceRequestDto.builder()
                .clientId(clientId)
                .longitude(new BigDecimal(longitude))
                .latitude(new BigDecimal(latitude))
                .radius(new Double(radius))
                .build();

        final List<LocationRequestModel> locationRequestModel = adviceService.processAdviceRequest(message);

        return locationRequestModel.stream()
                .map(this::map)
                .peek(l -> System.out.println("Found: latitude:[" + l.getLatitude() + "] longitude: [" + l.getLongitude() + "]"))
                .collect(Collectors.toList());
    }

    private LocationModel map(LocationRequestModel dto) {
        final LocationModel locationModel = new LocationModel();
        locationModel.setLatitude(dto.getLatitude());
        locationModel.setLongitude(dto.getLongitude());
        return locationModel;
    }
}
