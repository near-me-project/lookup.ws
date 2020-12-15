package near.me.lookup.repository.entity;

public enum LocationType {
    PRIVATE("private"), PUBLIC("public");

    private String type;

    LocationType(String type) {
        this.type = type;
    }

    public static LocationType fromString(String locationType) {
        for (LocationType value : LocationType.values()) {
            if (value.type.equals(locationType)) return value;
        }
        throw new RuntimeException("Not defined location type: " + locationType);
    }


    @Override
    public String toString() {
        return type;
    }
}
