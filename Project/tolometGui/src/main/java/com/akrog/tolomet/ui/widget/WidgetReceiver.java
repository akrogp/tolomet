package com.akrog.tolomet.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;

import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.viewmodel.WidgetSettings;

/**
 * Created by gorka on 11/05/16.
 */
public abstract class WidgetReceiver extends AppWidgetProvider {
    public static String FORCE_WIDGET_UPDATE = "com.akrog.tolomet.FORCE_APPWIDGET_UPDATE";
    public static String EXTRA_WIDGET_SIZE = "widgetSize";
    public static final int WIDGET_SIZE_SMALL = 0;
    public static final int WIDGET_SIZE_MEDIUM = 1;
    public static final int WIDGET_SIZE_LARGE = 2;

    protected abstract int getWidgetSize();

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        /*AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                getUpdateIntent(context));*/
    }

    /*private PendingIntent getUpdateIntent(Context context) {
        Intent intent = new Intent(FORCE_WIDGET_UPDATE);
        intent.putExtra(EXTRA_WIDGET_SIZE, getWidgetSize());
        return PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
    }*/

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        /*AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(getUpdateIntent(context));*/
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        for( int widgetId : appWidgetIds )
            new WidgetSettings(context, widgetId).delete();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // Update is carried out by a custom FORCE_WIDGET_UPDATE action
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if( AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)
                || FORCE_WIDGET_UPDATE.equals(action)
                || Intent.ACTION_USER_PRESENT.equals(action) )
            if(Tolomet.isNetworkAvailable())
                startService(context);
    }

    private void startService(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), WidgetService.class);
        intent.putExtra(EXTRA_WIDGET_SIZE, getWidgetSize());
        //context.startService(intent);
        ContextCompat.startForegroundService(context, intent);
    }
}