package com.akrog.tolometgui.ui.adapters;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.db.SpotEntity;
import com.akrog.tolometgui.ui.services.ResourceService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Locale;

import androidx.fragment.app.Fragment;

public class MapItemAdapter implements GoogleMap.InfoWindowAdapter {
    private final Fragment fragment;

    public MapItemAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        Object tag = marker.getTag();
        if( tag == null )
            return null;
        View view = fragment.getLayoutInflater().inflate(R.layout.item_map, null);
        if( tag instanceof Station )
            fillStation(view, (Station)tag);
        else if( tag instanceof SpotEntity )
            fillSpot(view, (SpotEntity)tag);
        else
            return null;
        return view;
    }

    private void fillStation(View view, Station station) {
        ImageView icon = view.findViewById(R.id.icon);
        Integer iconId = ResourceService.getProviderIcon(station.getProviderType());
        icon.setVisibility(iconId != null ? View.VISIBLE : View.INVISIBLE);
        if( iconId != null )
            icon.setImageResource(iconId);

        TextView textView = view.findViewById(iconId == null ? R.id.name_no_icon : R.id.name);
        textView.setText(station.getName());
        textView = view.findViewById(iconId == null ? R.id.name : R.id.name_no_icon);
        textView.setVisibility(View.GONE);

        textView = view.findViewById(R.id.desc);
        textView.setText(station.getProviderType().name());

        fillCoords(view, station.getLatitude(), station.getLongitude());
    }

    private void fillSpot(View view, SpotEntity spot) {
        ImageView icon = view.findViewById(R.id.icon);
        icon.setImageResource(R.drawable.ic_flyspots);

        TextView textView = view.findViewById(R.id.name);
        textView.setText(spot.getName());
        textView = view.findViewById(R.id.name_no_icon);
        textView.setVisibility(View.GONE);

        textView = view.findViewById(R.id.desc);
        textView.setText(spot.getDesc() == null ? null : Html.fromHtml(spot.getDesc()));

        fillCoords(view, spot.getLatitude(), spot.getLongitude());
    }

    private void fillCoords(View view, double latitude, double longitude) {
        TextView textView = view.findViewById(R.id.coords);
        textView.setText(String.format(Locale.US, "Coords.: %.5f, %.5f", latitude, longitude));
    }
}
