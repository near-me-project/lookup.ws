package near.me.lookup.repository;

import near.me.lookup.repository.entity.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
    List<Location> findByClientId(String clientId);
}
