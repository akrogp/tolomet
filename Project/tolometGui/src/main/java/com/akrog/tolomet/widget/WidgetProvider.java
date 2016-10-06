package com.akrog.tolomet.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.WidgetSettings;
import com.akrog.tolomet.data.FlyConstraint;
import com.akrog.tolomet.data.FlySpot;

/**
 * Created by gorka on 21/09/16.
 */

public class WidgetProvider {
    public enum FlyCondition { GOOD, BAD, UNKOWN };

    public static class StationData {
        String id;
        String country;
        String name;
        String date;
        String directionExt;
        String directionShort;
        String speed;
        String air;
        FlyCondition fly = FlyCondition.UNKOWN;
    }

    public static class WidgetData {
        StationData[] stations;
    }

    public WidgetProvider(Context context) {
        this.context = context;
        appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName comp;
        comp = new ComponentName(context, SmallWidgetReceiver.class);
        smallWidgets = appWidgetManager.getAppWidgetIds(comp);
        comp = new ComponentName(context, MediumWidgetReceiver.class);
        mediumWidgets = appWidgetManager.getAppWidgetIds(comp);
        comp = new ComponentName(context, LargeWidgetReceiver.class);
        largeWidgets = appWidgetManager.getAppWidgetIds(comp);
    }

    public void downloadData() {
        smallData = downloadData(smallWidgets);
        mediumData = downloadData(mediumWidgets);
        largeData = downloadData(largeWidgets);
    }

    private WidgetData downloadData(int[] widgetIds) {
        if( widgetIds == null || widgetIds.length == 0 )
            return null;
        WidgetData widgetData = new WidgetData();
        widgetData.stations = new StationData[widgetIds.length];
        for( int i = 0; i < widgetIds.length; i++ )
            widgetData.stations[i] = fillStation(widgetIds[i]);
        return widgetData;
    }

    private StationData fillStation(int widgetId) {
        WidgetSettings settings = new WidgetSettings(context,widgetId);
        FlySpot spot = settings.getSpot();
        if( !spot.isValid() )
            return null;
        FlyConstraint constraint = spot.getConstraints().get(0);
        model.setCountry(spot.getCountry());
        Station station = model.findStation(constraint.getStation());
        if( station == null )
            return null;
        model.setCurrentStation(station);
        /*if( !model.refresh() )
            return null;*/
        model.refresh();

        StationData data = new StationData();
        data.id = station.getId();
        data.country = station.getCountry();
        data.name = spot.getName();
        long stamp = station.getStamp();
        data.date = model.getStamp(stamp);
        Number num = station.getMeteo().getWindDirection().getAt(stamp);
        if( num != null ) {
            data.directionShort = model.parseDirection(num.intValue());
            data.directionExt = String.format("%dº (%s)", num.intValue(), data.directionShort);
            if( constraint.getMinDir() <= constraint.getMaxDir() )
                data.fly = num.intValue() >= constraint.getMinDir() && num.intValue() <= constraint.getMaxDir() ? FlyCondition.GOOD : FlyCondition.BAD;
            else
                data.fly = num.intValue() >= constraint.getMinDir() || num.intValue() <= constraint.getMaxDir() ? FlyCondition.GOOD : FlyCondition.BAD;
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
            if (num.floatValue() >= constraint.getMaxHum() )
                data.fly = FlyCondition.BAD;
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
            sb = new StringBuilder(String.format("%.1f", num));
            if( data.fly == FlyCondition.GOOD && (num.floatValue() >= constraint.getMaxWind() || num.floatValue() < constraint.getMinWind()) )
                data.fly = FlyCondition.BAD;
            num = station.getMeteo().getWindSpeedMax().getAt(stamp);
            if (num != null) {
                sb.append(String.format("~%.1f", num));
                if( data.fly == FlyCondition.GOOD && (num.floatValue() >= constraint.getMaxWind() || num.floatValue() < constraint.getMinWind()) )
                    data.fly = FlyCondition.BAD;
            }
            //sb.append(" km/h");
            data.speed = sb.toString();
        }
        return data;
    }

    public void updateWidgets() {
        updateWidgets(smallData, smallWidgets, WidgetReceiver.WIDGET_SIZE_SMALL);
        updateWidgets(mediumData, mediumWidgets, WidgetReceiver.WIDGET_SIZE_MEDIUM);
        updateWidgets(largeData, largeWidgets, WidgetReceiver.WIDGET_SIZE_LARGE);
    }

    private void updateWidgets(WidgetData widgetData, int[] allWidgetIds, int widgetSize) {
        if( widgetData == null )
            return;
        int[] layouts = {R.layout.widget_layout_small, R.layout.widget_layout_middle, R.layout.widget_layout_large};
        int i = 0;
        for (int widgetId : allWidgetIds) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), layouts[widgetSize]);
            StationData data = widgetData.stations[i++];
            if( data == null )
                continue;
            if( !updateViews(remoteViews,data,widgetSize) )
                continue;
            if( widgetSize != WidgetReceiver.WIDGET_SIZE_SMALL )
                remoteViews.setOnClickPendingIntent(R.id.widget_icon, getUpdateIntent());
            remoteViews.setOnClickPendingIntent(R.id.widget, getTolometIntent(data));
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }

    private PendingIntent getTolometIntent(StationData data) {
        Intent clickIntent = new Intent(context, Tolomet.class);
        //clickIntent.putExtra(WidgetReceiver.EXTRA_WIDGET_SIZE, widgetSize);
        clickIntent.putExtra(Tolomet.EXTRA_STATION_ID, data.id);
        clickIntent.putExtra(Tolomet.EXTRA_COUNTRY, data.country);
        return PendingIntent.getActivity(context,0,clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getUpdateIntent() {
        Intent clickIntent = new Intent(context, MediumWidgetReceiver.class);
        clickIntent.setAction(WidgetReceiver.FORCE_WIDGET_UPDATE);
        //clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
        //clickIntent.putExtra(WidgetReceiver.EXTRA_WIDGET_SIZE, widgetSize);
        return PendingIntent.getBroadcast(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private boolean updateViews( RemoteViews remoteViews, StationData data, int widgetSize ) {
        switch( widgetSize ) {
            case WidgetReceiver.WIDGET_SIZE_SMALL:
                remoteViews.setTextViewText(R.id.widget_station, data.name);
                //remoteViews.setTextViewText(R.id.widget_date, data.date);
                break;
            case WidgetReceiver.WIDGET_SIZE_MEDIUM:
                remoteViews.setTextViewText(R.id.widget_station, data.name);
                remoteViews.setTextViewText(R.id.widget_date, data.date);
                remoteViews.setTextViewText(R.id.widget_direction, data.directionExt);
                remoteViews.setTextViewText(R.id.widget_speed, data.speed);
                break;
            case WidgetReceiver.WIDGET_SIZE_LARGE:
                remoteViews.setTextViewText(R.id.widget_station, data.name);
                remoteViews.setTextViewText(R.id.widget_date, data.date);
                remoteViews.setTextViewText(R.id.widget_air, data.air);
                remoteViews.setTextViewText(R.id.widget_wind, data.directionExt + " " + data.speed + " km/h");
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
    private WidgetData smallData, mediumData, largeData;
}
