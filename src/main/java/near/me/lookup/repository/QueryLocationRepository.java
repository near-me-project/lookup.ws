package near.me.lookup.repository;

import near.me.lookup.controller.model.request.FilterCriteria;
import near.me.lookup.repository.querybuilders.CriteriaBuilder;
import near.me.lookup.repository.entity.Location;
import near.me.lookup.service.LocationService;
import near.me.lookup.service.domain.LocationDto;
import near.me.lookup.shared.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static near.me.lookup.repository.querybuilders.Contains.contains;
import static near.me.lookup.repository.querybuilders.Is.is;

@Repository
public class QueryLocationRepository {

    private LocationService locationService;
    private MongoTemplate mongoTemplate;

    @Autowired
    public QueryLocationRepository(LocationService locationService, MongoTemplate mongoTemplate) {
        this.locationService = locationService;
        this.mongoTemplate = mongoTemplate;
    }

    public List<LocationDto> findLocationsFiltering(String userId, FilterCriteria filter) {
        if (filter == null || filter.allFieldsEmpty()) return locationService.findAll(userId);

        Criteria criteria = new CriteriaBuilder()
                .where("clientId", is(userId))
                .and("city", is(filter.getCity()))
                .and("country", is(filter.getCountry()))
                .and("description", contains(filter.getDescription()))
                .$();


        if (false) {
            TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(filter.getDescription());
            List<Location> locations = mongoTemplate.find(Query.query(criteria).addCriteria(textCriteria), Location.class);
            return locations.stream().map(l -> ModelMapper.map(l, LocationDto.class)).collect(Collectors.toList());

        } else {
            List<Location> locations = mongoTemplate.find(Query.query(criteria), Location.class);
            return locations.stream().map(l -> ModelMapper.map(l, LocationDto.class)).collect(Collectors.toList());
        }
    }
}

