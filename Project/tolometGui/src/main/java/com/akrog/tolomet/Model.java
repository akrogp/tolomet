package com.akrog.tolomet;

import android.location.Location;

import com.akrog.tolomet.data.AppSettings;
import com.akrog.tolomet.data.DbMeteo;
import com.akrog.tolomet.data.DbTolomet;
import com.akrog.tolomet.providers.WindProviderType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by gorka on 6/10/16.
 */

public class Model {
    private static Model instance;
    private final Manager manager;
    private final DbTolomet db = DbTolomet.getInstance();
    private final DbMeteo cache = DbMeteo.getInstance();
    private final List<Station> selection = new ArrayList<Station>();
    private String country;
    private Station currentStation;

    private Model() {
        manager = new Manager();
        country = Locale.getDefault().getCountry();
    }

    public static Model getInstance() {
        if( instance == null )
            instance = new Model();
        return instance;
    }

    public Station findStation(String id) {
        return db.findStation(id);
    }

    public Station findStation(WindProviderType type, String code) {
        return findStation(Station.buildId(type,code));
    }

    private void setSelection(Collection<Station> stations) {
        selection.clear();
        selection.addAll(stations);
    }

    public void selectAll() {
        setSelection(db.findCountryStations(country));
    }

    public void selectNone() {
        selection.clear();
    }

    public void setCountry(String code) {
        country = code;
    }

    public String getCountry() {
        return country;
    }

    public void selectRegion(int code) {
        setSelection(db.findRegionStations(code));
    }

    public void selectVowel(char vowel) {
        setSelection(db.findVowelStations(country,vowel));
    }

    public void selectFavorites() {
        selection.clear();
        AppSettings settings = AppSettings.getInstance();
        for( String stationId : settings.getFavorites() ) {
            try {
                Station station = db.findStation(stationId);
                selection.add(station);
            } catch (Exception e) {
                settings.removeFavorite(stationId);
            }
        }
    }

    public void selectNearest(double lat, double lon) {
        List<Station> stations = db.findCloseStations(lat, lon, 5.0);
        float[] dist = new float[1];
        for( Station station : stations ) {
            Location.distanceBetween(lat, lon, station.getLatitude(), station.getLongitude(), dist);
            station.setDistance(dist[0]);
        }
        List<Station> close = new ArrayList<>();
        for( Station station : stations ) {
            if( station.getDistance() < 50000.0F )
                close.add(station);
        }
        Collections.sort(close, new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                return (int)Math.signum(s1.getDistance()-s2.getDistance());
            }
        });
        setSelection(close);
    }

    public void setCurrentStation(Station station) {
        currentStation = station;
    }

    public Station getCurrentStation() {
        return currentStation;
    }

    public List<Station> getAllStations() {
        return db.findCountryStations(country);
    }

    public List<Country> getCountries() {
        return db.getCountries();
    }

    public List<Region> getRegions() {
        List<Region> regions = db.findRegions(country);
        if( regions.size() == 1 )
            regions.clear();
        return regions;
    }

    public List<Station> getSelStations() {
        return selection;
    }

    public int getRefresh() {
        return getRefresh(currentStation);
    }

    public String getInforUrl() {
        return getInforUrl(currentStation);
    }

    public String getUserUrl() {
        return getUserUrl(currentStation);
    }

    public boolean refresh() {
        return refresh(currentStation);
    }

    public boolean travel(long date) {
        return travel(currentStation, date);
    }

    public void cancel() {
        cancel(currentStation);
    }

    public String getSummary(boolean large) {
        return getSummary(currentStation, large);
    }

    public String getSummary(Long stamp, boolean large) {
        return getSummary(currentStation, stamp, large);
    }

    public String getStamp() {
        return getStamp(currentStation);
    }

    public String getStamp(Long stamp) {
        return getStamp(currentStation, stamp);
    }

    public boolean isOutdated() {
        return isOutdated(currentStation);
    }

    public String parseDirection(int degrees) {
        return manager.parseDirection(degrees);
    }

    public boolean checkStation() {
        return checkStation(currentStation);
    }

    public int getRefresh(Station station) {
        return manager.getRefresh(station);
    }

    public String getInforUrl(Station station) {
        return manager.getInforUrl(station);
    }

    public String getUserUrl(Station station) {
        return manager.getUserUrl(station);
    }

    public boolean refresh(Station station) {
        cache.refresh(station);
        if( !manager.refresh(station) )
            return false;
        cache.save(station);
        return true;
    }

    public boolean travel(Station station, long date) {
        if( cache.travel(station, date) > 0 )
            return true;
        if( !manager.travel(station, date) )
            return false;
        cache.travelled(station, date);
        return true;
    }

    public void cancel(Station station) {
        manager.cancel(station);
    }

    public String getSummary(Station station, boolean large) {
        return manager.getSummary(station, large);
    }

    public String getSummary(Station station, Long stamp, boolean large) {
        return manager.getSummary(station, stamp, large);
    }

    public String getStamp(Station station) {
        return manager.getStamp(station);
    }

    public String getStamp(Station station, Long stamp) {
        return manager.getStamp(station, stamp);
    }

    public boolean isOutdated(Station station) {
        return manager.isOutdated(station);
    }

    public boolean checkStation(Station station) {
        return manager.checkStation(station);
    }
}
