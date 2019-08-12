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

import com.akrog.tolomet.Station;
import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui2.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {
    public enum Command {FAV, NEAR, FIND, SEP};
    private final Map<WindProviderType, Integer> mapProviders = new HashMap<>();

    private final Context context;
    private final List<Station> stations;
    private final LayoutInflater inflater;
    private final String separator;

    public SpinnerAdapter(@NonNull Context context, List<Station> stations) {
        this.context = context;
        this.stations = stations;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int max = 0;
        for( Station station : stations )
            max = Math.max(max, station.getName().length());
        StringBuilder sb = new StringBuilder(max);
        max += 5;
        while( max-- > 0 )
            sb.append('_');
        separator = sb.toString();
        mapProviders.put(WindProviderType.Aemet, R.drawable.aemet);
        mapProviders.put(WindProviderType.Euskalmet, R.drawable.euskalmet);
        mapProviders.put(WindProviderType.Ffvl, R.drawable.ffvl);
        mapProviders.put(WindProviderType.MeteoGalicia, R.drawable.galicia);
        mapProviders.put(WindProviderType.Holfuy, R.drawable.holfuy);
        mapProviders.put(WindProviderType.LaRioja, R.drawable.larioja);
        mapProviders.put(WindProviderType.Meteocat, R.drawable.meteocat);
        mapProviders.put(WindProviderType.MeteoClimatic, R.drawable.meteoclimatic);
        mapProviders.put(WindProviderType.MeteoFrance, R.drawable.meteofrance);
        mapProviders.put(WindProviderType.MeteoNavarra, R.drawable.navarra);
        mapProviders.put(WindProviderType.WeatherUnderground, R.drawable.wunder);
    }

    @Override
    public int getCount() {
        return Command.values().length + stations.size();
    }

    @Override
    public Object getItem(int i) {
        int off = Command.SEP.ordinal()+1;
        return i < off ? null : stations.get(i-off);
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
        Station station = (Station)getItem(position);
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
            iconId = mapProviders.containsKey(station.getProviderType()) ? mapProviders.get(station.getProviderType()) : R.drawable.ic_widget;
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
        Station station = (Station)getItem(position);
        if( station == null )
            return context.getString(R.string.select);
        return station.toString();
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
        Station station = (Station)getItem(position);
        if( station.getDistance() > 0.0F )
            return String.format("%s @ %.1f km", station.getName(), station.getDistance()/1000);
        return station.getName();
    }
}
