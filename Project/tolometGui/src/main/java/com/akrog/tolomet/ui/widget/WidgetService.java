package com.akrog.tolomet.ui.widget;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.os.AsyncTaskCompat;

import com.akrog.tolomet.R;

/**
 * Created by gorka on 11/05/16.
 */
public class WidgetService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Notification notification = createNotification();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        if( task != null )
            return super.onStartCommand(intent, flags, startId);
        final WidgetProvider widgetProvider = new WidgetProvider(this.getApplicationContext());
        //final int widgetSize = intent.getIntExtra(WidgetReceiver.EXTRA_WIDGET_SIZE, WidgetReceiver.WIDGET_SIZE_MEDIUM);
        task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                widgetProvider.downloadData();
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                widgetProvider.updateWidgets();
                stopSelf();
                task = null;
            }

            @Override
            protected void onCancelled() {
                stopSelf();
                super.onCancelled();
                task = null;
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

    private Notification createNotification() {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_wind_unknown)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_widget))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;
        CharSequence name = getString(R.string.widget_channel_name);
        String description = getString(R.string.notification_widget);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private AsyncTask<Void, Void, Void> task;
    private static final String CHANNEL_ID = "com.akrog.tolomet.channel.widget";
}
