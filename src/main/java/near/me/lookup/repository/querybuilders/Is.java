package near.me.lookup.repository.querybuilders;

import org.springframework.data.mongodb.core.query.Criteria;

public class Is implements CriteriaAction {
    public static Is is(String value) {
        return new Is(value);
    }

    private String value;

    private Is(String value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public Criteria applyAnd(Criteria criteria, String key) {
        criteria.and(key).is(value);
        return criteria;
    }

    public Criteria applyWhere(Criteria criteria, String key) {
        return new Criteria(key).is(value);
    }
}
