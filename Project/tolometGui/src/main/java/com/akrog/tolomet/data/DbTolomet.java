package com.akrog.tolomet.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.akrog.tolomet.Country;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Region;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.providers.WindProviderType;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gorka on 14/10/16.
 */

public class DbTolomet extends SQLiteAssetHelper {
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

    private DbTolomet() {
        super(Tolomet.getAppContext(), DB_NAME, null, DB_VERSION);
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

    public Counts getRegionCounts(int id) {
        Counts info = new Counts();
        SQLiteDatabase lite = getReadableDatabase();
        Cursor cursor = lite.query(TAB_REGION, new String[]{COL_REG_NAME}, COL_REG_ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
        cursor.moveToFirst();
        info.name = cursor.getString(0);
        cursor.close();
        cursor = lite.rawQuery("SELECT COUNT(*) FROM Station WHERE region=?",new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        info.stationCount = cursor.getInt(0);
        cursor.close();
        return info;
    }

    public Counts getCountryCounts(String id) {
        Counts info = new Counts();
        SQLiteDatabase lite = getReadableDatabase();
        Cursor cursor = lite.rawQuery("SELECT COUNT(*) FROM Station, Region WHERE Region.id=Station.region AND Region.country=?",new String[]{id});
        cursor.moveToFirst();
        info.name = id;
        info.stationCount = cursor.getInt(0);
        cursor.close();
        return info;
    }

    public Set<String> findCountries() {
        Set<String> countries = new HashSet<>();
        SQLiteDatabase lite = getReadableDatabase();
        Cursor cursor = lite.query(true,TAB_REGION,new String[]{COL_REG_COUN},null,null,null,null,null,null);
        while( cursor.moveToNext() )
            countries.add(cursor.getString(0));
        cursor.close();
        return countries;
    }

    public List<Country> getCountries() {
        if( countries != null )
            return countries;
        Set<String> used = findCountries();
        countries = new ArrayList<>();
        Context context = Tolomet.getAppContext();
        String line, code;
        String[] fields;
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    context.getAssets().open("listings/"+context.getString(R.string.countries_csv))));
            while( (line=rd.readLine()) != null ) {
                fields = line.split("\\t");
                code = fields[0];
                if( !used.contains(code) )
                    continue;
                Country country = new Country();
                country.setCode(code);
                country.setName(fields[1]);
                countries.add(country);
            }
            rd.close();
        } catch (IOException e) {}
        Collections.sort(countries, new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        return countries;
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

    private static final String DB_NAME = "Tolomet.db";
    private static final int DB_VERSION = 2;
    private static DbTolomet instance;
    private List<Country> countries;
}
