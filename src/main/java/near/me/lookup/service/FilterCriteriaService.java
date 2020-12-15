package near.me.lookup.service;

import near.me.lookup.controller.model.request.FilterCriteria;
import near.me.lookup.service.domain.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterCriteriaService {

    private LocationService locationService;

    @Autowired
    public FilterCriteriaService(LocationService locationService) {
        this.locationService = locationService;
    }

    public List<LocationDto> findLocationsFiltering(String userId, FilterCriteria filter) {
        if (filter == null || filter.getCity() == null && filter.getCountry() == null) return locationService.findAll(userId);

        Criteria criteria = new Criteria();

        if (filter.getCity() != null && filter.getCountry() != null) {
            criteria = Criteria.where("clientId").is(userId).and("city").is(filter.getCity()).and("country").is(filter.getCountry());
        } else if (filter.getCountry() == null && filter.getCity() != null) {
            criteria = Criteria.where("clientId").is(userId).and("city").is(filter.getCity());
        } else if (filter.getCountry() != null && filter.getCity() == null) {
            criteria = Criteria.where("clientId").is(userId).and("country").is(filter.getCountry());
        }

        return locationService.findLocations(criteria);
    }
}

