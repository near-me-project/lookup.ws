package near.me.lookup.controller;

import near.me.lookup.controller.model.request.LocationRequestModel;
import near.me.lookup.controller.model.response.CreatedLocationResponseModel;
import near.me.lookup.controller.model.response.LocationResponseModel;
import near.me.lookup.service.LocationService;
import near.me.lookup.service.domain.LocationDto;
import near.me.lookup.service.domain.LocationRequestDto;
import near.me.lookup.shared.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<CreatedLocationResponseModel> addLocation(@RequestBody LocationRequestModel locationRequestModel) {
        final String locationId = locationService.addLocation(map(locationRequestModel));
        return new ResponseEntity<>(CreatedLocationResponseModel.builder().createdLocationId(locationId).build(), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{userId}")
    public List<LocationResponseModel> getMySavedLocations(@PathVariable(name = "userId") String userId, @RequestParam(name = "filter", required = false, defaultValue = "all") String filter) {
        List<LocationDto> all = locationService.getLocationsByUserId(userId);
        return all.stream().map(dto -> ModelMapper.map(dto, LocationResponseModel.class)).collect(Collectors.toList());
    }

    private LocationRequestDto map(LocationRequestModel model) {
        return LocationRequestDto.builder()
                .clientId(model.getClientId())
                .longitude(new BigDecimal(model.getLongitude()))
                .latitude(new BigDecimal(model.getLatitude()))
                .build();
    }
}
