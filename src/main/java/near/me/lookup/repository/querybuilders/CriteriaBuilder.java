package near.me.lookup.repository.querybuilders;

import org.springframework.data.mongodb.core.query.Criteria;

public class CriteriaBuilder {
    private Criteria criteria;

    public CriteriaBuilder() {
        criteria = new Criteria();
    }

    public CriteriaBuilder and(String key, CriteriaAction criteriaAction) {
        if (!criteriaAction.isEmpty()) criteriaAction.applyAnd(criteria, key);
        return this;
    }

    public CriteriaBuilder where(String key, CriteriaAction criteriaAction) {
        if (!criteriaAction.isEmpty()) criteria = criteriaAction.applyWhere(criteria, key);
        return this;
    }

    public Criteria $() {
        return criteria;
    }
}
