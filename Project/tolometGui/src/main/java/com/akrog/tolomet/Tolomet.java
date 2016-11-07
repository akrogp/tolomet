package com.akrog.tolomet;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

/**
 * Created by gorka on 6/10/16.
 */

public class Tolomet extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return context;
    }

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static Location getLocation( boolean warning ) {
        Location locationGps = null;
        Location locationNet = null;
        try {
            LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
            boolean isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNet = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if( isGps )
                locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if( isNet )
                locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
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
}
