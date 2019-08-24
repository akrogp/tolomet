package com.akrog.tolometgui2.ui.fragments;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.akrog.tolometgui2.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;

import androidx.annotation.Nullable;

public class MapFragment extends ToolbarFragment implements OnMapReadyCallback {
    private GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MapView mapView = getView().findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        setMapType();
        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, R.string.gps_rationale,
            () -> map.setMyLocationEnabled(true), null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.satellite_item).setChecked(settings.isSatellite());
    }

    @Override
    protected int getMenuResource() {
        return R.menu.map;
    }

    @Override
    protected int[] getLiveMenuItems() {
        return new int[0];
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.satellite_item ) {
            item.setChecked(!item.isChecked());
            settings.setSatellite(item.isChecked());
            setMapType();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setMapType() {
        map.setMapType(settings.isSatellite() ? GoogleMap.MAP_TYPE_HYBRID : GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public String getScreenshotSubject() {
        return getString(R.string.ShareMapSubject);
    }

    @Override
    public String getScreenshotText() {
        return !model.checkStation() ? "" : String.format("%s %s%s",
            getString(R.string.ShareMapPre),
            model.getCurrentStation().getName(),
            getString(R.string.ShareMapPost));
    }

    @Override
    public void onSettingsChanged() {

    }
}
