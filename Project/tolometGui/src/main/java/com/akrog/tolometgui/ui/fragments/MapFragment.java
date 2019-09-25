package com.akrog.tolometgui.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.db.DbTolomet;
import com.akrog.tolometgui.model.db.SpotEntity;
import com.akrog.tolometgui.ui.services.LocationService;
import com.akrog.tolometgui.ui.viewmodels.MapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.ViewModelProviders;

public class MapFragment extends ToolbarFragment implements OnMapReadyCallback, GoogleMap.OnCameraIdleListener, ClusterManager.OnClusterItemClickListener<ClusterItem> {
    private GoogleMap map;
    private ClusterManager<ClusterItem> cluster;
    private boolean resetZoom = true;
    private Marker currentMarker;
    private Bitmap windBitmap;
    private MapViewModel mapViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Canvas canvas = new Canvas();
        Drawable drawable = getResources().getDrawable(R.drawable.ic_wind);
        int pixels = (int)(48 * Resources.getSystem().getDisplayMetrics().density);
        windBitmap = Bitmap.createBitmap(pixels, pixels, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(windBitmap);
        drawable.setBounds(0, 0, pixels, pixels);
        drawable.draw(canvas);

        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);

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
        cluster = new ClusterManager<>(getActivity(), map);
        map.setOnMarkerClickListener(cluster);
        map.setOnCameraIdleListener(this);
        cluster.setOnClusterItemClickListener(this);
        cluster.setRenderer(new ItemRenderer(getActivity(), map, cluster));

        model.liveCurrentStation().observe(this, station -> {
            if( model.checkStation() )
                zoom(station);
        });

        if( !model.checkStation() ) {
            Location location = LocationService.getLocation(true);
            if( location != null )
                zoom(location.getLatitude(), location.getLongitude());
        }

