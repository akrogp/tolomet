package com.akrog.tolomet.ui.widget;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.os.AsyncTaskCompat;

/**
 * Created by gorka on 11/05/16.
 */
public class WidgetService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        final WidgetProvider widgetProvider = new WidgetProvider(this.getApplicationContext());
        //final int widgetSize = intent.getIntExtra(WidgetReceiver.EXTRA_WIDGET_SIZE, WidgetReceiver.WIDGET_SIZE_MEDIUM);
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                widgetProvider.downloadData();
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                widgetProvider.updateWidgets();
                stopSelf();
            }

            @Override
            protected void onCancelled() {
                stopSelf();
                super.onCancelled();
            }
        };
        AsyncTaskCompat.executeParallel(task);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
