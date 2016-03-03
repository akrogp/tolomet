package com.akrog.tolomet;

import android.content.Intent;
import android.os.Bundle;

import com.akrog.tolomet.providers.WindProviderType;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

public class MapActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(savedInstanceState, R.layout.activity_map,
                R.id.favorite_item, R.id.share_item, R.id.whatsapp_item, R.id.about_item, R.id.report_item);
        /*createView(savedInstanceState, R.layout.activity_map,
                R.id.share_item, R.id.whatsapp_item, R.id.about_item, R.id.report_item);*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMarkerClickListener(this);

        Intent intent = getIntent();
        String country = intent.getStringExtra(MapActivity.EXTRA_COUNTRY);
        WindProviderType provider = WindProviderType.valueOf(intent.getStringExtra(MapActivity.EXTRA_PROVIDER));
        String code = intent.getStringExtra(MapActivity.EXTRA_STATION);

        float hueHi = BitmapDescriptorFactory.HUE_GREEN;
        float hueMi = BitmapDescriptorFactory.HUE_YELLOW;
        float hueLo = BitmapDescriptorFactory.HUE_RED;

        model.setCountry(country);
        for( Station station : model.getAllStations() ) {
            if( model.getCurrentStation() == null && station.getProviderType() == provider && station.getCode().equals(code) )
                model.setCurrentStation(station);
            float hue;
            switch( station.getProviderType().getQuality() ) {
                case Good: hue = hueHi; break;
                case Medium: hue = hueMi; break;
                default: hue = hueLo; break;
            }
            Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(station.getLatitude(), station.getLongitude()))
                            .icon(BitmapDescriptorFactory.defaultMarker(hue))
                            .title(station.getName())
                            .snippet(String.format("%s", station.getProviderType().name()))
            );
            station.setExtra(marker);
            mapMarker.put(marker,station);
        }
        showStation();
        redraw();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Station station = mapMarker.get(marker);
        if( station == null )
            return false;
        spinner.changeStation(station);
        marker.hideInfoWindow();
        return true;
    }

    private void showStation() {
        if( model.getCurrentStation() == null || model.getCurrentStation().getExtra() == null )
            return;
        lastStation = model.getCurrentStation().getName();
        ((Marker)model.getCurrentStation().getExtra()).showInfoWindow();
        LatLng cam = new LatLng(model.getCurrentStation().getLatitude(), model.getCurrentStation().getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cam, 10));
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onChangedSettings() {
    }

    @Override
    public void onSelected(Station station) {
        redraw();
        if( station.isSpecial() || station.getExtra() == null )
            return;
        showStation();
    }

    @Override
    public void getScreenShot(GoogleMap.SnapshotReadyCallback callback) {
        mMap.snapshot(callback);
    }

    @Override
    public String getScreenShotSubject() {
        return getString(R.string.ShareMapSubject);
    }

    @Override
    public String getScreenShotText() {
        return String.format("%s %s%s",
                getString(R.string.ShareMapPre),
                lastStation,
                getString(R.string.ShareMapPost)
        );
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    public static final String EXTRA_COUNTRY = "com.akrog.tolomet.MapActivity.country";
    public static final String EXTRA_PROVIDER = "com.akrog.tolomet.MapActivity.provider";
    public static final String EXTRA_STATION = "com.akrog.tolomet.MapActivity.station";

    private GoogleMap mMap;
    private String lastStation;
    private final Map<Marker,Station> mapMarker = new HashMap<>();
}
