package com.akrog.tolomet.utils;

import java.util.Calendar;

public final class DateUtils {
    private DateUtils() {}

    public static void resetDay(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
    }
}
