package com.akrog.tolomet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;

import androidx.multidex.MultiDexApplication;

import java.io.File;

/**
 * Created by gorka on 6/10/16.
 */

public class Tolomet extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }

    public static File getAvailableCacheDir() {
        if( context == null )
            return null;
        File dir = context.getExternalCacheDir();
        if( dir != null )
            return dir;
        return context.getCacheDir();
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @SuppressLint("MissingPermission")
    public static Location getLocation(boolean warning ) {
        Location locationGps = null;
        Location locationNet = null;
        try {
            LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNet = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if( isGps ) {
                locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if( locationGps == null )
                    locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER,locationListener,null);
            }
            if( isNet ) {
                locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if( locationNet == null )
                    locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,locationListener,null);
            }
            else if( !isGps && warning )
                showLocationDialog();
        } catch (Exception e) {
        }
        if( locationGps == null )
            return locationNet;
        if( locationNet == null )
            return locationGps;
        return locationGps.getTime() >= locationNet.getTime() ? locationGps : locationNet;
    }

    public static boolean supportsMap() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    private static void showLocationDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage(context.getString(R.string.warn_gps));
        dialog.setPositiveButton(context.getString(R.string.gps_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                context.startActivity(myIntent);
            }
        });
        dialog.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        });
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.show();
    }

    private static Context context;
    private static LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
        }
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }
        @Override
        public void onProviderEnabled(String s) {
        }
        @Override
        public void onProviderDisabled(String s) {
        }
    };
    public static final String FILE_PROVIDER_AUTHORITY = "com.akrog.tolomet.ui.FileProvider";
}
