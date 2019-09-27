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
import java.util.List;
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

    public static final String TAB_SPOT = "Spot";
    public static final String COL_SPOT_ID = "id";
    public static final String COL_SPOT_LAT = "latitude";
    public static final String COL_SPOT_LON = "longitude";
    public static final String COL_SPOT_NAME = "name";
    public static final String COL_SPOT_DESC = "desc";
    public static final String COL_SPOT_TYPE = "type";
    public static final String COL_SPOT_UPD = "updated";
    public static final String COL_SPOT_PROV = "provider";

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

    public List<SpotEntity> findGeoSpots(double lat1, double lon1, double lat2, double lon2) {
        double tmp;
        if( lat2 < lat1 ) {
            tmp = lat1; lat1 = lat2; lat2 = tmp;
        }
        if( lon2 < lon1 ) {
            tmp = lon1; lon1 = lon2; lon2 = tmp;
        }
        return findSpots(
                "SELECT * FROM "+TAB_SPOT+" WHERE "+COL_SPOT_LAT+" BETWEEN ? AND ? AND "+COL_SPOT_LON+" BETWEEN ? AND ?",
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

    public List<ProviderInfo> getProviderCounts() {
        List<ProviderInfo> result = new ArrayList<>();
        result.addAll(getStationProviderCounts());
        result.addAll(getSpotProviderCounts());
        return result;
    }

    public List<ProviderInfo> getStationProviderCounts() {
        WindProviderType[] types = WindProviderType.values();
        String[] providers = new String[types.length];
        for( int i = 0; i < types.length; i++ )
            providers[i] = types[i].name();
        List<ProviderInfo> result = countProviders(providers, TAB_STATION);
        for( ProviderInfo info : result )
            info.setWindProviderType(WindProviderType.valueOf(info.getProvider()));
        return result;
    }

    public List<ProviderInfo> getSpotProviderCounts() {
        SpotProviderType[] types = SpotProviderType.values();
        String[] providers = new String[types.length];
        for( int i = 0; i < types.length; i++ )
            providers[i] = types[i].name();
        List<ProviderInfo> result = countProviders(providers, TAB_SPOT);
        for( ProviderInfo info : result )
            info.setSpotProviderType(SpotProviderType.valueOf(info.getProvider()));
        return result;
    }

    private List<ProviderInfo> countProviders(String[] providers, String table) {
        List<ProviderInfo> result = new ArrayList<>();
        SQLiteDatabase lite = getReadableDatabase();
        try( Cursor cursor = lite.rawQuery(
            "SELECT provider, updated, COUNT(*) FROM "+table+" WHERE provider IN (" +
                    TextUtils.join(",", Collections.nCopies(providers.length, "?")) +
                    ") GROUP BY provider",providers) ) {
            while (cursor.moveToNext()) {
                ProviderInfo info = new ProviderInfo();
                info.setProvider(cursor.getString(0));
                String date = cursor.getString(1);
                if (date != null) {
                    try {
                        info.setDate(DATE_FORMAT.parse(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                info.setCount(cursor.getInt(2));
                result.add(info);
            }
        }
        return result;
    }

    private List<Station> findStations(String rawQuery, String... args) {
        Set<String> favs = AppSettings.getInstance().getFavorites();
        List<Station> list = new ArrayList<>();
        SQLiteDatabase lite = getReadableDatabase();
        try( Cursor cursor = lite.rawQuery(rawQuery, args) ) {
            int iCode = cursor.getColumnIndex(COL_STA_CODE);
            int iName = cursor.getColumnIndex(COL_STA_NAME);
            int iProv = cursor.getColumnIndex(COL_STA_PROV);
            int iReg = cursor.getColumnIndex(COL_STA_REG);
            int iLat = cursor.getColumnIndex(COL_STA_LAT);
            int iLon = cursor.getColumnIndex(COL_STA_LON);
            int iCoun = cursor.getColumnIndex(COL_REG_COUN);
            while (cursor.moveToNext()) {
                Station station = new Station();
                station.setCode(cursor.getString(iCode));
                station.setName(cursor.getString(iName));
                station.setProviderType(WindProviderType.valueOf(cursor.getString(iProv)));
                if (iReg >= 0)
                    station.setRegion(cursor.getInt(iReg));
                station.setLatitude(cursor.getDouble(iLat));
                station.setLongitude(cursor.getDouble(iLon));
                if (iCoun >= 0)
                    station.setCountry(cursor.getString(iCoun));
                station.setFavorite(favs.contains(station.getId()));
                list.add(station);
            }
        }
        return list;
    }

    private List<SpotEntity> findSpots(String rawQuery, String... args) {
        List<SpotEntity> list = new ArrayList<>();
        SQLiteDatabase lite = getReadableDatabase();
        try( Cursor cursor = lite.rawQuery(rawQuery, args) ) {
            int iId = cursor.getColumnIndex(COL_SPOT_ID);
            int iName = cursor.getColumnIndex(COL_SPOT_NAME);
            int iDesc = cursor.getColumnIndex(COL_SPOT_DESC);
            int iProv = cursor.getColumnIndex(COL_SPOT_PROV);
            int iType = cursor.getColumnIndex(COL_SPOT_TYPE);
            int iLat = cursor.getColumnIndex(COL_SPOT_LAT);
            int iLon = cursor.getColumnIndex(COL_SPOT_LON);
            while (cursor.moveToNext()) {
                SpotEntity spot = new SpotEntity();
                spot.setId(cursor.getString(iId));
                spot.setName(cursor.getString(iName));
                spot.setDesc(cursor.getString(iDesc));
                spot.setProvider(SpotProviderType.valueOf(cursor.getString(iProv)));
                spot.setType(SpotType.valueOf(cursor.getString(iType)));
                spot.setLatitude(cursor.getDouble(iLat));
                spot.setLongitude(cursor.getDouble(iLon));
                list.add(spot);
            }
        }
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

    public void updateSpots(SpotProviderType type, List<SpotEntity> spots) {
        Date now = new Date();
        SQLiteDatabase lite = getWritableDatabase();
        lite.beginTransaction();
        try {
            lite.delete(TAB_SPOT, COL_SPOT_PROV + "=?", new String[]{type.name()});
            for( SpotEntity spot : spots ) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL_SPOT_ID, spot.getId());
                contentValues.put(COL_SPOT_NAME, spot.getName());
                contentValues.put(COL_SPOT_DESC, spot.getDesc());
                contentValues.put(COL_SPOT_PROV, spot.getProvider().name());
                contentValues.put(COL_SPOT_TYPE, spot.getType().name());
                contentValues.put(COL_SPOT_LAT, spot.getLatitude());
                contentValues.put(COL_SPOT_LON, spot.getLongitude());
                if( spot.getUpdated() == null )
                    spot.setUpdated(now);
                contentValues.put(COL_SPOT_UPD, DATE_FORMAT.format(spot.getUpdated()));
                lite.insertWithOnConflict(TAB_SPOT, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
            }
            lite.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lite.endTransaction();
        }
    }

    public static class ProviderInfo {
        private int count;
        private Date date;
        private String provider;
        private WindProviderType windProviderType;
        private SpotProviderType spotProviderType;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public WindProviderType getWindProviderType() {
            return windProviderType;
        }

        public void setWindProviderType(WindProviderType windProviderType) {
            this.windProviderType = windProviderType;
        }

        public SpotProviderType getSpotProviderType() {
            return spotProviderType;
        }

        public void setSpotProviderType(SpotProviderType spotProviderType) {
            this.spotProviderType = spotProviderType;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }
    }

    private static final String DB_NAME = "Tolomet.db";
    private static final int DB_VERSION = 12;
    private static DbTolomet instance;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
}
