package com.akrog.tolomet.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

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
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                getUpdateIntent(context));
    }

    private PendingIntent getUpdateIntent(Context context) {
        Intent intent = new Intent(FORCE_WIDGET_UPDATE);
        intent.putExtra(EXTRA_WIDGET_SIZE, getWidgetSize());
        return PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(getUpdateIntent(context));
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //startService(context, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        int widgetSize = getWidgetSize();
        if( FORCE_WIDGET_UPDATE.equals(intent.getAction()) && intent.getIntExtra(EXTRA_WIDGET_SIZE,-1) == widgetSize) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisWidget;
            switch( widgetSize ) {
                case WidgetReceiver.WIDGET_SIZE_SMALL:
                    thisWidget = new ComponentName(context, SmallWidgetReceiver.class);
                    break;
                case WidgetReceiver.WIDGET_SIZE_MEDIUM:
                    thisWidget = new ComponentName(context, MediumWidgetReceiver.class);
                    break;
                case WidgetReceiver.WIDGET_SIZE_LARGE:
                    thisWidget = new ComponentName(context, LargeWidgetReceiver.class);
                    break;
                default:
                    return;
            }
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
            startService(context,appWidgetIds);
        }
    }

    private void startService(Context context, int[] appWidgetIds) {
        Intent intent = new Intent(context.getApplicationContext(), WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
        intent.putExtra(EXTRA_WIDGET_SIZE, getWidgetSize());
        context.startService(intent);
    }
}