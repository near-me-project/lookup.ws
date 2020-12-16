package near.me.lookup.service.impl;

import near.me.lookup.controller.model.request.FilterCriteria;
import near.me.lookup.repository.entity.Location;
import near.me.lookup.service.LocationService;
import near.me.lookup.service.domain.LocationDto;
import near.me.lookup.shared.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterCriteriaService {

    private LocationService locationService;
    private MongoTemplate mongoTemplate;

    @Autowired
    public FilterCriteriaService(LocationService locationService, MongoTemplate mongoTemplate) {
        this.locationService = locationService;
        this.mongoTemplate = mongoTemplate;
    }

    public List<LocationDto> findLocationsFiltering(String userId, FilterCriteria filter) {
        if (filter == null || filter.getCity() == null && filter.getCountry() == null) return locationService.findAll(userId);

        Criteria criteria = new Criteria();
        String sortField = "";

        if (filter.getCity() != null && filter.getCountry() != null && filter.getDescription() != null) {
            criteria = Criteria.where("clientId").is(userId).and("city").is(filter.getCity()).and("country").is(filter.getCountry());
            sortField = "country";
        } else if (filter.getCountry() == null && filter.getCity() != null) {
            criteria = Criteria.where("clientId").is(userId).and("city").is(filter.getCity());
            sortField = "city";
        } else if (filter.getCountry() != null && filter.getCity() == null) {
            criteria = Criteria.where("clientId").is(userId).and("country").is(filter.getCountry());
            sortField = "country";
        }

        return findLocations(sortField, criteria);
    }

    private List<LocationDto> findLocations(String sortField, Criteria criteria) {
        List<Location> locations = mongoTemplate.find(Query.query(criteria).with(Sort.by(Sort.Direction.ASC, sortField)), Location.class);

        return locations.stream().map(l -> ModelMapper.map(l, LocationDto.class)).collect(Collectors.toList());
    }

    public List<LocationDto> findByDescriptionText(String text) {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(text);

        List<Location> locations = mongoTemplate.find(TextQuery.queryText(textCriteria), Location.class);

        return locations.stream().map(l -> ModelMapper.map(l, LocationDto.class)).collect(Collectors.toList());
    }
}

