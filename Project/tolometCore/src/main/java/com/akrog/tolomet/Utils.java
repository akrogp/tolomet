package com.akrog.tolomet;

import com.ibm.util.CoordinateConversion;

import java.util.Locale;

public class Utils {
    public static void utm2ll(Station station, int zone, char band) {
        String utm = String.format(Locale.US, "%d %c %f %f", zone, Character.toUpperCase(band), station.getLongitude(), station.getLatitude());
        CoordinateConversion conv = new CoordinateConversion();
        double[] ll = conv.utm2LatLon(utm);
        station.setLatitude(ll[0]);
        station.setLongitude(ll[1]);
    }

    public static void utm2ll(Station station) {
        utm2ll(station, 30, 'T');
    }
}
