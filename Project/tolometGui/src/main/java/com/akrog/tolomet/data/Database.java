package com.akrog.tolomet.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.akrog.tolomet.Region;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.providers.WindProviderType;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by gorka on 14/10/16.
 */

public class Database extends SQLiteAssetHelper {
    public static final String TAB_STATION = "Station";
    public static final String TAB_REGION = "Region";
    public static final String COL_STA_ID = "id";
    public static final String COL_STA_CODE = "code";
    public static final String COL_STA_NAME = "name";
    public static final String COL_STA_PROV = "provider";
    public static final String COL_STA_REG = "region";
    public static final String COL_STA_LAT = "latitude";
    public static final String COL_STA_LON = "longitude";
    public static final String COL_REG_ID = "id";
    public static final String COL_REG_NAME = "name";
    public static final String COL_REG_COUN = "country";

    private Database() {
        super(Tolomet.getAppContext(), DB_NAME, null, DB_VERSION);
    }

    public static synchronized Database getInstance() {
        if( instance == null )
            instance = new Database();
        return instance;
    }

    public Station findStation(String id) {
        return findStations(
                "SELECT Station.*,Region.country FROM Station,Region WHERE Station.id=? and Region.id=Station.region",
                id).get(0);
    }

    public List<Station> findCountryStations(String country) {
        return findStations(
                "SELECT Station.*,Region.country FROM Station,Region WHERE Region.country=? and Station.region=Region.id ORDER BY Station.name",
                country);
    }

    public List<Station> findRegionStations(int region) {
        return findStations(
                "SELECT Station.*,Region.country FROM Station,Region WHERE Station.region=? and Station.region=Region.id ORDER BY Station.name",
                String.valueOf(region));
    }

    public List<Station> findVowelStations(String country, char vowel) {
        return findStations(
                "SELECT Station.*,Region.country FROM Station,Region WHERE Region.country=? AND Station.name LIKE ? AND Region.id=Station.region ORDER BY Station.name",
                country, String.format("%c%%",vowel));
    }

    public List<Station> findCloseStations(double lat, double lon, double degrees) {
        return findStations(
                "SELECT Station.*,Region.country FROM Station, Region WHERE Station.latitude BETWEEN ? AND ? AND Station.longitude BETWEEN ? AND ? AND Region.id=Station.region",
                String.valueOf(lat-degrees), String.valueOf(lat+degrees), String.valueOf(lon-degrees), String.valueOf(lon+degrees));
    }

    public List<Region> findRegions(String country) {
        List<Region> list = new ArrayList<>();
        SQLiteDatabase lite = getReadableDatabase();
        Cursor cursor = lite.query(TAB_REGION,null,COL_REG_COUN+"=?",new String[]{country},null,null,COL_REG_NAME);
        int iCode = cursor.getColumnIndex(COL_REG_ID);
        int iName = cursor.getColumnIndex(COL_REG_NAME);
        while( cursor.moveToNext() ) {
            Region region = new Region();
            region.setCode(cursor.getInt(iCode));
            region.setName(cursor.getString(iName));
            list.add(region);
        }
        cursor.close();
        return list;
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
            station.setRegion(cursor.getInt(iReg));
            station.setLatitude(cursor.getDouble(iLat));
            station.setLongitude(cursor.getDouble(iLon));
            station.setCountry(cursor.getString(iCoun));
            station.setFavorite(favs.contains(station.getId()));
            list.add(station);
        }
        cursor.close();
        return list;
    }

    private static final String DB_NAME = "Tolomet.db";
    private static final int DB_VERSION = 1;
    private static Database instance;
}
