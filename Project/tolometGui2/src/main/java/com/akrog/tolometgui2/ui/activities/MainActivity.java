package com.akrog.tolometgui2.ui.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui2.BuildConfig;
import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.model.Model;
import com.akrog.tolometgui2.ui.adapters.SpinnerAdapter;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {
    private Model model;
    private Spinner spinner;
    private SpinnerAdapter spinnerAdapter;
    private boolean autoSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoSelected = true;
        model = ViewModelProviders.of(this).get(Model.class);
        model.selectNone();
        model.liveCurrentStation().observe(this, station -> ((TextView)findViewById(R.id.text_test)).setText(String.valueOf(station)));

        Toolbar toolbar = configureToolbar();
        configureDrawer(toolbar);
    }

    private Toolbar configureToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        spinner = toolbar.findViewById(R.id.spinner);
        spinnerAdapter = new SpinnerAdapter(this, model.getSelStations());
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        return toolbar;
    }

    private void configureDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView textVersion = headerView.findViewById(R.id.textVersion);
        textVersion.setText(String.format("(v%s - db%d)", BuildConfig.VERSION_NAME, 0));
    }

    @Override
    public void onSettingsChanged() {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_charts) {
            // Handle the camera action
        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_report) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Station station = (Station)adapterView.getSelectedItem();
        model.setCurrentStation(station);
        if( i == SpinnerAdapter.Command.FAV.ordinal() )
            model.selectFavorites();
        else if( i == SpinnerAdapter.Command.NEAR.ordinal() )
            selectNearest(() -> {}, () -> {
                spinnerAdapter.notifyDataSetChanged();
                spinner.performClick();
            });
        if( station == null ) {
            spinnerAdapter.notifyDataSetChanged();
            if( autoSelected )
                autoSelected = false;
            else
                spinner.performClick();
        }
    }

    private void selectNearest(Runnable onNothing, Runnable onFound) {
        final Context activity = this;
        askLocation(ll -> {
            if( ll == null ) {
                Toast.makeText(activity, R.string.error_gps, Toast.LENGTH_SHORT).show();
                onNothing.run();
            } else {
                model.selectNearest(ll.getLatitude(), ll.getLongitude());
                if (model.getSelStations().isEmpty()) {
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
}
