package com.akrog.tolometgui.widget.providers;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.akrog.tolometgui.Tolomet;
import com.akrog.tolometgui.model.AppSettings;
import com.akrog.tolometgui.model.WidgetSettings;
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

    private final Constraints constraints = new Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build();

    protected abstract int getWidgetSize();

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);

        PeriodicWorkRequest periodicRequest = new PeriodicWorkRequest
            .Builder(UpdateWorker.class, 1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build();

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork("widget_update_" + getWidgetSize(), ExistingPeriodicWorkPolicy.KEEP, periodicRequest);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);

        WorkManager.getInstance(context)
            .cancelUniqueWork("widget_update_" + getWidgetSize());
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        for( int widgetId : appWidgetIds )
            new WidgetSettings(context, widgetId).delete();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        AppSettings settings = AppSettings.getInstance();
        long timediff = System.currentTimeMillis() - settings.getWidgetStamp();
        if( timediff < 1*60*1000 && appWidgetIds != null ) {
            WidgetModel model = new WidgetModel(Tolomet.getAppContext());
            model.update();
            return;
        }
        WorkRequest updateRequest = new OneTimeWorkRequest
            .Builder(UpdateWorker.class)
            .setConstraints(constraints)
            .build();
        LiveData<Operation.State> liveState = WorkManager.getInstance(context)
            .enqueue(updateRequest)
            .getState();
        liveState.observeForever(new Observer<Operation.State>() {
            @Override
            public void onChanged(Operation.State state) {
                liveState.removeObserver(this);
                settings.saveWidgetStamp(System.currentTimeMillis());
                WidgetModel model = new WidgetModel(Tolomet.getAppContext());
                model.update();
            }
        });
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
            WidgetModel model = new WidgetModel(getApplicationContext());
            model.download();
            return Result.success();
        }
    }
}