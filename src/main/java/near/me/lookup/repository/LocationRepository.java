package near.me.lookup.repository;

import near.me.lookup.repository.entity.LocationEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, Long> {
    List<LocationEntity> findByClientId(String clientId);
}
