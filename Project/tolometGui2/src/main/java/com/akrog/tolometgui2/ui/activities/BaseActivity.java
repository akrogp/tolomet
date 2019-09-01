package com.akrog.tolometgui2.ui.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.model.AppSettings;
import com.akrog.tolometgui2.ui.services.LocationService;
import com.gunhansancar.android.sdk.helper.LocaleHelper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.util.Consumer;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.onCreate(this);
    }

    @Override
    protected void onStart() {
        stopped = false;
        super.onStart();
    }

    @Override
    protected void onStop() {
        stopped = true;
        super.onStop();
    }

    protected boolean isStopped() {
        return stopped;
    }

    public void requestPermission(String permission, int rationale, Runnable onGranted, Runnable onDenied) {
        if (Build.VERSION.SDK_INT < 23)
            onGranted.run();
        else if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED ) {
            this.onGranted = onGranted;
            this.onDenied = onDenied;
            if( shouldShowRequestPermissionRationale(permission) ) {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle(R.string.permission_neccesary);
                alert.setMessage(rationale);
                alert.setIcon(android.R.drawable.ic_dialog_info);
                alert.setPositiveButton(R.string.ok, (dialogInterface, i) -> requestPermissions(new String[]{permission}, RC_PERMISSION));
                alert.setNegativeButton(R.string.cancel, (dialogInterface, i) -> onDenied.run());
                alert.show();
            } else
                requestPermissions(new String[]{permission}, RC_PERMISSION);
        } else
            onGranted.run();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if( grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED ) {
            switch( requestCode ) {
                case RC_PERMISSION:
                    if( onDenied != null ) {
                        onDenied.run();
                        onDenied = null;
                    }
                    break;
            }
            return;
        }
        switch( requestCode ) {
            case RC_PERMISSION:
                if( onGranted != null ) {
                    onGranted.run();
                    onGranted = null;
                }
                break;
        }
    }

    protected void lockScreenOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    protected void unlockScreenOrientation() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    public void askLocation(Consumer<Location> onOk, Runnable onError, boolean warning) {
        requestPermission(
            Manifest.permission.ACCESS_FINE_LOCATION, R.string.gps_rationale,
            () -> onOk.accept(LocationService.getLocation(warning)), onError);
    }

    public abstract void onSettingsChanged(String key);

    public static final int SETTINGS_REQUEST = 0;
    protected final AppSettings settings = AppSettings.getInstance();
    private boolean stopped = true;
    private Runnable onGranted, onDenied;
    private static final int RC_PERMISSION = 100;
}