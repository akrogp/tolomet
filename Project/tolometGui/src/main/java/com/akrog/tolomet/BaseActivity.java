package com.akrog.tolomet;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akrog.tolomet.data.AppSettings;
import com.akrog.tolomet.presenters.MySpinner;
import com.akrog.tolomet.presenters.MyToolbar;
import com.akrog.tolomet.view.AndroidUtils;
import com.google.android.gms.maps.GoogleMap;

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
        callback.onSnapshotReady(AndroidUtils.getScreenShot(getWindow().getDecorView()));
    }

    public void onFavorite(boolean fav) {
        spinner.setFavorite(fav);
    }

    public void service() {
        ProgressBar progressBar = ((ProgressBar)findViewById(R.id.progressbar));
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
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

    public void addCancelListenner( Runnable listenner ) {
        listCancel.add(listenner);
    }

    public abstract void onRefresh();

    public abstract void onBrowser();

    public abstract void onChangedSettings();

    public abstract void onSelected(Station station);

    public abstract String getScreenShotSubject();

    public abstract String getScreenShotText();

    protected final Model model = Model.getInstance();
    protected final AppSettings settings = AppSettings.getInstance();
    protected final MyToolbar toolbar = new MyToolbar();
    protected final MySpinner spinner = new MySpinner();
    private boolean inProgress = false;
    private final List<Runnable> listCancel = new ArrayList<>();
}
