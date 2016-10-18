package com.akrog.tolomet;

import android.location.Location;

import com.akrog.tolomet.data.AppSettings;
import com.akrog.tolomet.data.Database;
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
    private final Database db = Database.getInstance();
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
        return manager.getCountries();
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
        return manager.getRefresh(currentStation);
    }

    public String getInforUrl() {
        return manager.getInforUrl(currentStation);
    }

    public String getUserUrl() {
        return manager.getUserUrl(currentStation);
    }

    public boolean refresh() {
        return manager.refresh(currentStation);
    }

    public boolean travel(long date) {
        return manager.travel(currentStation, date);
    }

    public void cancel() {
        manager.cancel(currentStation);
    }

    public String getSummary(boolean large) {
        return manager.getSummary(currentStation, large);
    }

    public String getSummary(Long stamp, boolean large) {
        return manager.getSummary(currentStation, stamp, large);
    }

    public String getStamp() {
        return manager.getStamp(currentStation);
    }

    public String getStamp(Long stamp) {
        return manager.getStamp(currentStation, stamp);
    }

    public boolean isOutdated() {
        return manager.isOutdated(currentStation);
    }

    public String parseDirection(int degrees) {
        return manager.parseDirection(degrees);
    }

    public boolean checkCurrent() {
        return manager.checkStation(currentStation);
    }
}
