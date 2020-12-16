package near.me.lookup.repository.querybuilders;

import org.springframework.data.mongodb.core.query.Criteria;

public interface CriteriaAction {
    boolean isEmpty();

    Criteria applyAnd(Criteria criteria, String key);

    Criteria applyWhere(Criteria criteria, String key);
}
