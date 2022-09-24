package com.akrog.tolomet.utils;

import java.util.Locale;

public class StringUtils {
    public static String toCsv(String sep, Object... fields) {
        StringBuilder csv = new StringBuilder();
        int count = 0;
        for( Object field : fields ) {
            csv.append(field);
            count++;
            if( count < fields.length )
                csv.append(sep);
        }
        return csv.toString();
    }

    public static String formatDecimal(Number number) {
        if( number == null )
            return "";
        return String.format(Locale.US, "%f", number.doubleValue());
    }
}
