package com.akrog.tolometgui2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.ui.services.ResourceService;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class SearchAdapter extends ArrayAdapter<Station> {
    public SearchAdapter(@NonNull Context context, int resource, @NonNull List<Station> stations) {
        super(context, resource, stations);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Station station = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_row, parent, false);

        TextView textTitle = convertView.findViewById(R.id.station_title);
        textTitle.setText(station.getName());

        ImageView icon = convertView.findViewById(R.id.station_icon);
        Integer iconId = ResourceService.getProviderIcon(station.getProviderType());
        if( iconId != null ) {
            icon.setImageDrawable(ContextCompat.getDrawable(getContext(), iconId));
            icon.setVisibility(View.VISIBLE);
        } else
            icon.setVisibility(View.GONE);

        TextView textType = convertView.findViewById(R.id.station_type);
        textType.setText(iconId == null ? station.getProviderType().getCode() : "");
        textType.setVisibility(iconId == null ? View.VISIBLE : View.GONE);

        return convertView;
    }
}
