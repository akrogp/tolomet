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

    public static String reCapitalize(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        char prev = ' ';
        for( char ch : str.toCharArray() ) {
            if( !Character.isLetter(prev) )
                sb.append(Character.toUpperCase(ch));
            else
                sb.append(Character.toLowerCase(ch));
            prev = ch;
        }
        return sb.toString();
    }
}
