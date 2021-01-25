package near.me.lookup.service.impl;

import near.me.lookup.repository.LocationRepository;
import near.me.lookup.repository.QueryLocationRepositoryImpl;
import near.me.lookup.repository.entity.Location;
import near.me.lookup.repository.entity.LocationType;
import near.me.lookup.repository.querybuilders.CriteriaBuilder;
import near.me.lookup.service.LocationService;
import near.me.lookup.service.domain.LocationDto;
import near.me.lookup.service.domain.GpsLocationUtils;
import near.me.lookup.service.domain.LocationRequestDto;
import near.me.lookup.shared.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static near.me.lookup.repository.querybuilders.Is.is;

@Service
public class LocationServiceImpl implements LocationService {

    private QueryLocationRepositoryImpl queryLocationRepositoryImpl;
    private LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(QueryLocationRepositoryImpl repository, LocationRepository locationRepository) {
        this.queryLocationRepositoryImpl = repository;
        this.locationRepository = locationRepository;
    }

    @Override
    public Optional<String> addLocation(LocationRequestDto locationRequestDto) {

        CriteriaBuilder builder = new CriteriaBuilder().where("uuid", is(locationRequestDto.getUuid()));

        if (!queryLocationRepositoryImpl.findLocationsByCriteria(builder).isEmpty()) return Optional.empty();


        boolean isClose = locationRepository
                .findByClientId(locationRequestDto.getClientId())
                .stream()
                .anyMatch(dbLoc -> veryCloseTo(dbLoc, locationRequestDto));

        if (isClose) return Optional.empty();

        locationRequestDto.setLocationId(UUID.randomUUID().toString());
        Location location = ModelMapper.map(locationRequestDto, Location.class);
        location.setCreatedAt(LocalDateTime.now());
        if (locationRequestDto.getLocationType() == null) location.setLocationType(LocationType.PRIVATE);

        final Location savedEntity = locationRepository.save(location);

        return Optional.of(savedEntity.getLocationId());
    }

    private boolean veryCloseTo(Location dbLoc, LocationRequestDto locationRequestDto) {

        BigDecimal x1 = dbLoc.getLatitude();
        BigDecimal y1 = dbLoc.getLongitude();
        BigDecimal x2 = locationRequestDto.getLatitude();
        BigDecimal y2 = locationRequestDto.getLongitude();

        return new GpsLocationUtils().isCloseEnough(x1, y1, x2, y2, 50.0);
    }

    @Override
    public List<LocationDto> findLocationsByClientId(String clientId) {
        final List<Location> entityList = locationRepository.findByClientId(clientId);
        return entityList.stream().map(entity -> ModelMapper.map(entity, LocationDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<LocationDto> findAll(String clientId) {
        List<Location> locations = locationRepository.findByClientId(clientId);
        return locations.stream().map(l -> ModelMapper.map(l, LocationDto.class)).collect(Collectors.toList());
    }

    @Override
    public LocationDto findByLocationId(String locationId) {
        Location location = locationRepository.findByLocationId(locationId);
        return ModelMapper.map(location, LocationDto.class);
    }
}
