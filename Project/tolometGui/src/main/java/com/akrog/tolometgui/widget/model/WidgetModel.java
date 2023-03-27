package com.akrog.tolometgui.widget.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Station;
import com.akrog.tolometgui.model.db.DbMeteo;
import com.akrog.tolometgui.model.db.DbTolomet;
import com.akrog.tolometgui.widget.presenters.WidgetManager;

import java.util.ArrayList;
import java.util.List;

public class WidgetModel {
    private final WidgetManager widgetManager;
    private final Manager stationManager;
    private final List<WidgetInfo> widgets;

    public WidgetModel(Context context) {
        widgetManager = new WidgetManager(context);
        stationManager = new Manager();
        widgets = widgetManager.findWidgets();
    }

    public void download() {
        for( Station station : findStations() ) {
            stationManager.refresh(station);
            DbMeteo.getInstance().meteoDao().saveStation(station);
        }
    }

    public void update() {
        List<Station> stations = findStations();
        for( int i = 0; i < widgets.size(); i++ ) {
            LiveData<Station> liveStation = DbMeteo.getInstance().meteoDao().loadStation(stations.get(i));
            WidgetInfo widget = widgets.get(i);
            liveStation.observeForever(new Observer<Station>() {
                @Override
                public void onChanged(Station station) {
                    widgetManager.updateWidget(widget, station);
                    liveStation.removeObserver(this);
                }
            });
        }
    }

    private List<Station> findStations() {
        List<Station> stations = new ArrayList<>(widgets.size());
        for( WidgetInfo widget : widgets ) {
            String stationId = widget.getFlySpot().getConstraints().get(0).getStation();
            Station station = DbTolomet.getInstance().stationDao().findStation(stationId);
            stations.add(station);
        }
        return stations;
    }
 }