package com.akrog.tolomet.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import com.akrog.tolomet.Country;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.viewmodel.DbTolomet;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapActivity extends ToolbarActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraChangeListener {
    public static final String PATH = "map";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createSpinnerView(savedInstanceState, R.layout.activity_map,
                R.id.favorite_item,
                R.id.charts_item, R.id.info_item, R.id.origin_item, R.id.browser_item,
                R.id.share_item, //R.id.whatsapp_item,
                R.id.help_item, R.id.about_item, R.id.report_item);
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, R.string.gps_rationale,
                () -> mMap.setMyLocationEnabled(true), null);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnCameraChangeListener(this);

        if( !model.getCurrentStation().isSpecial() )
            zoom(model.getCurrentStation());
        else {
            Location location = Tolomet.getLocation(true);
            if( location != null )
                zoom(location.getLatitude(), location.getLongitude());
        }

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
        float minZoom = 5.0f;
        if( cameraPosition.zoom < minZoom ) {
            mMap.animateCamera(CameraUpdateFactory.zoomTo(minZoom));
            return;
        }
        LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
        List<Station> stations = DbTolomet.getInstance().findGeoStations(
                bounds.northeast.latitude, bounds.northeast.longitude,
                bounds.southwest.latitude, bounds.southwest.longitude);
        if( stations.size() > REGION_LIMIT)
            updateCountryClusters(stations);
        else if( stations.size() > STATION_LIMIT )
            updateRegionClusters(stations);
        else
            updateStationMarkers(stations);
    }

    private void updateStationMarkers(List<Station> stations) {
        if( isClustered() )
            clearMarkers();
        else
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
                    .snippet(String.format("%s", station.getProviderType().name())));
            station2marker.put(station, marker);
            marker2station.put(marker,station);
        }

        showStation();
    }

    private void updateRegionClusters(List<Station> stations) {
        clearMarkers();
        Map<Integer,List<Station>> clusters = new HashMap<>();
        for( Station station : stations ) {
            List<Station> list = clusters.get(station.getRegion());
            if( list == null ) {
                list = new ArrayList<>();
                clusters.put(station.getRegion(),list);
            }
            list.add(station);
        }
        Map<String,String> countries = new HashMap<>();
        for( Country country : DbTolomet.getInstance().getCountries() )
            countries.put(country.getCode(), country.getName());
        float hue;
        for(Map.Entry<Integer,List<Station>> cluster : clusters.entrySet() ) {
            Station station = selectMedian(cluster.getValue());
            DbTolomet.Counts info = DbTolomet.getInstance().getRegionCounts(cluster.getKey());
            if( info.getName().length() == 2 ) {
                hue = BitmapDescriptorFactory.HUE_BLUE;
                info.setName(countries.get(info.getName()));
            } else
                hue = BitmapDescriptorFactory.HUE_VIOLET;
            String desc = cluster.getValue().size() == info.getStationCount() ?
                    String.valueOf(info.getStationCount()) :
                    String.format("%d/%d", cluster.getValue().size(), info.getStationCount());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(station.getLatitude(), station.getLongitude()))
                    .icon(BitmapDescriptorFactory.defaultMarker(hue))
                    .title(info.getName())
                    .snippet(desc));
            marker2station.put(marker,station);
        }
    }

    private void updateCountryClusters(List<Station> stations) {
        clearMarkers();
        Map<String,List<Station>> clusters = new HashMap<>();
        for( Station station : stations ) {
            List<Station> list = clusters.get(station.getCountry());
            if( list == null ) {
                list = new ArrayList<>();
                clusters.put(station.getCountry(),list);
            }
            list.add(station);
        }
        Map<String,String> countries = new HashMap<>();
        for( Country country : DbTolomet.getInstance().getCountries() )
            countries.put(country.getCode(), country.getName());
        float hue = BitmapDescriptorFactory.HUE_BLUE;
        for(Map.Entry<String,List<Station>> cluster : clusters.entrySet() ) {
            Station station = selectMedian(cluster.getValue());
            DbTolomet.Counts info = DbTolomet.getInstance().getCountryCounts(cluster.getKey());
            info.setName(countries.get(info.getName()));
            String desc = cluster.getValue().size() == info.getStationCount() ?
                    String.valueOf(info.getStationCount()) :
                    String.format("%d/%d", cluster.getValue().size(), info.getStationCount());
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(station.getLatitude(), station.getLongitude()))
                    .icon(BitmapDescriptorFactory.defaultMarker(hue))
                    .title(info.getName())
                    .snippet(desc));
            marker2station.put(marker,station);
        }
    }

    private void clearMarkers() {
        station2marker.clear();
        for( Marker marker : marker2station.keySet() )
            marker.remove();
        marker2station.clear();
    }

    private Station selectMedian(List<Station> stations) {
        Collections.sort(stations, new Comparator<Station>() {
            @Override
            public int compare(Station lhs, Station rhs) {
                return (int)Math.signum(lhs.getLatitude()-rhs.getLatitude());
            }
        });
        final double medLat = stations.get(stations.size()/2).getLatitude();
        Collections.sort(stations, new Comparator<Station>() {
            @Override
            public int compare(Station lhs, Station rhs) {
                return (int)Math.signum(lhs.getLongitude()-rhs.getLongitude());
            }
        });
        final double medLon = stations.get(stations.size()/2).getLongitude();
        Collections.sort(stations, new Comparator<Station>() {
            @Override
            public int compare(Station lhs, Station rhs) {
                double dist1 = Math.abs(lhs.getLatitude()-medLat)+Math.abs(lhs.getLongitude()-medLon);
                double dist2 = Math.abs(rhs.getLatitude()-medLat)+Math.abs(rhs.getLongitude()-medLon);
                return (int)Math.signum(dist1-dist2);
            }
        });
        return stations.get(0);
    }

    private boolean isClustered() {
        return station2marker.isEmpty();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if( !isClustered() ) {
            Station station = marker2station.get(marker);
            if (station == null)
                return false;
            spinner.selectStation(station);
        }
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
    public void onSettingsChanged() {
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
        return lastStation == null ? "" : String.format("%s %s%s",
                getString(R.string.ShareMapPre),
                lastStation,
                getString(R.string.ShareMapPost)
        );
    }

    @Override
    public String getRelativeLink() {
        return String.format("%s/%s", PATH, model.getCurrentStation().getId());
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }

    private GoogleMap mMap;
    private String lastStation;
    private final Map<Marker,Station> marker2station = new HashMap<>();
    private final Map<Station,Marker> station2marker = new HashMap<>();
    private static final int STATION_LIMIT = 100;
    private static final int REGION_LIMIT = 1000;
}
