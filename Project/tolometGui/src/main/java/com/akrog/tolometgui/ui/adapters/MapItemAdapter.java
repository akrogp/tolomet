package com.akrog.tolometgui.ui.adapters;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderQuality;
import com.akrog.tolometgui.R;
import com.akrog.tolomet.Spot;
import com.akrog.tolomet.SpotType;
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
        if( tag instanceof Station )
            return fillStation((Station)tag);
        if( tag instanceof Spot)
            return fillSpot((Spot)tag);
        return null;
    }

    private View fillStation(Station station) {
        View view = fragment.getLayoutInflater().inflate(R.layout.map_station, null);

        ImageView icon = view.findViewById(R.id.icon);
        if( station.getProviderType().getQuality() == WindProviderQuality.Good )
            icon.setColorFilter(fragment.getResources().getColor(R.color.colorGood));
        else if( station.getProviderType().getQuality() == WindProviderQuality.Medium )
            icon.setColorFilter(fragment.getResources().getColor(R.color.colorMedium));
        else if( station.getProviderType().getQuality() == WindProviderQuality.Poor )
            icon.setColorFilter(fragment.getResources().getColor(R.color.colorPoor));

        TextView textView = view.findViewById(R.id.name);
        textView.setText(station.getName());

        textView = view.findViewById(R.id.provider_name);
        textView.setText(station.getProviderType().name());

        icon = view.findViewById(R.id.provider_icon);
        Integer iconId = ResourceService.getProviderIcon(station.getProviderType());
        if( iconId != null )
            icon.setImageResource(iconId);
        else
            icon.setVisibility(View.GONE);

        fillCoords(view, station.getLatitude(), station.getLongitude());

        return view;
    }

    private View fillSpot(Spot spot) {
        View view = fragment.getLayoutInflater().inflate(R.layout.map_spot, null);

        ImageView icon = view.findViewById(R.id.icon);
        if( spot.getType() == SpotType.TAKEOFF ) {
            icon.setImageResource(R.drawable.ic_flyspot);
            icon.setColorFilter(fragment.getResources().getColor(R.color.colorTakeoff));
        } else if( spot.getType() == SpotType.LANDING ) {
            icon.setImageResource(R.drawable.ic_flyspot);
            icon.setColorFilter(fragment.getResources().getColor(R.color.colorLanding));
        } else if( spot.getType() == SpotType.TREKKING )
            icon.setImageResource(R.drawable.ic_hiker);

        TextView textView = view.findViewById(R.id.name);
        textView.setText(spot.getName());

        textView = view.findViewById(R.id.desc);
        textView.setText(spot.getDesc() == null ? null : Html.fromHtml(spot.getDesc()));

        fillCoords(view, spot.getLatitude(), spot.getLongitude());

        return view;
    }

    private void fillCoords(View view, double latitude, double longitude) {
        TextView textView = view.findViewById(R.id.coords);
        textView.setText(String.format(Locale.US, "Coords.: %.5f, %.5f", latitude, longitude));
    }
}