        /*KmlLayer kml = new KmlLayer(map, R.raw.elliott, getActivity());
        kml.addLayerToMap();*/
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
        } else if( id == R.id.browser_item )
            openBrowser();

        return super.onOptionsItemSelected(item);
    }

    private void openBrowser() {
        double lat, lon;
        if( model.getCurrentStation() != null && !model.getCurrentStation().isSpecial() ) {
            lat = model.getCurrentStation().getLatitude();
            lon = model.getCurrentStation().getLongitude();
        } else {
            lat = map.getCameraPosition().target.latitude;
            lon = map.getCameraPosition().target.longitude;
        }
        String url = getUrl(lat,lon);
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public static String getUrl( double lat, double lon ) {
        return String.format(Locale.ENGLISH, "http://maps.google.com/maps?q=loc:%f,%f", lat, lon);
    }

    private void setMapType() {
        map.setMapType(settings.isSatellite() ? GoogleMap.MAP_TYPE_HYBRID : GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void getBitmap(Consumer<Bitmap> consumer) {
        map.snapshot(bitmap -> consumer.accept(bitmap));
    }

    @Override
    public boolean needsScreenshotStation() {
        return false;
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

    @Override
    public void onCameraIdle() {
        LatLngBounds bounds = map.getProjection().getVisibleRegion().latLngBounds;
        List<Station> stations = DbTolomet.getInstance().findGeoStations(
                bounds.northeast.latitude, bounds.northeast.longitude,
                bounds.southwest.latitude, bounds.southwest.longitude);
        List<SpotEntity> spots = DbTolomet.getInstance().findGeoSpots(
                bounds.northeast.latitude, bounds.northeast.longitude,
                bounds.southwest.latitude, bounds.southwest.longitude);
        cluster.clearItems();
        if( currentMarker != null ) {
            currentMarker.remove();
            currentMarker = null;
        }
        Station station = model.checkStation() ? model.getCurrentStation() : null;
        for( Station item : stations )
            if( item.equals(station) )
                currentMarker = map.addMarker(configureMarker(null, station));
        else
            cluster.addItem(new StationItem(item));
        for( SpotEntity spot : spots )
            if( mapViewModel.getSpot() != null && mapViewModel.getSpot().getId().equals(spot.getId()) )
                currentMarker = map.addMarker(configureMarker(null, spot));
            else
                cluster.addItem(new SpotItem(spot));
        if( currentMarker != null )
            currentMarker.showInfoWindow();
        cluster.cluster();
    }

    @Override
    public boolean onClusterItemClick(ClusterItem item) {
        if (item instanceof StationItem) {
            mapViewModel.setSpot(null);
            model.selectStation(((StationItem) item).getStation());
        } else {
            mapViewModel.setSpot(((SpotItem)item).getSpot());
            model.selectStation(null);
            zoom(mapViewModel.getSpot().getLatitude(), mapViewModel.getSpot().getLongitude());
        }
        return true;
    }

    private void zoom(Station station) {
        zoom(station.getLatitude(), station.getLongitude());
        resetZoom = false;
    }

    private void zoom(double lat, double lon) {
        LatLng cam = new LatLng(lat, lon);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cam, resetZoom ? 10 : map.getCameraPosition().zoom));
    }

    private MarkerOptions configureMarker(MarkerOptions options, Station station) {
        if( options == null )
            options = new MarkerOptions();
        float hue;
        float hueHi = BitmapDescriptorFactory.HUE_GREEN;
        float hueMi = BitmapDescriptorFactory.HUE_YELLOW;
        float hueLo = BitmapDescriptorFactory.HUE_RED;
        switch( station.getProviderType().getQuality() ) {
            case Good: hue = hueHi; break;
            case Medium: hue = hueMi; break;
            default: hue = hueLo; break;
        }
        return options
            .icon(BitmapDescriptorFactory.defaultMarker(hue))
            .position(new LatLng(station.getLatitude(), station.getLongitude()))
            .title(station.getName())
            .snippet(station.getProviderType().name());
    }

    private MarkerOptions configureMarker(MarkerOptions options, SpotEntity spot) {
        if( options == null )
            options = new MarkerOptions();
        return options
                .icon(BitmapDescriptorFactory.fromBitmap(windBitmap))
                .position(new LatLng(spot.getLatitude(), spot.getLongitude()))
                .title(spot.getName())
                .snippet(spot.getDesc());
    }

    private class ItemRenderer extends DefaultClusterRenderer<ClusterItem> {

        public ItemRenderer(Context context, GoogleMap map, ClusterManager<ClusterItem> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(ClusterItem item, MarkerOptions markerOptions) {
            if( item instanceof StationItem )
                configureMarker(markerOptions, ((StationItem)item).getStation());
            else
                configureMarker(markerOptions, ((SpotItem)item).getSpot());
            super.onBeforeClusterItemRendered(item, markerOptions);
        }

        @Override
        protected void onClusterItemRendered(ClusterItem clusterItem, Marker marker) {
            super.onClusterItemRendered(clusterItem, marker);
            if( !(clusterItem instanceof StationItem) )
                return;
            if( model.checkStation() && model.getCurrentStation().getId().equals(((StationItem)clusterItem).getStation().getId()) )
                marker.showInfoWindow();
        }
    }

    static class StationItem implements ClusterItem {
        private final Station station;

        public StationItem(Station station) {
            this.station = station;
        }

        @Override
        public LatLng getPosition() {
            return new LatLng(station.getLatitude(), station.getLongitude());
        }

        @Override
        public String getTitle() {
            return station.getName();
        }

        @Override
        public String getSnippet() {
            return station.getProviderType().name();
        }

        public Station getStation() {
            return station;
        }
    }

    static class SpotItem implements ClusterItem {
        private final SpotEntity spot;

        public SpotItem(SpotEntity spot) {
            this.spot = spot;
        }

        @Override
        public LatLng getPosition() {
            return new LatLng(spot.getLatitude(), spot.getLongitude());
        }

        @Override
        public String getTitle() {
            return spot.getName();
        }

        @Override
        public String getSnippet() {
            return spot.getDesc();
        }

        public SpotEntity getSpot() {
            return spot;
        }
    }
}
