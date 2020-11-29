package near.me.lookup.service;


import near.me.lookup.service.domain.LocationDto;
import near.me.lookup.service.domain.LocationRequestDto;

import java.util.List;

public interface LocationService {

    String addLocation(LocationRequestDto locationRequestDto);

    List<LocationDto> getLocationsByUserId(String userId);
}
