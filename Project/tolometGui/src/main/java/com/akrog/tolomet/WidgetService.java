package com.akrog.tolomet;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;

import com.akrog.tolomet.providers.WindProviderType;

/**
 * Created by gorka on 11/05/16.
 */
public class WidgetService extends Service {
    private enum FlyCondition { GOOD, BAD, UNKOWN };

    private static class StationData {
        String name;
        String direction;
        String speed;
        FlyCondition fly = FlyCondition.UNKOWN;
    }

    private static class WidgetData {
        StationData[] stations;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
        final int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        new AsyncTask<Void, Void, WidgetData>() {
            @Override
            protected WidgetData doInBackground(Void... params) {
                StationData stationData = fillStation(model);
                WidgetData widgetData = new WidgetData();
                widgetData.stations = new StationData[allWidgetIds.length];
                for( int i = 0; i < allWidgetIds.length; i++ )
                    widgetData.stations[i] = stationData;
                return widgetData;
            }

            private StationData fillStation(Manager model) {
                model.setCountry("ES");
                Station station = model.findStation(WindProviderType.Euskalmet,"C042");
                model.setCurrentStation(station);
                model.refresh();

                StationData data = new StationData();
                data.name = station.getName();
                long stamp = station.getStamp();
                StringBuilder sb = new StringBuilder(model.getStamp(stamp));
                Number num = station.getMeteo().getWindDirection().getAt(stamp);
                if( num != null ) {
                    sb.append(String.format(" %d (%s)", num.intValue(), model.parseDirection(num.intValue())));
                    data.fly = num.intValue() <= 30 || num.intValue() >= 300 ? FlyCondition.GOOD : FlyCondition.BAD;
                }
                data.direction = sb.toString();
                num = station.getMeteo().getWindSpeedMed().getAt(stamp);
                if( num != null ) {
                    sb = new StringBuilder(String.format("%.1f", num));
                    if( data.fly == FlyCondition.GOOD && (num.floatValue() >= 30.0 || num.floatValue() < 10.0) )
                        data.fly = FlyCondition.BAD;
                    num = station.getMeteo().getWindSpeedMax().getAt(stamp);
                    if (num != null) {
                        sb.append(String.format("~%.1f", num));
                        if( data.fly == FlyCondition.GOOD && num.floatValue() > 30.0 )
                            data.fly = FlyCondition.BAD;
                    }
                    sb.append(" km/h");
                    data.speed = sb.toString();
                }
                return data;
            }

            @Override
            protected void onPostExecute(WidgetData widgetData) {
                int i = 0;
                for (int widgetId : allWidgetIds) {
                    RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout);
                    StationData data = widgetData.stations[i++];
                    remoteViews.setTextViewText(R.id.widget_station, data.name);
                    remoteViews.setTextViewText(R.id.widget_direction, data.direction);
                    remoteViews.setTextViewText(R.id.widget_speed, data.speed);
                    if( data.fly == FlyCondition.GOOD )
                        remoteViews.setImageViewResource(R.id.widget_icon,R.drawable.ic_wind_good);
                    else if( data.fly == FlyCondition.BAD )
                        remoteViews.setImageViewResource(R.id.widget_icon,R.drawable.ic_wind_bad);
                    else
                        remoteViews.setImageViewResource(R.id.widget_icon,R.drawable.ic_wind_unknown);

                    Intent clickIntent = new Intent(getApplicationContext(), WidgetProvider.class);
                    clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                    clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
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
