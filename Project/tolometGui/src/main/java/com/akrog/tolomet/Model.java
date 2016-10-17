package com.akrog.tolomet;

import com.akrog.tolomet.providers.WindProviderType;

import java.util.List;

/**
 * Created by gorka on 6/10/16.
 */

public class Model {
    private static Model instance;
    private final Manager manager;

    private Model() {
        manager = new Manager();
    }

    public static Model getInstance() {
        if( instance == null )
            instance = new Model();
        return instance;
    }

    public Station findStation(String id) {
        return manager.findStation(id);
    }

    public Station findStation(WindProviderType type, String code) {
        return manager.findStation(type, code);
    }

    public void selectAll() {
        manager.selectAll();
    }

    public void selectNone() {
        manager.selectNone();
    }

    public void setCountry(String code) {
        manager.setCountry(code);
    }

    public String getCountry() {
        return manager.getCountry();
    }

    public void selectRegion(int code) {
        manager.selectRegion(code);
    }

    public void selectVowel(char vowel) {
        manager.selectVowel(vowel);
    }

    public void selectFavorites() {
        manager.selectFavorites();
    }

    public void selectNearest() {
        manager.selectNearest();
    }

    public void setCurrentStation(Station station) {
        manager.setCurrentStation(station);
    }

    public Station getCurrentStation() {
        return manager.getCurrentStation();
    }

    public List<Station> getAllStations() {
        return manager.getAllStations();
    }

    public List<Country> getCountries() {
        return manager.getCountries();
    }

    public List<Region> getRegions() {
        return manager.getRegions();
    }

    public List<Station> getSelStations() {
        return manager.getSelStations();
    }

    public int getRefresh() {
        return manager.getRefresh();
    }

    public String getInforUrl() {
        return manager.getInforUrl();
    }

    public String getUserUrl() {
        return manager.getUserUrl();
    }

    public boolean refresh() {
        return manager.refresh();
    }

    public boolean travel(long date) {
        return manager.travel(date);
    }

    public void cancel() {
        manager.cancel();
    }

    public String getSummary(boolean large) {
        return manager.getSummary(large);
    }

    public String getSummary(Long stamp, boolean large) {
        return manager.getSummary(stamp, large);
    }

    public String getStamp() {
        return manager.getStamp();
    }

    public String getStamp(Long stamp) {
        return manager.getStamp(stamp);
    }

    public boolean isOutdated() {
        return manager.isOutdated();
    }

    public String parseDirection(int degrees) {
        return manager.parseDirection(degrees);
    }

    public boolean checkCurrent() {
        return manager.checkCurrent();
    }
}
