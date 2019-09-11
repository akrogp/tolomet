package com.akrog.tolometgui2.widget.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.Tolomet;
import com.akrog.tolometgui2.ui.services.WeakTask;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

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
        task = new UpdateTask(this);
        task.execute();
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

    private static class UpdateTask extends WeakTask<WidgetService, Void, Void, Void> {
        private final WidgetPopulator widgetPopulator = new WidgetPopulator(Tolomet.getAppContext());

        UpdateTask(WidgetService context) {
            super(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            WidgetService service = getContext();
            if( service == null )
                return null;
            widgetPopulator.downloadData();
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            WidgetService service = getContext();
            if( service == null )
                return;
            widgetPopulator.updateWidgets();
            service.stopSelf();
            service.task = null;
        }

        @Override
        protected void onCancelled() {
            WidgetService service = getContext();
            if( service == null )
                return;
            service.stopSelf();
            service.task = null;
        }
    }

    private UpdateTask task;
    private static final String CHANNEL_ID = "com.akrog.tolomet.channel.widget";
}
