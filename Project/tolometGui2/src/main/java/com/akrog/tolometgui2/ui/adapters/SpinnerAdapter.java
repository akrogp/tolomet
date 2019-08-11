package com.akrog.tolometgui2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.akrog.tolometgui2.R;

public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {
    private static final String[] SAMPLE = {"Punta Galea (Faro)", "Orduña", "Orozko"};
    private enum Command {FAV, NEAR, FIND, SEP};
    private final Context context;
    private final LayoutInflater inflater;
    private final String separator;

    public SpinnerAdapter(@NonNull Context context) {
        this.context = context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int max = 0;
        for( String item : SAMPLE )
            max = Math.max(max, item.length());
        StringBuilder sb = new StringBuilder(max);
        max += 5;
        while( max-- > 0 )
            sb.append('_');
        separator = sb.toString();
    }

    @Override
    public int getCount() {
        return Command.values().length + SAMPLE.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /*@Override
    public boolean isEnabled(int position) {
        return position <= Command.SEP.ordinal() ? false : true;
    }*/

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if( convertView == null )
            convertView = inflater.inflate(R.layout.spinner_selected, parent, false);
        ((TextView)convertView.findViewById(R.id.station_title)).setText(getText(position));
        return convertView;
    }

    @Override
    public View getDropDownView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if( convertView == null )
            convertView = inflater.inflate(R.layout.spinner_row, parent, false);
        ((TextView)convertView.findViewById(R.id.station_title)).setText(getDropDownText(position));
        ImageView icon = convertView.findViewById(R.id.station_icon);
        int iconId;
        int padding = (int)context.getResources().getDimension(R.dimen.spinner_sep);
        int paddingTop = padding, paddingBottom = padding;
        if( position == Command.FAV.ordinal() )
            iconId = R.drawable.ic_widget;
        else if( position == Command.NEAR.ordinal() )
            iconId = R.drawable.ic_widget;
        else if( position == Command.FIND.ordinal() ) {
            iconId = R.drawable.ic_widget;
            paddingBottom = 0;
        }
        else if( position == Command.SEP.ordinal() ) {
            iconId = 0;
            paddingTop = paddingBottom = 0;
        }
        else
            iconId = R.drawable.euskalmet;
        if( iconId == 0)
            icon.setVisibility(View.GONE);
        else {
            icon.setImageDrawable(ContextCompat.getDrawable(context, iconId));
            icon.setVisibility(View.VISIBLE);
        }
        convertView.findViewById(R.id.spinner_layout).setPadding(0,paddingTop,0,paddingBottom);
        return convertView;
    }

    private String getText(int position) {
        if( position <= Command.SEP.ordinal() )
            return context.getString(R.string.select);
        return SAMPLE[position-Command.values().length];
    }

    private String getDropDownText(int position) {
        if( position == Command.FAV.ordinal() )
            return context.getString(R.string.menu_fav);
        if( position == Command.NEAR.ordinal() )
            return context.getString(R.string.menu_close);
        if( position == Command.FIND.ordinal() )
            return "Buscar por nombre";
        if( position == Command.SEP.ordinal() )
            return separator;
        return SAMPLE[position-Command.values().length];
    }
}
