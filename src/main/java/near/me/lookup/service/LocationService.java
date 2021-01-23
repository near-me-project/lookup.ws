package near.me.lookup.service;

import near.me.lookup.service.domain.LocationDto;
import near.me.lookup.service.domain.LocationRequestDto;

import java.util.List;
import java.util.Optional;

public interface LocationService {

    Optional<String> addLocation(LocationRequestDto locationRequestDto);

    List<LocationDto> findLocationsByClientId(String userId);

    List<LocationDto> findAll(String userId);

    LocationDto findByLocationId(String locationId);
}
