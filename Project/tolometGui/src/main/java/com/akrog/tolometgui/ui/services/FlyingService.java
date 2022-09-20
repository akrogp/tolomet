package com.akrog.tolometgui.ui.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Station;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.AppSettings;
import com.akrog.tolometgui.model.db.DbMeteo;
import com.akrog.tolometgui.ui.activities.MainActivity;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FlyingService extends Service {
    public class LocalBinder extends Binder {
        public FlyingService getService() {
            return FlyingService.this;
        }
    }

    public FlyingService() throws SocketException {
        manager = new Manager(AppSettings.getInstance().getSelectedLanguage());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
        PendingIntent.getActivity(this, 0, notificationIntent,
            PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_FLYING)
            .setSmallIcon(R.drawable.ic_wind_unknown)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notification_xctrack))
            .setTicker(getString(R.string.FlyMode))
            .setContentIntent(pendingIntent)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build();

        startForeground(ONGOING_NOTIFICATION_ID, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        executor.shutdownNow();
        if( socket != null && !socket.isClosed() )
            socket.close();
        super.onDestroy();
    }

    public void trackStation(Station station) {
        if(  !manager.checkStation(station) )
            return;
        this.station = station;
        executor.execute(() -> refresh());
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
        Log.i("Tolomet", "refreshing");
        if( !manager.checkStation(station) )
            return;
        boolean updated;
        synchronized( station ) {
            updated = manager.refresh(station);
        }
        if( AppSettings.getInstance().isSendXctrack() )
            sendXctrack();
        if( updated )
            DbMeteo.getInstance().meteoDao().saveStation(station);
        Log.i("Tolomet", "result: " + updated);
        int dif = (int)((System.currentTimeMillis()-station.getStamp())/60/1000L);
        int interval = manager.getRefresh(station);
        int minutes = dif >= interval ? 1 : interval-dif;
        Log.i("Tolomet", "minutes: " + minutes);
        executor.schedule(() -> refresh(), minutes, TimeUnit.MINUTES);
        //executor.schedule(() -> refresh(), 5, TimeUnit.SECONDS);
    }

    private void sendXctrack() {
        try {
            if (socket == null)
                socket = new DatagramSocket();

            String status1 = getStatus(false);
            String status2 = getStatus(true);
            String name1 = station.getName();
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
            .getSummary(station, false, full, settings.getSpeedFactor(), settings.getSpeedLabel())
            //.replaceAll(",", "\\,");
            .replace(',', '.');
    }

    private static final String CHANNEL_FLYING = "com.akrog.tolomet.channel.flying";
    private static final int ONGOING_NOTIFICATION_ID = 2001;
    private final IBinder binder = new LocalBinder();
    private final Manager manager;
    private Station station;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private DatagramSocket socket;
    private final AppSettings settings = AppSettings.getInstance();
}