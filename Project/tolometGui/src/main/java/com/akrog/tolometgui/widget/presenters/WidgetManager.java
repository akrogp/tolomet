package com.akrog.tolometgui.widget.presenters;

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
import com.akrog.tolometgui.ui.activities.MainActivity;
import com.akrog.tolometgui.widget.model.FlyCondition;
import com.akrog.tolometgui.widget.model.WidgetInfo;
import com.akrog.tolometgui.widget.providers.LargeWidgetProvider;
import com.akrog.tolometgui.widget.providers.MediumWidgetProvider;
import com.akrog.tolometgui.widget.providers.SmallWidgetProvider;
import com.akrog.tolometgui.widget.providers.SpotWidgetProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorka on 21/09/16.
 */

public class WidgetManager {
    private static int[] LAYOUTS = {
        R.layout.widget_layout_small,
        R.layout.widget_layout_middle,
        R.layout.widget_layout_large
    };

    private static class WidgetData {
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

    public WidgetManager(Context context) {
        this.context = context;
        appWidgetManager = AppWidgetManager.getInstance(context);
    }

    public List<WidgetInfo> findWidgets() {
        List<WidgetInfo> result = new ArrayList<>();
        result.addAll(findWidgets(SmallWidgetProvider.class, SpotWidgetProvider.WIDGET_SIZE_SMALL));
        result.addAll(findWidgets(MediumWidgetProvider.class, SpotWidgetProvider.WIDGET_SIZE_MEDIUM));
        result.addAll(findWidgets(LargeWidgetProvider.class, SpotWidgetProvider.WIDGET_SIZE_LARGE));
        return result;
    }

    private <T> List<WidgetInfo> findWidgets(Class<T> providerClass, int widgetSize) {
        List<WidgetInfo> result = new ArrayList<>();
        ComponentName comp = new ComponentName(context, providerClass);
        int[] ids = appWidgetManager.getAppWidgetIds(comp);
        if( ids == null || ids.length == 0 )
            return result;
        for( int id : ids ) {
            WidgetSettings settings = new WidgetSettings(context,id);
            FlySpot spot = settings.getSpot();
            if( !spot.isValid() )
                continue;
            WidgetInfo info = new WidgetInfo();
            info.setWidgetId(id);
            info.setWidgetSize(widgetSize);
            info.setFlySpot(spot);
            result.add(info);
        }
        return result;
    }

    public void updateWidget(WidgetInfo widgetInfo, Station station) {
        WidgetData data = fillData(widgetInfo, station);
        if( data == null )
            return;
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), LAYOUTS[widgetInfo.getWidgetSize()]);
        if( !updateViews(remoteViews,data) )
            return;
        remoteViews.setOnClickPendingIntent(R.id.widget_icon, getUpdateIntent());
        remoteViews.setOnClickPendingIntent(R.id.widget, getTolometIntent(data));
        appWidgetManager.updateAppWidget(widgetInfo.getWidgetId(), remoteViews);
    }

    private WidgetData fillData(WidgetInfo widgetInfo, Station station) {
        FlySpot spot = widgetInfo.getFlySpot();
        if( station == null || !spot.isValid() )
            return null;
        FlyConstraint constraint = spot.getConstraints().get(0);
        if( !constraint.getStation().equals(station.getId()) )
            return null;
        Long stamp = station.getStamp();
        if( stamp == null )
            return null;

        WidgetData data = new WidgetData();
        data.id = station.getId();
        data.country = station.getCountry();
        data.name = spot.getName();
        data.date = model.getStamp(station, stamp);
        data.factor = AppSettings.getSpeedFactor(spot.getSpeedUnits());
        data.unit = context.getResources().getStringArray(R.array.pref_speedUnitEntries)[spot.getSpeedUnits()];
        data.widgetSize = widgetInfo.getWidgetSize();
        data.widgetId = widgetInfo.getWidgetId();

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
            sb.append(String.format("%.1fmb", num));
        }
        num = station.getMeteo().getIrradiance().getAt(stamp);
        if( num != null ) {
            if( sb.length() != 0 )
                sb.append(' ');
            sb.append(String.format("%dW/m2", Math.round(num.floatValue())));
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
    private final Manager model = new Manager(AppSettings.getInstance().getSelectedLanguage());
}
