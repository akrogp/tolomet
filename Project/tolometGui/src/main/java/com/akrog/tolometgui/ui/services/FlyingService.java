package com.akrog.tolometgui.ui.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Station;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.AppSettings;
import com.akrog.tolometgui.model.db.DbMeteo;
import com.akrog.tolometgui.ui.activities.MainActivity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FlyingService extends LifecycleService {
    public class LocalBinder extends Binder {
        public FlyingService getService() {
            return FlyingService.this;
        }
    }

    public class NotificationActionsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopForeground(true);
        }
    }

    public FlyingService() {
        manager = new Manager(AppSettings.getInstance().getSelectedLanguage());
    }

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        int pendingIntentFlags = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0;

        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent contentPendingIntent =
        PendingIntent.getActivity(this, 0, contentIntent, pendingIntentFlags);

        Intent landIntent = new Intent(ACTION_LAND);
        PendingIntent landPendingIntent = PendingIntent.getBroadcast(this, 0, landIntent, pendingIntentFlags);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_FLYING)
            .setSmallIcon(R.drawable.ic_wind_unknown)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notification_xctrack))
            .setTicker(getString(R.string.FlyMode))
            .setContentIntent(contentPendingIntent)
            .addAction(R.drawable.ic_land_mode, getString(R.string.LandMode), landPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build();

        startForeground(ONGOING_NOTIFICATION_ID, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        receiver = new NotificationActionsReceiver();
        IntentFilter filter = new IntentFilter(ACTION_LAND);
        this.registerReceiver(receiver, filter);

        currentMeteo.observe(this, station -> {
            if( AppSettings.getInstance().isSendXctrack() )
                executor.execute(this::sendXctrack);
        });
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);

        executor.shutdownNow();
        if( socket != null && !socket.isClosed() )
            socket.close();

        super.onDestroy();
    }

    public void trackStation(Station station) {
        if(  !manager.checkStation(station) )
            return;
        liveStation.setValue(station);
        executor.execute(this::refresh);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;
        CharSequence name = getString(R.string.flying_channel_name);
        String description = getString(R.string.notification_xctrack);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_FLYING, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private void refresh() {
        Station station = liveStation.getValue();
        if( !manager.checkStation(station) )
            return;
        Station clone = station.clone();
        if(manager.refresh(clone))
            DbMeteo.getInstance().meteoDao().saveStation(clone);
        int dif = (int)((System.currentTimeMillis()-station.getStamp())/60/1000L);
        int interval = manager.getRefresh(clone);
        int minutes = dif >= interval ? 1 : interval-dif;
        executor.schedule(this::refresh, minutes, TimeUnit.MINUTES);
    }

    private void sendXctrack() {
        try {
            if (socket == null)
                socket = new DatagramSocket();

            String status1 = getStatus(false);
            String status2 = getStatus(true);
            String name1 = liveStation.getValue().getName();
            int maxLen = Math.min(name1.length(), 10);
            String name2 = name1.substring(0, maxLen);
            String xctod = String.format("$XCTOD,%s %s,%s %s,%s %s,%s %s,%s,%s,%s,%s",
                name1, status1, // 1
                name1, status2, // 2
                name2, status1, // 3
                name2, status2, // 4
                name1, name2, status1, status2);    // 5-8
            byte[] buffer = xctod.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), settings.getPortXctrack());
            socket.send(packet);
        } catch( Exception e ) {
            e.printStackTrace();
        }
    }

    private String getStatus(boolean full) {
        return manager
            .getSummary(liveStation.getValue(), false, full, settings.getSpeedFactor(), settings.getSpeedLabel())
            //.replaceAll(",", "\\,");
            .replace(',', '.');
    }

    public static final String ACTION_LAND = "com.akrog.tolomet.action.land";
    private static final String CHANNEL_FLYING = "com.akrog.tolomet.channel.flying";
    private static final int ONGOING_NOTIFICATION_ID = 2001;
    private final IBinder binder = new LocalBinder();
    private final Manager manager;
    private final MutableLiveData<Station> liveStation = new MutableLiveData<>();
    private final LiveData<Station> currentMeteo = Transformations.switchMap(liveStation,
        station -> DbMeteo.getInstance().meteoDao().loadStation(station));
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private DatagramSocket socket;
    private final AppSettings settings = AppSettings.getInstance();
    private NotificationActionsReceiver receiver;
}