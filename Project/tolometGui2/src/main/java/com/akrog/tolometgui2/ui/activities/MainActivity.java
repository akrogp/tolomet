package com.akrog.tolometgui2.ui.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.akrog.tolometgui2.BuildConfig;
import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.model.DbTolomet;
import com.akrog.tolometgui2.ui.fragments.ChartsFragment;
import com.akrog.tolometgui2.ui.viewmodels.MainViewModel;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends ToolbarActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        model = ViewModelProviders.of(this).get(MainViewModel.class);
        model.selectStation(settings.loadStation());
        //model.liveCurrentStation().observe(this, station -> {});

        Toolbar toolbar = configureToolbar();
        configureDrawer(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ChartsFragment chartsFragment = new ChartsFragment();
        fragmentTransaction.add(R.id.content_layout, chartsFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        settings.saveStation(model.getCurrentStation());
    }

    private void configureDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar);

        View headerView = navigationView.getHeaderView(0);
        TextView textVersion = headerView.findViewById(R.id.textVersion);
        textVersion.setText(String.format("(v%s - db%d)", BuildConfig.VERSION_NAME, DbTolomet.getInstance().getVersion()));
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
}
