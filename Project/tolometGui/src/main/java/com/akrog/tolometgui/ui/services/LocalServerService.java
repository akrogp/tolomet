package com.akrog.tolometgui.ui.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.LiveData;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.utils.StringUtils;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.AppSettings;
import com.akrog.tolometgui.model.db.DbMeteo;
import com.akrog.tolometgui.model.db.DbTolomet;
import com.akrog.tolometgui.model.db.MeteoDao;
import com.akrog.tolometgui.model.db.StationDao;
import com.akrog.tolometgui.ui.activities.MainActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java9.util.concurrent.CompletableFuture;

public class LocalServerService extends LifecycleService {

    public class NotificationActionsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            stopForeground(true);
        }
    }

    public LocalServerService() {
        manager = new Manager(AppSettings.getInstance().getSelectedLanguage());
    }

    @Override
    public void onCreate() {
        super.onCreate();

        receiver = new NotificationActionsReceiver();
        IntentFilter filter = new IntentFilter(ACTION_STOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
        } else
            this.registerReceiver(receiver, filter);

        executor.execute(() -> {
            try {
                serve();
            } catch (IOException e) {
                e.printStackTrace();
                stopForeground(true);
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if( !settings.isLocalServer() ) {
            stopForeground(true);
            return super.onStartCommand(intent, flags, startId);
        }

        createNotificationChannel();

        int pendingIntentFlags = PendingIntent.FLAG_IMMUTABLE;

        Intent contentIntent = new Intent(this, MainActivity.class);
        PendingIntent contentPendingIntent =
                PendingIntent.getActivity(this, 0, contentIntent, pendingIntentFlags);

        Intent stopIntent = new Intent(ACTION_STOP);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(this, 0, stopIntent, pendingIntentFlags);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_SERVER)
            .setSmallIcon(R.drawable.ic_wind_unknown)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notification_serving))
            .setTicker(getString(R.string.notification_serving))
            .setContentIntent(contentPendingIntent)
            .addAction(R.drawable.ic_stop, getString(R.string.stop), stopPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build();

        startForeground(ONGOING_NOTIFICATION_ID, notification);

        return super.onStartCommand(intent, flags, startId);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return;
        CharSequence name = getString(R.string.server_channel_name);
        String description = getString(R.string.notification_serving);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_SERVER, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        stop = true;
        executor.shutdownNow();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    private void serve() throws IOException {
        DatagramSocket socket = new DatagramSocket(settings.getPortLocalServer());
        socket.setSoTimeout(TIMEOUT);
        byte[] buf = new byte[BUFFER];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        while(!stop) {
            try{
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                byte[] output = process(packet).getBytes();
                if( output != null )
                    socket.send(new DatagramPacket(output, output.length, address, port));
            } catch (SocketTimeoutException e) {
            }
        }
        socket.close();
    }

    private String process(DatagramPacket packet) {
        if( packet.getLength() < SIGNATURE.length() )
            return INVALID;
        String input = new String(packet.getData(), 0, packet.getLength());
        String[] fields = input.split(SEP);
        if(fields.length < 2 || !SIGNATURE.equals(fields[0]))
            return INVALID;
        String cmd = fields[1];
        try {
            if (cmd.equals(CMD_GEO))
                return processGeo(Double.parseDouble(fields[2]), Double.parseDouble(fields[3]), Double.parseDouble(fields[4]), Double.parseDouble(fields[5]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return INVALID;
    }

    private String processGeo(double lat1, double lon1, double lat2, double lon2) throws ExecutionException, InterruptedException {
        StringBuilder output = new StringBuilder(CMD_GEO);
        for(Station station : db.findGeoStations(lat1, lon1, lat2, lon2)) {
            output.append("\n");
            station = toFuture(meteo.loadStation(station)).get();
            if( manager.refresh(station) )
                meteo.saveStation(station);
            long stamp = station.getStamp();
            output.append(StringUtils.toCsv(SEP,
                station.getId(),
                station.getName(),
                station.getProviderType().name(),
                station.getProviderType().getQuality().name(),
                StringUtils.formatDecimal(station.getLatitude()),
                StringUtils.formatDecimal(station.getLongitude()),
                stamp,
                StringUtils.formatDecimal(station.getMeteo().getWindDirection().getAt(stamp)),
                StringUtils.formatDecimal(station.getMeteo().getWindSpeedMed().getAt(stamp)),
                StringUtils.formatDecimal(station.getMeteo().getWindSpeedMax().getAt(stamp))
            ));
        }
        return output.toString();
    }

    private <T> CompletableFuture<T> toFuture(LiveData<T> liveData) {
        CompletableFuture<T> future = new CompletableFuture<>();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> liveData.observe(this, future::complete));
        return future;
    }

    //public static final String ACTION_START = "com.akrog.tolomet.action.server.start";
    public static final String ACTION_STOP = "com.akrog.tolomet.action.server.stop";
    public static final int TIMEOUT = 1000;
    public static final int BUFFER = 30000;
    public static final String SIGNATURE = "TOLO";
    public static final String SEP = "\t";
    //public static final String SEP = ",";
    public static final String INVALID = "Invalid request";
    public static final String CMD_GEO = "GEO";
    private static final String CHANNEL_SERVER = "com.akrog.tolomet.channel.server";
    private static final int ONGOING_NOTIFICATION_ID = 3001;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private volatile boolean stop = false;
    private final StationDao db = DbTolomet.getInstance().stationDao();
    private final MeteoDao meteo = DbMeteo.getInstance().meteoDao();
    private final Manager manager;
    private final AppSettings settings = AppSettings.getInstance();
    private NotificationActionsReceiver receiver;
}