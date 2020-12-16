package near.me.lookup.repository.querybuilders;

import org.springframework.data.mongodb.core.query.Criteria;

public class Contains implements CriteriaAction {
    public static Contains contains(String value) {
        return new Contains(value);
    }

    private String value;

    private Contains(String value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public Criteria applyAnd(Criteria criteria, String key) {
        criteria.and(key).regex(".*" + value + ".*");
        return criteria;
    }

    public Criteria applyWhere(Criteria criteria, String key) {
        return new Criteria(key).regex(".*" + value + ".*");
    }
}
