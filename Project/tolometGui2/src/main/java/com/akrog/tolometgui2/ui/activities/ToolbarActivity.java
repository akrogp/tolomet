package com.akrog.tolometgui2.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.ui.adapters.SpinnerAdapter;
import com.akrog.tolometgui2.ui.fragments.SearchFragment;
import com.akrog.tolometgui2.ui.viewmodels.MainViewModel;

import java.lang.reflect.Method;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public abstract class ToolbarActivity extends ProgressActivity implements AdapterView.OnItemSelectedListener {
    private MainViewModel model;
    private Spinner spinner;
    private SpinnerAdapter spinnerAdapter;
    Toolbar toolbar;
    private boolean skipClick;
    private Menu menu;

    protected Toolbar configureToolbar() {
        model = ViewModelProviders.of(this).get(MainViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        spinner = toolbar.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        model.liveSelStations().observe(this, stations -> {
            skipClick = true;
            Station station = model.getCurrentStation();
            spinnerAdapter = new SpinnerAdapter(this, stations, model.getCommand());
            int pos = spinnerAdapter.getPosition(station);
            spinner.setAdapter(spinnerAdapter);
            spinner.setSelection(pos);
        });

        return toolbar;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main, menu);
        updateMenu(null);
        model.liveCurrentStation().observe(this, station -> updateMenu(station));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Station station = model.getCurrentStation();

        //noinspection SimplifiableIfStatement

        if( id == R.id.favorite_item ) {
            settings.setFavorite(station,!item.isChecked());
            model.selectStation(station);
        } else if (id == R.id.share_item) {
            return true;
        }

        updateMenu(station);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if( skipClick ) {
            skipClick = false;
            return;
        }

        MainViewModel.Command cmd = spinnerAdapter.getCommand(i);
        if( cmd == MainViewModel.Command.FIND ) {
            DialogFragment dialog = new SearchFragment();
            dialog.show(getSupportFragmentManager(), "SearchDialog");
            return;
        }

        Station station = spinnerAdapter.getStation(i);
        model.setCurrentStation(station);

        if( cmd == MainViewModel.Command.FAV )
            model.selectFavorites();
        else if( cmd == MainViewModel.Command.NEAR )
            selectNearest(() -> {}, () -> spinner.performClick());
        if( cmd != null )
            spinner.performClick();
    }

    private void updateMenu(Station station) {
        MenuItem favItem = menu.findItem(R.id.favorite_item);
        int favIcon;
        if( station == null || !station.isFavorite() )
            favIcon = R.drawable.ic_favorite_outline;
        else
            favIcon = R.drawable.ic_favorite;
        favItem.setIcon(favIcon);
        favItem.setChecked(station != null && station.isFavorite());
        setEnabled(favItem, station != null);
        setEnabled(menu.findItem(R.id.share_item), true);
    }

    private void setEnabled( MenuItem item, boolean enabled ) {
        item.setEnabled(enabled);
        item.getIcon().setAlpha(enabled?0xFF:0x42);
    }

    private void selectNearest(Runnable onNothing, Runnable onFound) {
        final Context activity = this;
        askLocation(ll -> {
            if( ll == null ) {
                Toast.makeText(activity, R.string.error_gps, Toast.LENGTH_SHORT).show();
                onNothing.run();
            } else {
                model.selectNearest(ll.getLatitude(), ll.getLongitude());
                if (model.getSelStations() == null || model.getSelStations().isEmpty()) {
                    Toast.makeText(activity, R.string.warn_near, Toast.LENGTH_SHORT).show();
                    onNothing.run();
                } else
                    onFound.run();
            }
        }, () -> {
            Toast.makeText(activity, R.string.warn_near,Toast.LENGTH_SHORT).show();
            onNothing.run();
        }, true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public boolean beginProgress() {
        if( !super.beginProgress() )
            return false;
        toolbar.setEnabled(false);
        spinner.setEnabled(false);
        return true;
    }

    @Override
    public boolean endProgress() {
        if( !super.endProgress() )
            return false;
        toolbar.setEnabled(true);
        spinner.setEnabled(true);
        return true;
    }
}