package com.akrog.tolometgui.widget.services;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Station;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.AppSettings;
import com.akrog.tolometgui.model.FlyConstraint;
import com.akrog.tolometgui.model.FlySpot;
import com.akrog.tolometgui.model.WidgetSettings;
import com.akrog.tolometgui.model.db.DbTolomet;
import com.akrog.tolometgui.ui.activities.MainActivity;
import com.akrog.tolometgui.widget.providers.LargeWidgetProvider;
import com.akrog.tolometgui.widget.providers.MediumWidgetProvider;
import com.akrog.tolometgui.widget.providers.SmallWidgetProvider;
import com.akrog.tolometgui.widget.providers.SpotWidgetProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorka on 21/09/16.
 */

public class WidgetPopulator {
    public enum FlyCondition { GOOD, BAD, UNKOWN };

    public static class WidgetData {
        String id;
        String country;
        String name;
        String date;
        String directionExt;
        String directionShort;
        String speed;
        String air;
        FlyCondition fly = FlyCondition.UNKOWN;
        String unit;
        float factor;
        int widgetSize;
        int widgetId;
    }

    public WidgetPopulator(Context context) {
        this.context = context;
        appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName comp;
        comp = new ComponentName(context, SmallWidgetProvider.class);
        smallWidgets = appWidgetManager.getAppWidgetIds(comp);
        comp = new ComponentName(context, MediumWidgetProvider.class);
        mediumWidgets = appWidgetManager.getAppWidgetIds(comp);
        comp = new ComponentName(context, LargeWidgetProvider.class);
        largeWidgets = appWidgetManager.getAppWidgetIds(comp);
    }

    public void downloadData() {
        widgetData = new ArrayList<>();
        widgetData.addAll(downloadData(smallWidgets, SpotWidgetProvider.WIDGET_SIZE_SMALL));
        widgetData.addAll(downloadData(mediumWidgets, SpotWidgetProvider.WIDGET_SIZE_MEDIUM));
        widgetData.addAll(downloadData(largeWidgets, SpotWidgetProvider.WIDGET_SIZE_LARGE));
    }

    private List<WidgetData> downloadData(int[] widgetIds, int widgetSize) {
        if( widgetIds == null || widgetIds.length == 0 )
            return new ArrayList<>();
        List<WidgetData> stations = new ArrayList<>(widgetIds.length);
        for( int i = 0; i < widgetIds.length; i++ ) {
            WidgetData station = fillStation(widgetIds[i], widgetSize);
            if( station != null )
                stations.add(station);
        }
        return stations;
    }

    private WidgetData fillStation(int widgetId, int widgetSize) {
        WidgetSettings settings = new WidgetSettings(context,widgetId);
        FlySpot spot = settings.getSpot();
        if( !spot.isValid() )
            return null;
        FlyConstraint constraint = spot.getConstraints().get(0);
        Station station = DbTolomet.getInstance().stationDao().findStation(constraint.getStation());
        if( station == null )
            return null;
        model.refresh(station);
        Long stamp = station.getStamp();
        if( stamp == null )
            return null;

        //logUpdate(spot.getName());

        WidgetData data = new WidgetData();
        data.id = station.getId();
        data.country = station.getCountry();
        data.name = spot.getName();
        //long stamp = station.getStamp();
        data.date = model.getStamp(station, stamp);
        data.factor = AppSettings.getSpeedFactor(spot.getSpeedUnits());
        data.unit = context.getResources().getStringArray(R.array.pref_speedUnitEntries)[spot.getSpeedUnits()];
        data.widgetSize = widgetSize;
        data.widgetId = widgetId;

        fillMeteo(data, station, stamp);

        data.fly = checkConditions(station, stamp, constraint);
        if( data.fly == FlyCondition.GOOD ) {
            long prevStamp = stamp-model.getRefresh(station)*60*1000;
            prevStamp = station.getMeteo().getStamp(prevStamp);
            if( checkConditions(station, prevStamp, constraint) == FlyCondition.BAD )
                data.fly = FlyCondition.UNKOWN;
        }

        return data;
    }

    /*private void logUpdate(String spot) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), "TolometWidgetLog.txt");
            PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            pw.println(String.format("%s %s",df.format(new Date()),spot));
            pw.close();
        } catch (Exception e) {}
    }*/

    private void fillMeteo(WidgetData data, Station station, long stamp ) {
        Number num = station.getMeteo().getWindDirection().getAt(stamp);
        if( num != null ) {
            data.directionShort = model.parseDirection(num.intValue());
            data.directionExt = String.format("%dº (%s)", num.intValue(), data.directionShort);
        }
        StringBuilder sb = new StringBuilder();
        num = station.getMeteo().getAirTemperature().getAt(stamp);
        if( num != null )
            sb.append(String.format("%.1fºC", num));
        num = station.getMeteo().getAirHumidity().getAt(stamp);
        if( num != null ) {
            if( sb.length() != 0 )
                sb.append(' ');
            sb.append(String.format("%.0f%%", num));
        }
        num = station.getMeteo().getAirPressure().getAt(stamp);
        if( num != null ) {
            if( sb.length() != 0 )
                sb.append(' ');
            sb.append(String.format("%.0f mb", num));
        }
        data.air = sb.toString();
        num = station.getMeteo().getWindSpeedMed().getAt(stamp);
        if( num != null ) {
            sb = new StringBuilder(String.format("%.1f", num.floatValue()*data.factor));
            num = station.getMeteo().getWindSpeedMax().getAt(stamp);
            if (num != null)
                sb.append(String.format("~%.1f", num.floatValue()*data.factor));
            data.speed = sb.toString();
        }
    }

