package com.akrog.tolomet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.akrog.tolomet.data.AppSettings;
import com.akrog.tolomet.presenters.MySpinner;
import com.akrog.tolomet.presenters.MyToolbar;
import com.akrog.tolomet.view.AndroidUtils;
import com.google.android.gms.maps.GoogleMap;

import java.lang.reflect.Method;

/**
 * Created by gorka on 24/02/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings.initialize(this);
    }

    public void createView(Bundle savedInstanceState, int layoutResId, int... buttonIds ) {
        setContentView(layoutResId);
        toolbar.initialize(this, savedInstanceState);
        toolbar.setButtons(buttonIds);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        spinner.initialize(this, null);
        redraw();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        toolbar.save(outState);
        spinner.save(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(menu);
        redraw();
        return true;
    }

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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean alertNetwork() {
        if( !isNetworkAvailable() ) {
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
        callback.onSnapshotReady(AndroidUtils.getScreenShot(getWindow().getDecorView()));
    }

    public void onFavorite(boolean fav) {
        spinner.setFavorite(fav);
    }

    public abstract void onRefresh();

    public abstract void onBrowser();

    public abstract void onChangedSettings();

    public abstract void onSelected(Station station);

    public abstract String getScreenShotSubject();

    public abstract String getScreenShotText();

    protected final Model model = Model.getInstance();
    protected final AppSettings settings = new AppSettings();
    protected final MyToolbar toolbar = new MyToolbar();
    protected final MySpinner spinner = new MySpinner();
}
