package com.akrog.tolometgui.ui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akrog.tolomet.SpotType;
import com.akrog.tolomet.Station;
import com.akrog.tolometgui.R;
import com.akrog.tolomet.Spot;
import com.akrog.tolometgui.model.db.DbTolomet;
import com.akrog.tolometgui.ui.activities.BaseActivity;
import com.akrog.tolometgui.ui.activities.MainActivity;
import com.akrog.tolometgui.ui.adapters.MapItemAdapter;
import com.akrog.tolometgui.ui.services.LocationService;
import com.akrog.tolometgui.ui.services.ResourceService;
import com.akrog.tolometgui.ui.services.WeakTask;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.Nullable;
import androidx.core.util.Consumer;
import androidx.lifecycle.ViewModelProviders;

public class MapFragment extends ToolbarFragment implements
        OnMapReadyCallback, GoogleMap.OnCameraIdleListener, GoogleMap.OnInfoWindowClickListener,
        ClusterManager.OnClusterItemClickListener<ClusterItem> {
    private GoogleMap map;
    private ClusterManager<ClusterItem> cluster;
    private boolean resetZoom = true;
    private Marker currentMarker;
    private MapViewModel mapViewModel;
    private final Pattern URL_PATTERN = Pattern.compile("http.?://\\S*");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapViewModel = ViewModelProviders.of(getActivity()).get(MapViewModel.class);

        MapView mapView = getView().findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if( !isAdded() )
            return;
        this.map = map;
        setMapType();
        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, R.string.gps_rationale,
            () -> enableLocation(), null);
        configureReportButtons();
        map.setInfoWindowAdapter(new MapItemAdapter(this));
        cluster = new ClusterManager<>(getActivity(), map);
        map.setOnMarkerClickListener(cluster);
        map.setOnCameraIdleListener(this);
        cluster.setOnClusterItemClickListener(this);
        cluster.setRenderer(new ItemRenderer(getActivity(), map, cluster));
        map.setOnInfoWindowClickListener(this);

        model.liveCurrentStation().observe(getViewLifecycleOwner(), station -> {
            if( model.checkStation() ) {
                mapViewModel.setSpot(null);
                zoom(station);
            }
        });

        mapViewModel.liveSpot().observe(getViewLifecycleOwner(), spot -> {
            zoom(spot);
        });

        if( !model.checkStation() && mapViewModel.getSpot() == null ) {
            Location location = LocationService.getLocation(true);
            if( location != null )
                zoom(location.getLatitude(), location.getLongitude());
        }

        /*KmlLayer kml = new KmlLayer(map, R.raw.elliott, getActivity());
        kml.addLayerToMap();*/
    }

    private void configureReportButtons() {
        BaseActivity activity = (BaseActivity)getActivity();
        getView().findViewById(R.id.button_add_hiker).setOnClickListener(view ->
                activity.sendMail("davidherranzelliott@gmail.com", getString(R.string.AddHikeSpotSubject), getString(R.string.ElliottGreetings))
        );
        getView().findViewById(R.id.button_add_spot).setOnClickListener(view ->
            activity.sendMail("davidherranzelliott@gmail.com", getString(R.string.AddFlySpotSubject), getString(R.string.ElliottGreetings))
        );
        getView().findViewById(R.id.button_add_station).setOnClickListener(view ->
            activity.sendMail("akrog.apps@gmail.com", getString(R.string.AddStationSubject), getString(R.string.ReportGreetings))
        );
    }

    @SuppressLint("MissingPermission")
    private void enableLocation() {
        map.setMyLocationEnabled(true);
        View newButton = getView().findViewById(R.id.button_location);
        View oldButton = ((View)getView().findViewById(R.id.map).findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        if( oldButton != null ) {
            oldButton.setVisibility(View.GONE);
            newButton.setVisibility(View.VISIBLE);
            newButton.setOnClickListener(view -> oldButton.performClick());
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.findItem(R.id.satellite_item).setChecked(settings.isSatellite());
        menu.findItem(R.id.flyspots_item).setChecked(settings.isFlySpots());
        menu.findItem(R.id.hikespots_item).setChecked(settings.isHikeSpots());
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
        } else if( id == R.id.flyspots_item ) {
            item.setChecked(!item.isChecked());
            settings.setFlySpots(item.isChecked());
            getView().findViewById(R.id.button_add_spot).setVisibility(item.isChecked()?View.VISIBLE:View.GONE);
            onCameraIdle();
        } else if( id == R.id.hikespots_item ) {
            item.setChecked(!item.isChecked());
            settings.setHikeSpots(item.isChecked());
            getView().findViewById(R.id.button_add_hiker).setVisibility(item.isChecked()?View.VISIBLE:View.GONE);
            onCameraIdle();
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
        new LoadTask(this).execute(bounds);
    }

    @Override
    public boolean onClusterItemClick(ClusterItem item) {
        if (item instanceof StationItem) {
            mapViewModel.setSpot(null);
            model.selectStation(((StationItem) item).getStation());
        } else {
            mapViewModel.setSpot(((SpotItem)item).getSpot());
            model.selectStation(null);
        }
        return true;
    }

    private void zoom(Station station) {
        if( station == null )
            return;
        zoom(station.getLatitude(), station.getLongitude());
        resetZoom = false;
    }

    private void zoom(Spot spot) {
        if( spot == null )
            return;
        zoom(spot.getLatitude(), spot.getLongitude());
        resetZoom = false;
    }

    private void zoom(double lat, double lon) {
        LatLng cam = new LatLng(lat, lon);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(cam, resetZoom ? 10 : map.getCameraPosition().zoom));
    }

    private MarkerOptions configureMarker(MarkerOptions options, Station station) {
        if( options == null )
            options = new MarkerOptions();
        return options
            .icon(BitmapDescriptorFactory.fromBitmap(ResourceService.getMarketBitmap(station)))
            .position(new LatLng(station.getLatitude(), station.getLongitude()))
            .title(station.getName())
            .snippet(station.getProviderType().name());
    }

    private MarkerOptions configureMarker(MarkerOptions options, Spot spot) {
        if( options == null )
            options = new MarkerOptions();
        return options
                .icon(BitmapDescriptorFactory.fromBitmap(ResourceService.getMarkerBitmap(spot)))
                .position(new LatLng(spot.getLatitude(), spot.getLongitude()))
                .title(spot.getName())
                .snippet(spot.getDesc());
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        if( marker.getTag() == null )
            return;
        if( marker.getTag() instanceof Station )
            ((MainActivity)getActivity()).navigate(R.id.nav_charts);
        else if( marker.getTag() instanceof Spot)
            navigateSpot((Spot)marker.getTag());
    }

    protected void navigateSpot(Spot spot) {
        if( spot == null )
            return;
        Intent intent;
        Matcher matcher;
        if( spot.getDesc() != null && (matcher=URL_PATTERN.matcher(spot.getDesc())).find() ) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(matcher.group()));
            startActivity(intent);
            return;
        }
        if( spot.getName() != null && !spot.getName().isEmpty() )
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Locale.ENGLISH,
                "geo:0,0?q=%f,%f(%s)", spot.getLatitude(), spot.getLongitude(), spot.getName()
            )));
        else
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Locale.ENGLISH,
                "geo:%f,%f", spot.getLatitude(), spot.getLongitude()
            )));
        if( intent.resolveActivity(activity.getPackageManager()) != null ) {
            Intent chooser = Intent.createChooser(intent, getString(R.string.select_app));
            startActivity(chooser);
        } else
            Toast.makeText(activity, R.string.install_nav_app, Toast.LENGTH_LONG).show();
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
            if( clusterItem instanceof SpotItem ) {
                marker.setTag(((SpotItem)clusterItem).spot);
                return;
            }
            if( clusterItem instanceof StationItem ) {
                marker.setTag(((StationItem) clusterItem).station);
                if (model.checkStation() && model.getCurrentStation().getId().equals(((StationItem) clusterItem).getStation().getId()))
                    marker.showInfoWindow();
            }
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
        private final Spot spot;

        public SpotItem(Spot spot) {
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

        public Spot getSpot() {
            return spot;
        }
    }

    private static class ItemList {
        List<Station> stations;
        List<Spot> spots;
    }

    private static class LoadTask extends WeakTask<MapFragment,LatLngBounds,Void,ItemList> {
        LoadTask(MapFragment fragment) {
            super(fragment);
        }

        @Override
        protected ItemList doInBackground(MapFragment fragment, LatLngBounds... boundss) {
            ItemList items = new ItemList();
            LatLngBounds bounds = boundss[0];
            items.stations = DbTolomet.getInstance().stationDao().findGeoStations(
                    bounds.northeast.latitude, bounds.northeast.longitude,
                    bounds.southwest.latitude, bounds.southwest.longitude);
            items.spots = new ArrayList<>();
            if( fragment.settings.isFlySpots() )
                items.spots.addAll(DbTolomet.getInstance().spotDao().findGeoSpots(
                    bounds.northeast.latitude, bounds.northeast.longitude,
                    bounds.southwest.latitude, bounds.southwest.longitude, SpotType.LANDING, SpotType.TAKEOFF));
            if( fragment.settings.isHikeSpots() )
                items.spots.addAll(DbTolomet.getInstance().spotDao().findGeoSpots(
                        bounds.northeast.latitude, bounds.northeast.longitude,
                        bounds.southwest.latitude, bounds.southwest.longitude, SpotType.TREKKING));
            return items;
        }

        @Override
        protected void onPostExecute(MapFragment frag, ItemList items) {
            frag.cluster.clearItems();
            if( frag.currentMarker != null ) {
                frag.currentMarker.remove();
                frag.currentMarker = null;
            }
            Station station = frag.model.checkStation() ? frag.model.getCurrentStation() : null;
            for( Station item : items.stations )
                if( item.equals(station) ) {
                    frag.currentMarker = frag.map.addMarker(frag.configureMarker(null, station));
                    frag.currentMarker.setTag(station);
                }
                else
                    frag.cluster.addItem(new StationItem(item));
            for( Spot spot : items.spots )
                if( frag.mapViewModel.getSpot() != null && frag.mapViewModel.getSpot().getId().equals(spot.getId()) ) {
                    frag.currentMarker = frag.map.addMarker(frag.configureMarker(null, spot));
                    frag.currentMarker.setTag(spot);
                } else
                    frag.cluster.addItem(new SpotItem(spot));
            if( frag.currentMarker != null )
                frag.currentMarker.showInfoWindow();
            frag.cluster.cluster();
        }
    }
}
