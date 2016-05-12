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

    private static class WidgetData {
        String[] allWidgetSummaries;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());
        final int[] allWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        new AsyncTask<Void, Void, WidgetData>() {
            @Override
            protected WidgetData doInBackground(Void... params) {
                Station station = new Station();
                station.setCode("C042");
                station.setProviderType(WindProviderType.Euskalmet);
                model.setCurrentStation(station);
                model.refresh();
                String txt = model.getSummary(false);
                WidgetData widgetData = new WidgetData();
                widgetData.allWidgetSummaries = new String[allWidgetIds.length];
                for( int i = 0; i < allWidgetIds.length; i++ )
                    widgetData.allWidgetSummaries[i] = txt;
                return widgetData;
            }

            @Override
            protected void onPostExecute(WidgetData widgetData) {
                int i = 0;
                for (int widgetId : allWidgetIds) {
                    RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget_layout);
                    remoteViews.setTextViewText(R.id.widget_summary, widgetData.allWidgetSummaries[i++]);
                    Intent clickIntent = new Intent(getApplicationContext(), WidgetProvider.class);
                    clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                    clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    remoteViews.setOnClickPendingIntent(R.id.widget_summary, pendingIntent);
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
