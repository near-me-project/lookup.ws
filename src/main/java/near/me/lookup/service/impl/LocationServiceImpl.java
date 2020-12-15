package near.me.lookup.service.impl;

import near.me.common.LocationRequestEvent;
import near.me.lookup.repository.LocationRepository;
import near.me.lookup.repository.entity.Location;
import near.me.lookup.repository.entity.LocationType;
import near.me.lookup.service.LocationService;
import near.me.lookup.service.domain.LocationDto;
import near.me.lookup.service.domain.LocationRequestDto;
import near.me.lookup.service.messaging.RabbitClient;
import near.me.lookup.shared.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;
    private RabbitClient rabbitClient;
    private MongoTemplate mongoTemplate;


    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, RabbitClient rabbitClient, MongoTemplate mongoTemplate) {
        this.locationRepository = locationRepository;
        this.rabbitClient = rabbitClient;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public String addLocation(LocationRequestDto locationRequestDto) {
        locationRequestDto.setLocationId(UUID.randomUUID().toString());
        Location location = ModelMapper.map(locationRequestDto, Location.class);
        location.setCreatedAt(LocalDate.now());
        if (locationRequestDto.getLocationType() == null) location.setLocationType(LocationType.PRIVATE);

        final Location savedEntity = locationRepository.save(location);

        LocationRequestEvent locationRequestEvent = LocationRequestEvent.builder()
                .clientId(savedEntity.getClientId())
                .city(locationRequestDto.getCity())
                .country(locationRequestDto.getCountry())
                .latitude(savedEntity.getLatitude().toPlainString())
                .longitude(savedEntity.getLongitude().toPlainString())
                .build();

        rabbitClient.sendEventToQueue(locationRequestEvent, RabbitClient.SOCIAL_NETWORK_QUEUE_ADD_LOCATION_EVENT);

        return savedEntity.getLocationId();
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
    public List<LocationDto> findLocations(Criteria criteria) {
        List<Location> locations = mongoTemplate.find(Query.query(criteria), Location.class);

        return locations.stream().map(l -> ModelMapper.map(l, LocationDto.class)).collect(Collectors.toList());
    }
}