    private FlyCondition checkConditions(Station station, long stamp, FlyConstraint constraint) {
        boolean missing = false;
        Number num = station.getMeteo().getWindDirection().getAt(stamp);
        if( num != null ) {
            if( constraint.getMinDir() <= constraint.getMaxDir() ) {
                if( num.intValue() < constraint.getMinDir() || num.intValue() > constraint.getMaxDir() )
                    return FlyCondition.BAD;
            } else if (num.intValue() < constraint.getMinDir() && num.intValue() > constraint.getMaxDir() )
                return FlyCondition.BAD;
        } else
            missing = true;
        num = station.getMeteo().getAirHumidity().getAt(stamp);
        if( num != null && num.floatValue() > constraint.getMaxHum() )
            return FlyCondition.BAD;
        num = station.getMeteo().getWindSpeedMax().getAt(stamp);
        if( num == null )
            num = station.getMeteo().getWindSpeedMed().getAt(stamp);
        if( num == null )
            missing = true;
        else if( num.floatValue() < constraint.getMinWind() || num.floatValue() > constraint.getMaxWind() )
            return FlyCondition.BAD;
        return missing ? FlyCondition.UNKOWN : FlyCondition.GOOD;
    }

    public void updateWidgets() {
        if( widgetData == null || widgetData.isEmpty() )
            return;
        int[] layouts = {R.layout.widget_layout_small, R.layout.widget_layout_middle, R.layout.widget_layout_large};
        int i = 0;
        for (WidgetData widget : widgetData) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layouts[widget.widgetSize]);
            if( !updateViews(remoteViews,widget) )
                continue;
            //if( widgetSize != WidgetReceiver.WIDGET_SIZE_SMALL )
                remoteViews.setOnClickPendingIntent(R.id.widget_icon, getUpdateIntent());
            remoteViews.setOnClickPendingIntent(R.id.widget, getTolometIntent(widget));
            appWidgetManager.updateAppWidget(widget.widgetId, remoteViews);
        }
    }

    private PendingIntent getTolometIntent(WidgetData data) {
        Intent clickIntent = new Intent(context, MainActivity.class);
        //clickIntent.putExtra(WidgetReceiver.EXTRA_WIDGET_SIZE, widgetSize);
        clickIntent.putExtra(MainActivity.EXTRA_STATION, data.id);
        // http://stackoverflow.com/questions/3168484/pendingintent-works-correctly-for-the-first-notification-but-incorrectly-for-the#comment3283736_3168653
        clickIntent.setAction(Long.toString(System.currentTimeMillis()));
        return PendingIntent.getActivity(context,0,clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getUpdateIntent() {
        Intent clickIntent = new Intent(context, MediumWidgetProvider.class);
        clickIntent.setAction(SpotWidgetProvider.FORCE_WIDGET_UPDATE);
        //clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        //clickIntent.putExtra(WidgetReceiver.EXTRA_WIDGET_SIZE, widgetSize);
        return PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private boolean updateViews(RemoteViews remoteViews, WidgetData data) {
        switch( data.widgetSize ) {
            case SpotWidgetProvider.WIDGET_SIZE_SMALL:
                remoteViews.setTextViewText(R.id.widget_station, data.name);
                //remoteViews.setTextViewText(R.id.widget_date, data.date);
                break;
            case SpotWidgetProvider.WIDGET_SIZE_MEDIUM:
                remoteViews.setTextViewText(R.id.widget_station, data.name);
                remoteViews.setTextViewText(R.id.widget_date, data.date);
                remoteViews.setTextViewText(R.id.widget_direction, data.directionExt);
                remoteViews.setTextViewText(R.id.widget_speed, data.speed);
                break;
            case SpotWidgetProvider.WIDGET_SIZE_LARGE:
                remoteViews.setTextViewText(R.id.widget_station, data.name);
                remoteViews.setTextViewText(R.id.widget_date, data.date);
                remoteViews.setTextViewText(R.id.widget_air, data.air);
                remoteViews.setTextViewText(R.id.widget_wind, data.directionExt + " " + data.speed + " " + data.unit);
                break;
            default:
                return false;
        }
        if( data.fly == FlyCondition.GOOD )
            remoteViews.setImageViewResource(R.id.widget_icon,R.drawable.ic_wind_good);
        else if( data.fly == FlyCondition.BAD )
            remoteViews.setImageViewResource(R.id.widget_icon,R.drawable.ic_wind_bad);
        else
            remoteViews.setImageViewResource(R.id.widget_icon,R.drawable.ic_wind_unknown);
        return true;
    }

    private final Context context;
    private final AppWidgetManager appWidgetManager;
    private final Manager model = new Manager();
    private final int[] smallWidgets, mediumWidgets, largeWidgets;
    private List<WidgetData> widgetData;
}
