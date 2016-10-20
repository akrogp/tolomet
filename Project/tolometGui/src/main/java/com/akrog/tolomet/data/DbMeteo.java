package com.akrog.tolomet.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.Meteo;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Tolomet;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gorka on 20/10/16.
 */

public class DbMeteo extends SQLiteAssetHelper {
    public static final String TAB_METEO = "Meteo";
    public static final String COL_MET_STATION = "station";
    public static final String COL_MET_STAMP = "stamp";
    public static final String COL_MET_DIR = "dir";
    public static final String COL_MET_MED = "med";
    public static final String COL_MET_MAX = "max";
    public static final String COL_MET_HUM = "hum";
    public static final String COL_MET_TEMP = "temp";
    public static final String COL_MET_PRES = "pres";

    private DbMeteo() {
        super( Tolomet.getAppContext(), DB_NAME, Tolomet.getAppContext().getExternalCacheDir().getAbsolutePath(), null, DB_VERSION);
    }

    public static synchronized DbMeteo getInstance() {
        if( instance == null )
            instance = new DbMeteo();
        return instance;
    }

    public int refresh(Station station) {
        if( station.getMeteo().isEmpty() )
            return travel(station, System.currentTimeMillis());
        return loadFrom(station, station.getStamp());
    }

    public int travel(Station station, long dayStamp) {
        Calendar from = Calendar.getInstance();
        from.setTimeInMillis(dayStamp);
        from.set(Calendar.HOUR_OF_DAY,0);
        from.set(Calendar.MINUTE,0);
        from.set(Calendar.SECOND,0);
        from.set(Calendar.MILLISECOND,0);

        Calendar to = Calendar.getInstance();
        from.setTimeInMillis(dayStamp);
        from.set(Calendar.HOUR_OF_DAY,23);
        from.set(Calendar.MINUTE,59);
        from.set(Calendar.SECOND,59);
        from.set(Calendar.MILLISECOND,999);

        return load(station, from.getTimeInMillis(), to.getTimeInMillis());
    }

    public int load(Station station, long from, long to) {
        SQLiteDatabase lite = getReadableDatabase();
        Cursor cursor = lite.query(TAB_METEO,null,
                "station=? AND stamp BETWEEN ? AND ?",
                new String[]{station.getId(), String.valueOf(from), String.valueOf(to)},
                null, null, null);
        return loadCursor(cursor, station);
    }

    public int loadFrom(Station station, long stamp) {
        SQLiteDatabase lite = getReadableDatabase();
        Cursor cursor = lite.query(TAB_METEO,null,
                "station=? AND stamp>?",
                new String[]{station.getId(), String.valueOf(stamp)},
                null, null, null);
        return loadCursor(cursor, station);
    }

