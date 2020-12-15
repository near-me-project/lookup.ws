package near.me.lookup.service;

import near.me.lookup.service.domain.LocationDto;
import near.me.lookup.service.domain.LocationRequestDto;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface LocationService {

    String addLocation(LocationRequestDto locationRequestDto);

    List<LocationDto> findLocationsByClientId(String userId);

    List<LocationDto> findAll(String userId);

    <T> List<LocationDto> findLocations(Criteria criteria);
}
