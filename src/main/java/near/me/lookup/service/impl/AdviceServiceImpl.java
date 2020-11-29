package near.me.lookup.service.impl;

import near.me.lookup.controller.model.request.IncomeAdviceRequestDto;
import near.me.lookup.controller.model.request.LocationRequestModel;
import near.me.lookup.repository.LocationRepository;
import near.me.lookup.repository.entity.LocationEntity;
import near.me.lookup.service.AdviceService;
import near.me.lookup.service.domain.LocationFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdviceServiceImpl implements AdviceService {

    private LocationRepository locationRepository;
    private LocationFinder locationFinder;

    @Autowired
    public AdviceServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
        this.locationFinder = new LocationFinder();
    }

    @Override
    public List<LocationRequestModel> processAdviceRequest(IncomeAdviceRequestDto event) {
        final List<LocationEntity> allLocations = locationRepository.findByClientId(event.getClientId());

        return allLocations.stream()
                .filter(entity -> closeEnoughToTarget(entity, event.getLongitude(), event.getLatitude(), event.getRadius()))
                .map(this::map)
                .collect(Collectors.toList());
    }

    private LocationRequestModel map(LocationEntity entity) {
        return LocationRequestModel.builder()
                .clientId(entity.getClientId())
                .latitude(entity.getLatitude().toPlainString())
                .longitude(entity.getLongitude().toPlainString())
                .build();
    }

    private boolean closeEnoughToTarget(LocationEntity entity, BigDecimal targetLongitude, BigDecimal targetLatitude, Double radius) {
        return locationFinder.isCloseEnough(entity.getLatitude(), entity.getLongitude(), targetLongitude, targetLatitude, radius);
    }
}
