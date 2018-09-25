package com.akrog.tolomet.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akrog.tolomet.model.Consumer;
import com.akrog.tolomet.viewmodel.Model;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.viewmodel.AppSettings;
import com.akrog.tolomet.ui.presenters.MySpinner;
import com.akrog.tolomet.ui.presenters.MyToolbar;
import com.akrog.tolomet.ui.view.AndroidUtils;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.gunhansancar.android.sdk.helper.LocaleHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gorka on 24/02/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.onCreate(this);
    }

    public void createSpinnerView(Bundle savedInstanceState, int layoutResId, int... buttonIds ) {
        setContentView(layoutResId);
        toolbar.initialize(this, savedInstanceState);
        toolbar.setButtons(buttonIds);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void createTitleView(Bundle savedInstanceState, int layoutResId, int... buttonIds ) {
        createSpinnerView(savedInstanceState, layoutResId, buttonIds);
        findViewById(R.id.station_spinner).setVisibility(View.GONE);
        findViewById(R.id.toolbar_title).setVisibility(View.VISIBLE);
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

    @Override
    protected void onResume() {
        super.onResume();
        spinner.initialize(this, null);
        Intent intent = getIntent();
        String id = intent.getStringExtra(EXTRA_STATION);
        if( id != null ) {
            intent.removeExtra(EXTRA_STATION);
            Station station = model.findStation(id);
            if( station != null )
                spinner.selectStation(station);
        }
        redraw();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        toolbar.save(outState);
        spinner.save(outState);
    }

    protected void requestPermission(String permission, int rationale, Runnable onGranted, Runnable onDenied) {
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

    protected void receiveDynamicLinks() {
        final Context context = this;
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(getIntent())
            .addOnSuccessListener(new OnSuccessListener<PendingDynamicLinkData>() {
                @Override
                public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                    if( pendingDynamicLinkData == null )
                        return;
                    Uri link = pendingDynamicLinkData.getLink();
                    String[] fields = link.getPathSegments().toArray(new String[0]);
                    if( !fields[0].equalsIgnoreCase("app") )
                        return;
                    Intent intent;
                    switch (fields[1]) {
                        case ChartsActivity.PATH:
                            if( fields.length == 3 ) {
                                intent = new Intent(context, ChartsActivity.class);
                                intent.putExtra(EXTRA_STATION, fields[2]);
                                startActivity(intent);
                            }
                            break;
                        case MapActivity.PATH:
                            if( fields.length == 3 ) {
                                intent = new Intent(context, MapActivity.class);
                                intent.putExtra(EXTRA_STATION, fields[2]);
                                startActivity(intent);
                            }
                            break;
                        case HelpActivity.PATH:
                            intent = new Intent(context, HelpActivity.class);
                            startActivity(intent);
                            break;
                        case InfoActivity.PATH:
                            if( fields.length == 3 ) {
                                intent = new Intent(context, InfoActivity.class);
                                intent.putExtra(EXTRA_STATION, fields[2]);
                                startActivity(intent);
                            }
                            break;
                        case ProviderActivity.PATH:
                            if( fields.length == 3 ) {
                                intent = new Intent(context, ProviderActivity.class);
                                intent.putExtra(EXTRA_STATION, fields[2]);
                                startActivity(intent);
                            }
                            break;
                    }
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(menu);
        redraw();
        return true;
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        Log.i(getClass().getSimpleName(), "called");
        if (menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( resultCode == RESULT_OK )
            spinner.loadState(null);
        if( requestCode == SETTINGS_REQUEST ) {
            if( data.getBooleanExtra(LocaleHelper.SELECTED_LANGUAGE,false) ) {
                //Toast.makeText(this,"recreate",Toast.LENGTH_SHORT).show();
                recreate();
            } else
                onSettingsChanged();
        }
    }

    public AppSettings getSettings() {
        return settings;
    }

    public boolean alertNetwork() {
        if( !Tolomet.isNetworkAvailable() ) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage( getString(R.string.NoNetwork) );
            alertDialog.show();
            return true;
        }
        return false;
    }

    public void redraw() {
        toolbar.updateView();
        spinner.updateView();
    }

    public void getScreenShot(GoogleMap.SnapshotReadyCallback callback) {
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.storage_rationale,
                () -> callback.onSnapshotReady(AndroidUtils.getScreenShot(getWindow().getDecorView())), null);
    }

    public void onFavorite(boolean fav) {
        spinner.setFavorite(fav);
    }

    public ProgressBar getProgressBar() {
        return (ProgressBar)findViewById(R.id.progressbar);
    }

    public boolean beginProgress() {
        if( inProgress )
            return false;
        toolbar.setEnabled(false);
        spinner.setEnabled(false);
        ProgressBar progressBar = getProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        inProgress = true;
        return true;
    }

    public boolean endProgress() {
        if( !inProgress )
            return false;
        ProgressBar progressBar = getProgressBar();
        progressBar.setVisibility(View.GONE);
        inProgress = false;
        toolbar.setEnabled(true);
        spinner.setEnabled(true);
        listCancel.clear();
        return true;
    }

    @Override
    public void onBackPressed() {
        if( inProgress )
            onCancel();
        else
            super.onBackPressed();
    }

    public void onCancel() {
        Toast.makeText(Tolomet.getAppContext(),getString(R.string.DownloadCancelled),Toast.LENGTH_SHORT).show();
        for( Runnable listenner : listCancel )
            listenner.run();
        endProgress();
    }

    public void askLocation(Consumer<Location> onOk, Runnable onError, boolean warning) {
        requestPermission(
                Manifest.permission.ACCESS_FINE_LOCATION, R.string.gps_rationale,
                () -> onOk.accept(Tolomet.getLocation(warning)), onError);
    }

    public void addCancelListenner( Runnable listenner ) {
        listCancel.add(listenner);
    }

    public abstract void onRefresh();

    public abstract void onBrowser();

    public abstract void onSettingsChanged();

    public abstract void onSelected(Station station);

    public abstract String getScreenShotSubject();

    public abstract String getScreenShotText();

    public abstract String getRelativeLink();

    public static final String EXTRA_STATION = "com.akrog.tolomet.ui.activities.BaseActivity.station";
    public static final int SETTINGS_REQUEST = 0;
    public static final int MAP_REQUEST = 1;
    protected final Model model = Model.getInstance();
    protected final AppSettings settings = AppSettings.getInstance();
    protected final MyToolbar toolbar = new MyToolbar();
    protected final MySpinner spinner = new MySpinner();
    private boolean inProgress = false;
    private final List<Runnable> listCancel = new ArrayList<>();
    private boolean stopped = true;
    private Runnable onGranted, onDenied;
    private static final int RC_PERMISSION = 100;
}
