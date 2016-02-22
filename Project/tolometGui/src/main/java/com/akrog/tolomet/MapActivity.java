package com.akrog.tolomet;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.akrog.tolomet.providers.WindProviderType;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        float hueMi = BitmapDescriptorFactory.HUE_ORANGE;
        float hueLo = BitmapDescriptorFactory.HUE_RED;

        Manager manager = new Manager();
        manager.setCountry(country);
        Station current = null;
        Marker currentMarker = null;
        for( Station station : manager.getAllStations() ) {
            if( current == null && station.getProviderType() == provider && station.getCode().equals(code) )
                current = station;
            float hue;
            if( station.getRefresh() <= 15 )
                hue = hueHi;
            else if( station.getRefresh() <= 30 )
                hue = hueMi;
            else
                hue = hueLo;
            Marker marker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(station.getLatitude(), station.getLongitude()))
                            .icon(BitmapDescriptorFactory.defaultMarker(hue))
                            .title(station.getName())
                            .snippet(String.format("%s", station.getProviderType().name()))
            );
            if( station == current )
                currentMarker = marker;
        }
        if( currentMarker != null )
            currentMarker.showInfoWindow();

        LatLng cam = new LatLng(current.getLatitude(), current.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cam, 10));
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }

    public static final String EXTRA_COUNTRY = "com.akrog.tolomet.MapActivity.country";
    public static final String EXTRA_PROVIDER = "com.akrog.tolomet.MapActivity.provider";
    public static final String EXTRA_STATION = "com.akrog.tolomet.MapActivity.station";
    private GoogleMap mMap;
}
