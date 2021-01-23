package near.me.lookup.controller;

import near.me.lookup.controller.model.request.FastLocationRequestModel;
import near.me.lookup.controller.model.request.FilterCriteria;
import near.me.lookup.controller.model.request.LocationRequestModel;
import near.me.lookup.controller.model.response.CreatedLocationResponseModel;
import near.me.lookup.controller.model.response.LocationResponseModel;
import near.me.lookup.controller.model.response.UpdatedLocationResponseModel;
import near.me.lookup.repository.QueryLocationRepositoryImpl;
import near.me.lookup.repository.entity.LocationType;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/locations")
public class LocationController {

    private LocationService locationService;
    private QueryLocationRepositoryImpl queryLocationRepositoryImpl;

    @Autowired
    public LocationController(LocationService locationService, QueryLocationRepositoryImpl queryLocationRepositoryImpl) {
        this.locationService = locationService;
        this.queryLocationRepositoryImpl = queryLocationRepositoryImpl;
    }

    @PostMapping
    public ResponseEntity<CreatedLocationResponseModel> addLocation(@RequestBody LocationRequestModel locationRequestModel) {
        final Optional<String> locationId  = locationService.addLocation(map(locationRequestModel));

        return locationId
                .map(id -> new ResponseEntity<>(CreatedLocationResponseModel.builder().createdLocationId(id).build(), HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.OK));
    }

    @PostMapping(path = "/fast")
    public ResponseEntity<CreatedLocationResponseModel> addFastLocation(@RequestBody FastLocationRequestModel locationRequestModel) {

        System.out.println("Location: lat: " + locationRequestModel.getLatitude() + " lon: " + locationRequestModel.getLongitude() + " uuid: " + locationRequestModel.getUuid());

        final Optional<String> locationId = locationService.addLocation(ModelMapper.map(locationRequestModel, LocationRequestDto.class));

        return locationId
                .map(id -> new ResponseEntity<>(CreatedLocationResponseModel.builder().createdLocationId(id).build(), HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.OK));
    }

    @PutMapping(path = "/{locationId}")
    public ResponseEntity<UpdatedLocationResponseModel> updateLocation(@PathVariable(name = "locationId") String locationId, @RequestBody LocationRequestModel locationRequestModel) {
        final String updatedLocationId = queryLocationRepositoryImpl.updateLocation(locationId, map(locationRequestModel));
        return new ResponseEntity<>(UpdatedLocationResponseModel.builder().updatedLocationId(updatedLocationId).build(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{locationId}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteLocation(@PathVariable(name = "locationId") String locationId) {
        queryLocationRepositoryImpl.deleteLocation(locationId);
    }

    @GetMapping(path = "/for/{userId}")
    public List<LocationResponseModel> getLocations(@PathVariable(name = "userId") String userId, FilterCriteria filter) {
        System.out.println("getLocations: for user id " + userId);
        List<LocationDto> all = queryLocationRepositoryImpl.findLocationsFiltering(userId, filter);
        return all.stream().map(dto -> ModelMapper.map(dto, LocationResponseModel.class)).collect(Collectors.toList());
    }

    @GetMapping(path = "/{locationId}")
    public LocationResponseModel getLocation(@PathVariable(name = "locationId") String locationId) {
        LocationDto dto = locationService.findByLocationId(locationId);
        return ModelMapper.map(dto, LocationResponseModel.class);
    }

    private LocationRequestDto map(LocationRequestModel model) {
        return LocationRequestDto.builder()
                .clientId(model.getClientId())
                .longitude(new BigDecimal(model.getLongitude()))
                .latitude(new BigDecimal(model.getLatitude()))
                .city(model.getCity())
                .country(model.getCountry())
                .description(model.getDescription())
                .locationType(LocationType.fromString(model.getLocationType()))
                .clientDefinedLocationType(model.getClientDefinedLocationType())
                .build();
    }
}
