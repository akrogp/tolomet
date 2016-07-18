package com.akrog.tolomet.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.data.WidgetSettings;
import com.akrog.tolomet.data.WindConstraint;
import com.akrog.tolomet.data.WindSpot;

/**
 * Created by gorka on 11/05/16.
 */
public class WidgetService extends Service {
    private enum FlyCondition { GOOD, BAD, UNKOWN };

    private static class StationData {
        String name;
        String date;
        String directionExt;
        String directionShort;
        String speed;
        String air;
        FlyCondition fly = FlyCondition.UNKOWN;
    }

    private static class WidgetData {
        StationData[] stations;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
        final int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        final int widgetSize = intent.getIntExtra(WidgetReceiver.EXTRA_WIDGET_SIZE, WidgetReceiver.WIDGET_SIZE_MEDIUM);
        new AsyncTask<Void, Void, WidgetData>() {
            @Override
            protected WidgetData doInBackground(Void... params) {
                WidgetData widgetData = new WidgetData();
                widgetData.stations = new StationData[allWidgetIds.length];
                for( int i = 0; i < allWidgetIds.length; i++ )
                    widgetData.stations[i] = fillStation(allWidgetIds[i]);
                return widgetData;
            }

            private StationData fillStation(int widgetId) {
                WidgetSettings settings = new WidgetSettings(getApplicationContext(),widgetId);
                WindSpot spot = settings.getSpot();
                if( !spot.isValid() )
                    return null;
                WindConstraint constraint = spot.getConstraints().get(0);
                model.setCountry(spot.getCountry());
                Station station = model.findStation(constraint.getStation());
                if( station == null )
                    return null;
                model.setCurrentStation(station);
                if( !model.refresh() )
                    return null;

                StationData data = new StationData();
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
                    if (num.floatValue() >= 98.0)
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

            @Override
            protected void onPostExecute(WidgetData widgetData) {
                int[] layouts = {R.layout.widget_layout_small, R.layout.widget_layout_middle, R.layout.widget_layout_large};
                int i = 0;
                for (int widgetId : allWidgetIds) {
                    RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), layouts[widgetSize]);
                    StationData data = widgetData.stations[i++];
                    if( data == null )
                        continue;
                    Intent clickIntent;
                    switch( widgetSize ) {
                        case WidgetReceiver.WIDGET_SIZE_SMALL:
                            clickIntent = new Intent(getApplicationContext(), SmallWidgetReceiver.class);
                            remoteViews.setTextViewText(R.id.widget_station, data.name);
                            //remoteViews.setTextViewText(R.id.widget_date, data.date);
                            break;
                        case WidgetReceiver.WIDGET_SIZE_MEDIUM:
                            clickIntent = new Intent(getApplicationContext(), MediumWidgetReceiver.class);
                            remoteViews.setTextViewText(R.id.widget_station, data.name);
                            remoteViews.setTextViewText(R.id.widget_date, data.date);
                            remoteViews.setTextViewText(R.id.widget_direction, data.directionExt);
                            remoteViews.setTextViewText(R.id.widget_speed, data.speed);
                            break;
                        case WidgetReceiver.WIDGET_SIZE_LARGE:
                            clickIntent = new Intent(getApplicationContext(), LargeWidgetReceiver.class);
                            remoteViews.setTextViewText(R.id.widget_station, data.name);
                            remoteViews.setTextViewText(R.id.widget_date, data.date);
                            remoteViews.setTextViewText(R.id.widget_air, data.air);
                            remoteViews.setTextViewText(R.id.widget_wind, data.directionExt + " " + data.speed + " km/h");
                            break;
                        default:
                            continue;
                    }
                    if( data.fly == FlyCondition.GOOD )
                        remoteViews.setImageViewResource(R.id.widget_icon,R.drawable.ic_wind_good);
                    else if( data.fly == FlyCondition.BAD )
                        remoteViews.setImageViewResource(R.id.widget_icon,R.drawable.ic_wind_bad);
                    else
                        remoteViews.setImageViewResource(R.id.widget_icon,R.drawable.ic_wind_unknown);
                    clickIntent.setAction(WidgetReceiver.FORCE_WIDGET_UPDATE);
                    clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
                    clickIntent.putExtra(WidgetReceiver.EXTRA_WIDGET_SIZE, widgetSize);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);
                    appWidgetManager.updateAppWidget(widgetId, remoteViews);
                }
                stopSelf();
            }

            @Override
            protected void onCancelled() {
                stopSelf();
                super.onCancelled();
            }
        }.execute();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private final Manager model = new Manager();
}
