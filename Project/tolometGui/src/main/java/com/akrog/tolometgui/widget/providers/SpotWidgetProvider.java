package com.akrog.tolometgui.widget.providers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.akrog.tolometgui.Tolomet;
import com.akrog.tolometgui.model.AppSettings;
import com.akrog.tolometgui.model.WidgetSettings;
import com.akrog.tolometgui.ui.services.NetworkService;
import com.akrog.tolometgui.widget.model.WidgetModel;

import java.util.concurrent.TimeUnit;

/**
 * Created by gorka on 11/05/16.
 */
public abstract class SpotWidgetProvider extends AppWidgetProvider {
    public static String FORCE_WIDGET_UPDATE = "com.akrog.tolomet.FORCE_APPWIDGET_UPDATE";
    public static String EXTRA_WIDGET_SIZE = "widgetSize";
    public static final int WIDGET_SIZE_SMALL = 0;
    public static final int WIDGET_SIZE_MEDIUM = 1;
    public static final int WIDGET_SIZE_LARGE = 2;

    protected abstract int getWidgetSize();

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

        OneTimeWorkRequest noloopWorker = new OneTimeWorkRequest.Builder(UpdateWorker.class)
            .setInitialDelay(5000L, TimeUnit.DAYS)
            .build();
        WorkManager.getInstance(context)
            .enqueueUniqueWork("noloop_work" + getWidgetSize(), ExistingWorkPolicy.KEEP, noloopWorker);

        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(
            AlarmManager.ELAPSED_REALTIME,
            AlarmManager.INTERVAL_HOUR, AlarmManager.INTERVAL_HOUR,
            getUpdateIntent(context));
    }

    private PendingIntent getUpdateIntent(Context context) {
        Intent intent = new Intent(FORCE_WIDGET_UPDATE);
        intent.putExtra(EXTRA_WIDGET_SIZE, getWidgetSize());
        return PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);

        WorkManager.getInstance(context)
            .cancelUniqueWork("noloop_work" + getWidgetSize());

        AlarmManager alarm = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(getUpdateIntent(context));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        for( int widgetId : appWidgetIds )
            new WidgetSettings(context, widgetId).delete();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // https://stackoverflow.com/questions/70654474/starting-workmanager-task-from-appwidgetprovider-results-in-endless-onupdate-cal
        if( NetworkService.isNetworkAvailable() ) {
            WorkRequest updateRequest = new OneTimeWorkRequest.Builder(UpdateWorker.class).build();
            WorkManager.getInstance(Tolomet.getAppContext()).enqueue(updateRequest);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if( FORCE_WIDGET_UPDATE.equals(action) || Intent.ACTION_USER_PRESENT.equals(action) )
            onUpdate(context, null, null);
    }

    public static class UpdateWorker extends Worker {
        public UpdateWorker(
                @NonNull Context context,
                @NonNull WorkerParameters params) {
            super(context, params);
        }

        @NonNull
        @Override
        public Result doWork() {
            WidgetModel model = new WidgetModel(Tolomet.getAppContext());
            model.download();
            model.update();
            AppSettings.getInstance().saveWidgetSkip(true);
            return Result.success();
        }
    }
}