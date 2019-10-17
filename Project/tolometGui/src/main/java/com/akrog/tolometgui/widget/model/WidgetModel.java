package com.akrog.tolometgui.widget.model;

import android.content.Context;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Station;
import com.akrog.tolometgui.model.db.DbMeteo;
import com.akrog.tolometgui.model.db.DbTolomet;
import com.akrog.tolometgui.widget.services.WidgetManager;

import java.util.ArrayList;
import java.util.List;

public class WidgetModel {
    private final WidgetManager widgetManager;
    private final Manager stationManager;
    private final List<WidgetInfo> widgets;
    private List<Station> stations;

    public WidgetModel(Context context) {
        widgetManager = new WidgetManager(context);
        stationManager = new Manager();
        widgets = widgetManager.findWidgets();
    }

    public void download() {
        stations = new ArrayList<>(widgets.size());
        for( WidgetInfo widget : widgets ) {
            String stationId = widget.getFlySpot().getConstraints().get(0).getStation();
            Station station = DbTolomet.getInstance().stationDao().findStation(stationId);
            stationManager.refresh(station);
            DbMeteo.getInstance().meteoDao().saveStation(station);
            stations.add(station);
        }
    }

    public void update() {
        for( int i = 0; i < widgets.size(); i++ )
            widgetManager.updateWidget(widgets.get(i), stations.get(i));
    }
 }