package com.akrog.tolometgui.ui.presenters;

import android.content.Context;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui.model.db.DbMeteo;
import com.akrog.tolometgui.model.db.DbTolomet;
import com.akrog.tolometgui.widget.model.WidgetInfo;
import com.akrog.tolometgui.widget.services.WidgetManager;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;

public class MyWidgets {
    private final Context context;
    private final WidgetManager manager;

    public MyWidgets(Context context) {
        this.context = context;
        manager = new WidgetManager(context);
    }

    public void liveUpdate(LifecycleOwner owner) {
        List<WidgetInfo> widgets = manager.findWidgets();
        for( WidgetInfo widget : widgets ) {
            String stationId = widget.getFlySpot().getConstraints().get(0).getStation();
            Station station = DbTolomet.getInstance().stationDao().findStation(stationId);
            DbMeteo.getInstance().meteoDao().loadStation(station).observe(owner, meteo ->
                manager.updateWidget(widget, meteo)
            );
        }
    }
}
