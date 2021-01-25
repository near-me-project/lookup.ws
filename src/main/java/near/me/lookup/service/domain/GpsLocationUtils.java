package near.me.lookup.service.domain;

import java.math.BigDecimal;

public class GpsLocationUtils {
    final double equatorialEarthRadius = 6378.1370D;
    final double degreeToRadian = (Math.PI / 180D);

    public boolean isCloseEnough(BigDecimal x1, BigDecimal y1, BigDecimal x2, BigDecimal y2, Double radius) {
        return haversineFunctionInMeters(x1.doubleValue(), y1.doubleValue(), x2.doubleValue(), y2.doubleValue()) <= radius;
    }

    private double haversineFunctionInMeters(double lat1, double long1, double lat2, double long2) {
        return 1000D * haversineFunctionInKM(lat1, long1, lat2, long2);
    }

    private double haversineFunctionInKM(double lat1, double long1, double lat2, double long2) {
        double degreesLong = (long2 - long1) * degreeToRadian;
        double degreesLat = (lat2 - lat1) * degreeToRadian;
        double a = Math.pow(Math.sin(degreesLat / 2D), 2D) + Math.cos(lat1 * degreeToRadian) * Math.cos(lat2 * degreeToRadian)
                * Math.pow(Math.sin(degreesLong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        return equatorialEarthRadius * c;
    }

}


