package com.akrog.tolometgui.ui.services;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.akrog.tolometgui.R;
import com.akrog.tolometgui.Tolomet;

public class LocationService {
    @SuppressLint("MissingPermission")
    public static Location getLocation(boolean warning ) {
        Location locationGps = null;
        Location locationNet = null;
        try {
            LocationManager locationManager = (LocationManager)Tolomet.getAppContext().getSystemService(Context.LOCATION_SERVICE);
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

    private static void showLocationDialog() {
        Context context = Tolomet.getAppContext();

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
}
