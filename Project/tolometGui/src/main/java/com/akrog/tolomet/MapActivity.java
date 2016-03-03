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

public class MapActivity extends BaseActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(savedInstanceState, R.layout.activity_map,
                R.id.favorite_item, R.id.share_item, R.id.whatsapp_item, R.id.about_item, R.id.report_item);
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

        Intent intent = getIntent();
        String country = intent.getStringExtra(MapActivity.EXTRA_COUNTRY);
        WindProviderType provider = WindProviderType.valueOf(intent.getStringExtra(MapActivity.EXTRA_PROVIDER));
        String code = intent.getStringExtra(MapActivity.EXTRA_STATION);

        float hueHi = BitmapDescriptorFactory.HUE_GREEN;
        float hueMi = BitmapDescriptorFactory.HUE_YELLOW;
        float hueLo = BitmapDescriptorFactory.HUE_RED;

        model.setCountry(country);
        Marker currentMarker = null;
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
            if( station == model.getCurrentStation() )
                currentMarker = marker;
        }
        if( currentMarker != null )
            currentMarker.showInfoWindow();

        LatLng cam = new LatLng(model.getCurrentStation().getLatitude(), model.getCurrentStation().getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cam, 10));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        redraw();
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onChangedSettings() {
    }

    @Override
    public void onSelected(Station station) {
    }

    @Override
    public String getScreenShotSubject() {
        return null;
    }

    @Override
    public String getScreenShotText() {
        return null;
    }

    public static final String EXTRA_COUNTRY = "com.akrog.tolomet.MapActivity.country";
    public static final String EXTRA_PROVIDER = "com.akrog.tolomet.MapActivity.provider";
    public static final String EXTRA_STATION = "com.akrog.tolomet.MapActivity.station";
    private GoogleMap mMap;
}
