package com.akrog.tolometgui.model.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui.Tolomet;
import com.akrog.tolometgui.model.AppSettings;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by gorka on 14/10/16.
 */

public class DbTolomet extends SQLiteAssetHelper {
    public static final String TAB_STATION = "Station";
    public static final String COL_STA_ID = "id";
    public static final String COL_STA_CODE = "code";
    public static final String COL_STA_NAME = "name";
    public static final String COL_STA_PROV = "provider";
    public static final String COL_STA_REG = "region";
    public static final String COL_STA_LAT = "latitude";
    public static final String COL_STA_LON = "longitude";
    public static final String COL_STA_UPD = "updated";
    public static final String COL_REG_COUN = "country";

    private DbTolomet() {
        super(Tolomet.getAppContext(), DB_NAME, null, DB_VERSION);
        setForcedUpgrade();
    }

    public static synchronized DbTolomet getInstance() {
        if( instance == null )
            instance = new DbTolomet();
        return instance;
    }

    public int getVersion() {
        return getReadableDatabase().getVersion();
    }

    public void setVersion(int version) {
        getWritableDatabase().setVersion(version);
    }

    public Station findStation(String id) {
        List<Station> list= findStations(
                "SELECT Station.*,Region.country FROM Station,Region WHERE Station.id=? and Region.id=Station.region",
                id);
        if( list.isEmpty() )
            return null;
        return list.get(0);
    }

    public List<Station> findGeoStations(double lat1, double lon1, double lat2, double lon2) {
        double tmp;
        if( lat2 < lat1 ) {
            tmp = lat1; lat1 = lat2; lat2 = tmp;
        }
        if( lon2 < lon1 ) {
            tmp = lon1; lon1 = lon2; lon2 = tmp;
        }
        return findStations(
                "SELECT Station.*,Region.country FROM Station, Region WHERE Station.latitude BETWEEN ? AND ? AND Station.longitude BETWEEN ? AND ? AND Region.id=Station.region",
                String.valueOf(lat1), String.valueOf(lat2), String.valueOf(lon1), String.valueOf(lon2));
    }

    public List<Station> findCloseStations(double lat, double lon, double degrees) {
        return findGeoStations(lat-degrees, lon-degrees, lat+degrees, lon+degrees);
    }

    public List<Station> searchStations(String text) {
        return findStations("SELECT * FROM Station WHERE name LIKE ?", "%"+text+"%");
    }

    public List<Station> allStations() {
        return findStations("SELECT * FROM Station");
    }

    public Map<String, ProviderInfo> getProviderCounts() {
        WindProviderType[] types = WindProviderType.values();
        String[] providers = new String[types.length];
        for( int i = 0; i < types.length; i++ )
            providers[i] = types[i].name();
        SQLiteDatabase lite = getReadableDatabase();
        Cursor cursor = lite.rawQuery(
                "SELECT provider, updated, COUNT(*) FROM Station WHERE provider IN (" +
                        TextUtils.join(",", Collections.nCopies(providers.length, "?")) +
                        ") GROUP BY provider",providers);
        Map<String, ProviderInfo> result = new HashMap<>();
        while( cursor.moveToNext() ) {
            ProviderInfo info = new ProviderInfo();
            String provider = cursor.getString(0);
            String date = cursor.getString(1);
            if( date != null ) {
                try {
                    info.setDate(DATE_FORMAT.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            info.setStationCount(cursor.getInt(2));
            result.put(provider, info);
        }
        cursor.close();
        return result;
    }

    private List<Station> findStations(String rawQuery, String... args) {
        Set<String> favs = AppSettings.getInstance().getFavorites();
        List<Station> list = new ArrayList<>();
        SQLiteDatabase lite = getReadableDatabase();
        Cursor cursor = lite.rawQuery(rawQuery, args);
        int iCode = cursor.getColumnIndex(COL_STA_CODE);
        int iName = cursor.getColumnIndex(COL_STA_NAME);
        int iProv = cursor.getColumnIndex(COL_STA_PROV);
        int iReg = cursor.getColumnIndex(COL_STA_REG);
        int iLat = cursor.getColumnIndex(COL_STA_LAT);
        int iLon = cursor.getColumnIndex(COL_STA_LON);
        int iCoun = cursor.getColumnIndex(COL_REG_COUN);
        while( cursor.moveToNext() ) {
            Station station = new Station();
            station.setCode(cursor.getString(iCode));
            station.setName(cursor.getString(iName));
            station.setProviderType(WindProviderType.valueOf(cursor.getString(iProv)));
            if( iReg >= 0 )
                station.setRegion(cursor.getInt(iReg));
            station.setLatitude(cursor.getDouble(iLat));
            station.setLongitude(cursor.getDouble(iLon));
            if( iCoun >= 0 )
                station.setCountry(cursor.getString(iCoun));
            station.setFavorite(favs.contains(station.getId()));
            list.add(station);
        }
        cursor.close();
        return list;
    }

    public void updateStations(WindProviderType type, List<Station> stations) {
        Date now = new Date();
        SQLiteDatabase lite = getWritableDatabase();
        lite.beginTransaction();
        try {
            lite.delete(TAB_STATION, COL_STA_PROV + "=?", new String[]{type.name()});
            for( Station station : stations ) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL_STA_ID, station.getId());
                contentValues.put(COL_STA_CODE, station.getCode());
                contentValues.put(COL_STA_NAME, station.getName());
                contentValues.put(COL_STA_PROV, station.getProviderType().name());
                contentValues.put(COL_STA_REG, station.getRegion());
                contentValues.put(COL_STA_LAT, station.getLatitude());
                contentValues.put(COL_STA_LON, station.getLongitude());
                if( station.getUpdated() == null )
                    station.setUpdated(now);
                contentValues.put(COL_STA_UPD, DATE_FORMAT.format(station.getUpdated()));
                lite.insertWithOnConflict(TAB_STATION, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
            }
            lite.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lite.endTransaction();
        }
    }

    public static class Counts {
        private String name;
        private int stationCount;
        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        public void setStationCount( int stationCount ) {
            this.stationCount = stationCount;
        }
        public int getStationCount() {
            return stationCount;
        }
    }

    public static class ProviderInfo {
        private int stationCount;
        private Date date;

        public int getStationCount() {
            return stationCount;
        }

        public void setStationCount(int stationCount) {
            this.stationCount = stationCount;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }

    private static final String DB_NAME = "Tolomet.db";
    private static final int DB_VERSION = 10;
    private static DbTolomet instance;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
