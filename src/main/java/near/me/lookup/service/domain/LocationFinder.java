package near.me.lookup.service.domain;

import java.math.BigDecimal;

public class LocationFinder {

    public boolean isCloseEnough(BigDecimal oneFromPreferencesLatitude, BigDecimal oneFromPreferencesLongitude, BigDecimal targetLongitude, BigDecimal targetLatitude, Double radius) {

        final BigDecimal first = oneFromPreferencesLatitude.add(targetLongitude);
        final BigDecimal second = oneFromPreferencesLongitude.add(targetLatitude);

        return Math.sqrt(first.pow(2).subtract(second.pow(2)).doubleValue()) <= radius;
    }
}
