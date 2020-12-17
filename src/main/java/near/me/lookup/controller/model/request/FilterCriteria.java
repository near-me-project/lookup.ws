package near.me.lookup.controller.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilterCriteria {

    private String country;
    private String city;
    private String description;

    public boolean allFieldsEmpty() {
        return country == null && city == null && description == null;
    }
}