    public int save(Station station ) {
        String id = station.getId();
        SQLiteDatabase lite = getReadableDatabase();
        Cursor cursor = lite.rawQuery(
                "SELECT MIN(stamp), MAX(stamp) FROM Meteo WHERE station=?",
                new String[]{id});
        cursor.moveToFirst();
        long from = cursor.isNull(0) ? -1 : cursor.getLong(0);
        long to = cursor.isNull(1) ? -1 : cursor.getLong(1);
        cursor.close();
        Map<Long,Values> map = mergeMeteo(station.getMeteo(), from, to);
        if( map.isEmpty() )
            return 0;
        lite = getWritableDatabase();
        lite.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            for( Map.Entry<Long,Values> entry : map.entrySet() ) {
                Values values = entry.getValue();
                contentValues.put(COL_MET_STATION, id);
                contentValues.put(COL_MET_STAMP, entry.getKey());
                contentValues.put(COL_MET_DIR, values.dir);
                contentValues.put(COL_MET_MED, values.med);
                contentValues.put(COL_MET_MAX, values.max);
                contentValues.put(COL_MET_HUM, values.hum);
                contentValues.put(COL_MET_TEMP, values.temp);
                contentValues.put(COL_MET_PRES, values.pres);
                lite.insert(TAB_METEO,null,contentValues);
            }
            lite.setTransactionSuccessful();
        } catch (Exception e) {
            return 0;
        } finally {
            lite.endTransaction();
        }
        return map.size();
    }

    private Map<Long,Values> mergeMeteo(Meteo meteo, long from, long to) {
        Map<Long,Values> map = new HashMap<>(meteo.getWindDirection().size());
        mergeValues(map, from, to, meteo.getWindDirection(), new Merger() {
            @Override public void merge(Values values, Number number) {values.dir = number.intValue();}
        });
        mergeValues(map, from, to, meteo.getWindSpeedMed(), new Merger() {
            @Override public void merge(Values values, Number number) {values.med = number.floatValue();}
        });
        mergeValues(map, from, to, meteo.getWindSpeedMax(), new Merger() {
            @Override public void merge(Values values, Number number) {values.max = number.floatValue();}
        });
        mergeValues(map, from, to, meteo.getAirHumidity(), new Merger() {
            @Override public void merge(Values values, Number number) {values.hum = number.floatValue();}
        });
        mergeValues(map, from, to, meteo.getAirTemperature(), new Merger() {
            @Override public void merge(Values values, Number number) {values.temp = number.floatValue();}
        });
        mergeValues(map, from, to, meteo.getAirPressure(), new Merger() {
            @Override public void merge(Values values, Number number) {values.pres = number.floatValue();}
        });
        return map;
    }

    private void mergeValues(Map<Long,Values> map, long from, long to, Measurement meas, Merger merger) {
        for( Map.Entry<Long,Number> entry : meas.getEntrySet() ) {
            if( from != -1 && entry.getKey() >= from && to != -1 && entry.getKey() <= to )
                continue;
            Values values = map.get(entry.getKey());
            if( values == null ) {
                values = new Values();
                map.put(entry.getKey(), values);
            }
            merger.merge(values, entry.getValue());
        }
    }

    private int loadCursor(Cursor cursor, Station station) {
        int count = cursor.getCount();
        int iStamp = cursor.getColumnIndex(COL_MET_STAMP);
        int iDir = cursor.getColumnIndex(COL_MET_DIR);
        int iMed = cursor.getColumnIndex(COL_MET_MED);
        int iMax = cursor.getColumnIndex(COL_MET_MAX);
        int iHum = cursor.getColumnIndex(COL_MET_HUM);
        int iTemp = cursor.getColumnIndex(COL_MET_TEMP);
        int iPres = cursor.getColumnIndex(COL_MET_PRES);
        while( cursor.moveToNext() ) {
            long stamp = cursor.getLong(iStamp);
            if( !cursor.isNull(iDir) )
                station.getMeteo().getWindDirection().put(stamp,cursor.getInt(iDir));
            if( !cursor.isNull(iMed) )
                station.getMeteo().getWindSpeedMed().put(stamp,cursor.getFloat(iMed));
            if( !cursor.isNull(iMax) )
                station.getMeteo().getWindSpeedMax().put(stamp,cursor.getFloat(iMax));
            if( !cursor.isNull(iHum) )
                station.getMeteo().getAirHumidity().put(stamp,cursor.getFloat(iHum));
            if( !cursor.isNull(iTemp) )
                station.getMeteo().getAirTemperature().put(stamp,cursor.getFloat(iTemp));
            if( !cursor.isNull(iPres) )
                station.getMeteo().getAirPressure().put(stamp,cursor.getFloat(iPres));
        }
        cursor.close();
        return count;
    }

    private static class Values {
        public Integer dir;
        public Float med, max, hum, temp, pres;
    }

    private interface Merger {
        void merge(Values values, Number number);
    }

    private static final String DB_NAME = "Meteo.db";
    private static final int DB_VERSION = 1;
    private static DbMeteo instance;
}
