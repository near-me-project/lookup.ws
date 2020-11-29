package near.me.lookup.service.impl;

import near.me.common.LocationInfoRequestModel;
import near.me.lookup.repository.LocationRepository;
import near.me.lookup.repository.entity.LocationEntity;
import near.me.lookup.service.LocationService;
import near.me.lookup.service.clients.InfoServiceClient;
import near.me.lookup.service.domain.LocationDto;
import near.me.lookup.service.domain.LocationRequestDto;
import near.me.lookup.shared.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;
    private InfoServiceClient infoServiceClient;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, InfoServiceClient infoServiceClient) {
        this.locationRepository = locationRepository;
        this.infoServiceClient = infoServiceClient;
    }

    @Override
    public String addLocation(LocationRequestDto locationRequestDto) {
        locationRequestDto.setLocationId(UUID.randomUUID().toString());
        final LocationEntity savedEntity = locationRepository.save(ModelMapper.map(locationRequestDto, LocationEntity.class));
        infoServiceClient.allocateNewPlace(ModelMapper.map(savedEntity, LocationInfoRequestModel.class));
        return savedEntity.getLocationId();
    }

    @Override
    public List<LocationDto> getLocationsByUserId(String userId) {
        final List<LocationEntity> entityList = locationRepository.findByClientId(userId);
        return entityList.stream().map(entity -> ModelMapper.map(entity, LocationDto.class)).collect(Collectors.toList());
    }
}
