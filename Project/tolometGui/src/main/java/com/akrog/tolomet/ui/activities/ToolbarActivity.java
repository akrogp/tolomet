package com.akrog.tolomet.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.model.Consumer;
import com.akrog.tolomet.ui.presenters.MySpinner;
import com.akrog.tolomet.ui.presenters.MyToolbar;
import com.akrog.tolomet.ui.view.AndroidUtils;
import com.akrog.tolomet.viewmodel.AppSettings;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.lang.reflect.Method;

/**
 * Created by gorka on 24/02/16.
 */
public abstract class ToolbarActivity extends ProgressActivity {

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

    public boolean beginProgress() {
        if( !super.beginProgress() )
            return false;
        toolbar.setEnabled(false);
        spinner.setEnabled(false);
        return true;
    }

    public boolean endProgress() {
        if( !super.endProgress() )
            return false;
        toolbar.setEnabled(true);
        spinner.setEnabled(true);
        return true;
    }

    public void askLocation(Consumer<Location> onOk, Runnable onError, boolean warning) {
        requestPermission(
                Manifest.permission.ACCESS_FINE_LOCATION, R.string.gps_rationale,
                () -> onOk.accept(Tolomet.getLocation(warning)), onError);
    }

    public abstract void onRefresh();

    public abstract void onBrowser();

    public abstract void onSelected(Station station);

    public abstract String getScreenShotSubject();

    public abstract String getScreenShotText();

    public abstract String getRelativeLink();

    public static final String EXTRA_STATION = "com.akrog.tolomet.ui.activities.ToolbarActivity.station";
    public static final int MAP_REQUEST = 1;
    protected final MyToolbar toolbar = new MyToolbar();
    protected final MySpinner spinner = new MySpinner();
}
