package com.akrog.tolomet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.akrog.tolomet.data.DbTolomet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(savedInstanceState, R.layout.activity_map,
                R.id.favorite_item,
                R.id.charts_item, R.id.info_item, R.id.origin_item, R.id.browser_item,
                R.id.share_item, R.id.whatsapp_item,
                R.id.about_item, R.id.report_item);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( model.getCurrentStation().getExtra() != null )
            showStation();
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
        mMap.setOnCameraChangeListener(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra(MapActivity.EXTRA_STATION);
        model.setCurrentStation(model.findStation(id));

        zoom(model.getCurrentStation());

        redraw();
    }

    private void zoom(Station station) {
        zoom(station.getLatitude(), station.getLongitude());
    }

    private void zoom(double lat, double lon) {
        LatLng cam = new LatLng(lat, lon);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cam, 10));
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        List<Station> stations = DbTolomet.getInstance().findGeoStations(
                bounds.northeast.latitude, bounds.northeast.longitude,
                bounds.southwest.latitude, bounds.southwest.longitude);

        for( Map.Entry<Station,Marker> entry : new ArrayList<>(station2marker.entrySet()) ) {
            if( !stations.contains(entry.getKey()) ) {
                station2marker.remove(entry.getKey());
                marker2station.remove(entry.getValue());
                entry.getValue().remove();
            }
        }

        float hueHi = BitmapDescriptorFactory.HUE_GREEN;
        float hueMi = BitmapDescriptorFactory.HUE_YELLOW;
        float hueLo = BitmapDescriptorFactory.HUE_RED;
        for( Station station : stations ) {
            if( station2marker.containsKey(station) )
                continue;
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
            station2marker.put(station, marker);
            marker2station.put(marker,station);
        }

        showStation();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Station station = marker2station.get(marker);
        if( station == null )
            return false;
        spinner.selectStation(station);
        marker.showInfoWindow();
        return true;
    }

    private void showStation() {
        if( model.getCurrentStation() == null || model.getCurrentStation().isSpecial() )
            return;
        Marker marker = station2marker.get(model.getCurrentStation());
        if( marker == null )
            return;
        lastStation = model.getCurrentStation().getName();
        marker.showInfoWindow();
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onBrowser() {
        double lat, lon;
        if( model.getCurrentStation() != null && !model.getCurrentStation().isSpecial() ) {
            lat = model.getCurrentStation().getLatitude();
            lon = model.getCurrentStation().getLongitude();
        } else {
            lat = mMap.getCameraPosition().target.latitude;
            lon = mMap.getCameraPosition().target.longitude;
        }
        String url = getUrl(lat,lon);
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static String getUrl( double lat, double lon ) {
        return String.format(Locale.ENGLISH,
                "http://maps.google.com/maps?q=loc:%f,%f", lat, lon);
    }

    @Override
    public void onChangedSettings() {
    }

    @Override
    public void onSelected(Station station) {
        redraw();
        if( station.isSpecial() )
            return;
        zoom(station);
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
    public static final String EXTRA_STATION = "com.akrog.tolomet.MapActivity.station";

    private GoogleMap mMap;
    private String lastStation;
    private final Map<Marker,Station> marker2station = new HashMap<>();
    private final Map<Station,Marker> station2marker = new HashMap<>();
}
