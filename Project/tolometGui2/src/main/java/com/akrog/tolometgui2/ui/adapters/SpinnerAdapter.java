package com.akrog.tolometgui2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akrog.tolometgui2.R;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private static final String[] SAMPLE = {"Punta Galea", "Orduña", "Orozko"};
    private final LayoutInflater inflater;

    public SpinnerAdapter(@NonNull Context context) {
        super(context, R.layout.spinner_row, SAMPLE);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if( convertView == null )
            convertView = inflater.inflate(R.layout.spinner_row, parent, false);
        ((TextView)convertView.findViewById(R.id.station_title)).setText(SAMPLE[position]);
        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if( convertView == null )
            convertView = inflater.inflate(R.layout.spinner_selected, parent, false);
        ((TextView)convertView.findViewById(R.id.station_title)).setText(SAMPLE[position]);
        return convertView;
    }
}
