package com.akrog.tolometgui.ui.viewmodels;

import android.location.Location;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui.model.AppSettings;
import com.akrog.tolometgui.model.db.DbTolomet;
import com.akrog.tolometgui.model.db.DbMeteo;
import com.akrog.tolometgui.model.db.TravelDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

/**
 * Created by gorka on 6/10/16.
 */

public class MainViewModel extends ViewModel {
    private final Manager manager;
    private final DbTolomet db = DbTolomet.getInstance();
    private final MutableLiveData<List<Station>> selection = new MutableLiveData<>();
    private final MutableLiveData<Station> currentStation = new MutableLiveData<>();
    private final LiveData<Station> currentMeteo = Transformations.switchMap(currentStation,
            station -> DbMeteo.getInstance().meteoDao().loadStation(station));
    private Command command = Command.FAV;

    public MainViewModel() {
        manager = new Manager();
    }

    public Station findStation(String id) {
        return db.findStation(id);
    }

    public Station findStation(WindProviderType type, String code) {
        return findStation(Station.buildId(type,code));
    }

    public void selectNone() {
        command = null;
        selection.postValue(null);
    }

    public void selectStation(Station station) {
        setCurrentStation(station);
        if( station == null ) {
            selectNone();
            return;
        }
        if( station.isFavorite() ) {
            selectFavorites();
            return;
        }
        command = null;
        List<Station> stations = new ArrayList<>(1);
        stations.add(station);
        selection.postValue(stations);
    }

    public void selectFavorites() {
        command = Command.FAV;
        List<Station> favs = new ArrayList<>();
        AppSettings settings = AppSettings.getInstance();
        for( String stationId : settings.getFavorites() ) {
            try {
                Station station = db.findStation(stationId);
                favs.add(station);
            } catch (Exception e) {
                settings.removeFavorite(stationId);
            }
        }
        Collections.sort(favs, new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                return s1.getName().compareTo(s2.getName());
            }
        });
        selection.postValue(favs);
    }

    public void selectNearest(double lat, double lon) {
        command = Command.NEAR;
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
        Collections.sort(close, (s1, s2) -> (int)Math.signum(s1.getDistance()-s2.getDistance()));
        selection.postValue(close);
    }

    public void setCurrentStation(Station station) {
        currentStation.postValue(station);
    }

    public Station getCurrentStation() {
        return currentStation.getValue();
    }

    public LiveData<Station> liveCurrentStation() {
        return currentStation;
    }

    public LiveData<Station> liveCurrentMeteo() {
        return currentMeteo;
    }

    public List<Station> getSelStations() {
        return selection.getValue();
    }

    public LiveData<List<Station>> liveSelStations() {
        return selection;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public int getRefresh() {
        return manager.getRefresh(getCurrentStation());
    }

    public String getInforUrl() {
        return manager.getInforUrl(getCurrentStation());
    }

    public String getUserUrl() {
        return manager.getUserUrl(getCurrentStation());
    }

    public void cancel() {
        manager.cancel(getCurrentStation());
    }

    public String getSummary(boolean large, float factor, String unit) {
        return manager.getSummary(getCurrentStation(), large, factor, unit);
    }

    public String getSummary(Long stamp, boolean large, float factor, String unit) {
        return manager.getSummary(getCurrentStation(), stamp, large, factor, unit);
    }

    public String getStamp() {
        return manager.getStamp(getCurrentStation());
    }

    public String getStamp(Long stamp) {
        return manager.getStamp(getCurrentStation(), stamp);
    }

    public boolean isOutdated() {
        return manager.isOutdated(getCurrentStation());
    }

    public String parseDirection(int degrees) {
        return manager.parseDirection(degrees);
    }

    public boolean checkStation() {
        return manager.checkStation(getCurrentStation());
    }

    public Station refresh() {
        Station station = getCurrentStation();
        if( !manager.checkStation(station) )
            return null;
        //Station clone = station.clone();
        Station clone = station;
        if( !manager.refresh(clone) )
            return null;
        DbMeteo.getInstance().meteoDao().saveStation(clone);
        return clone;
    }

    public Station travel(long date) {
        TravelDao travelDao = DbMeteo.getInstance().travelDao();
        if( travelDao.hasTravelled(getCurrentStation(), date) )
            return null;
        Station station = getCurrentStation().clone();
        if( !manager.travel(station, date) )
            return null;
        DbMeteo.getInstance().meteoDao().saveStation(station);
        travelDao.saveTravel(station, date);
        return station;
    }

    public enum Command {SEL, FAV, NEAR, FIND, SEP}
}