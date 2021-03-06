package near.me.lookup.repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import near.me.lookup.controller.model.request.FilterCriteria;
import near.me.lookup.repository.entity.Location;
import near.me.lookup.repository.querybuilders.CriteriaBuilder;
import near.me.lookup.service.domain.LocationDto;
import near.me.lookup.service.domain.LocationRequestDto;
import near.me.lookup.shared.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static near.me.lookup.repository.querybuilders.Contains.contains;
import static near.me.lookup.repository.querybuilders.Is.is;

@Service
public class QueryLocationRepositoryImpl {

    private MongoTemplate mongoTemplate;

    @Autowired
    public QueryLocationRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<LocationDto> findLocationsFiltering(String userId, FilterCriteria filter) {
//        if (filter == null || filter.allFieldsEmpty()) return locationService.findAll(userId);

        Criteria criteria = new CriteriaBuilder()
                .where("clientId", is(userId))
                .and("city", is(filter.getCity()))
                .and("country", is(filter.getCountry()))
                .and("description", contains(filter.getDescription()))
                .$();

        List<Location> locations = mongoTemplate.find(Query.query(criteria), Location.class);
        return locations.stream().map(l -> ModelMapper.map(l, LocationDto.class)).collect(Collectors.toList());

    }

    public String updateLocation(String locationId, LocationRequestDto dto) {

        Query query = Query.query(Criteria.where("locationId").is(locationId));
        Update update = new Update();
        set(update, "latitude", dto.getLatitude().toPlainString());
        set(update, "longitude", dto.getLongitude().toPlainString());
        set(update, "city", dto.getCity());
        set(update, "country", dto.getCountry());
        set(update, "locationType", dto.getLocationType().toString());
        set(update, "clientDefinedLocationType", dto.getClientDefinedLocationType());
        set(update, "description", dto.getDescription());

        UpdateResult result = mongoTemplate.upsert(query, update, Location.class);
        if (!result.wasAcknowledged() || result.getModifiedCount() == 0)
            throw new RuntimeException("Location wasn't updated. " + locationId);
        return locationId;
    }

    public void deleteLocation(String locationId) {
        Query query = Query.query(Criteria.where("locationId").is(locationId));
        DeleteResult result = mongoTemplate.remove(query, Location.class);
        if (!result.wasAcknowledged() || result.getDeletedCount() == 0)
            throw new RuntimeException("Location wasn't deleted. " + locationId);
    }

    private void set(Update update, String key, String value) {
        if (value != null) update.set(key, value);
    }

    public List<LocationDto> findLocationsByCriteria(CriteriaBuilder criteriaBuilder) {
        List<Location> locations = mongoTemplate.find(Query.query(criteriaBuilder.$()), Location.class);
        return locations.stream().map(l -> ModelMapper.map(l, LocationDto.class)).collect(Collectors.toList());
    }
}