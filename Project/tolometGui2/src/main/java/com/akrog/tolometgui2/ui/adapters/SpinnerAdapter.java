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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {
    public enum Command {FAV, NEAR, FIND, SEP};
    private final Map<WindProviderType, Integer> mapProviders = new HashMap<>();
    private final Map<Command, String> mapCommands = new HashMap<>();
    private final List<Command> listCommands = new ArrayList<>();
    private final Context context;
    private final List<Station> stations;
    private final LayoutInflater inflater;

    public SpinnerAdapter(@NonNull Context context, List<Station> stations, Command command) {
        this.context = context;
        this.stations = stations;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mapCommands.put(Command.FAV, context.getString(R.string.menu_fav));
        mapCommands.put(Command.NEAR, context.getString(R.string.menu_close));
        mapCommands.put(Command.FIND, "Buscar por nombre");
        buildList(command);

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

    private void buildList(Command command) {
        mapCommands.put(Command.SEP, command == null ? null : buildSeparator(command));
        listCommands.clear();
        for( Command item : Command.values() ) {
            if (command == null && item == Command.SEP)
                continue;
            if( item != command )
                listCommands.add(item);
        }
    }

    @Override
    public int getCount() {
        return listCommands.size() + stations.size();
    }

    @Override
    public Object getItem(int i) {
        Command cmd = getCommand(i);
        return cmd == null ? getStation(i) : cmd;
    }

    public Command getCommand(int i) {
        return i < listCommands.size() ? listCommands.get(i) : null;
    }

    public Station getStation(int i) {
        return i < listCommands.size() ? null : stations.get(i-listCommands.size());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void notifyDataSetChanged(Command command) {
        buildList(command);
        super.notifyDataSetChanged();
    }

    private String buildSeparator(Command command) {
        if( command == null )
            return null;
        return String.format("=== %s ===", mapCommands.get(command));
    }

    public int getPosition(Station station) {
        if( station == null )
            return 0;
        int off = 0;
        for( Station item : stations ) {
            if( item.getId().equals(station.getId()) )
                break;
            off++;
        }
        if( off >= stations.size() )
            return 0;
        return listCommands.size() + off;
    }

    @Override
    public boolean isEnabled(int position) {
        return getCommand(position) != Command.SEP;
    }

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

        Command cmd = getCommand(position);
        Station station = getStation(position);

        TextView textTitle = convertView.findViewById(R.id.station_title);
        textTitle.setText(getDropDownText(position));
        textTitle.setAlpha(cmd == Command.SEP ? 0.6F : 1.0F);

        ImageView icon = convertView.findViewById(R.id.station_icon);
        int iconId;
        if( cmd == Command.FAV )
            iconId = R.drawable.ic_spinner_favorite;
        else if( cmd == Command.NEAR )
            iconId = R.drawable.ic_spinner_gps;
        else if( cmd == Command.FIND )
            iconId = R.drawable.ic_spinner_search;
        else if( cmd == Command.SEP )
            iconId = 0;
        else
            iconId = mapProviders.containsKey(station.getProviderType()) ? mapProviders.get(station.getProviderType()) : -1;
        if( iconId <= 0)
            icon.setVisibility(View.GONE);
        else {
            icon.setImageDrawable(ContextCompat.getDrawable(context, iconId));
            icon.setVisibility(View.VISIBLE);
        }

        TextView textType = convertView.findViewById(R.id.station_type);
        textType.setText(iconId == -1 ? station.getProviderType().getCode() : "");
        textType.setVisibility(iconId == -1 ? View.VISIBLE : View.GONE);

        return convertView;
    }

    private String getText(int position) {
        Station station = getStation(position);
        if( station == null )
            return context.getString(R.string.select);
        return station.toString();
    }

    private String getDropDownText(int position) {
        Command cmd = getCommand(position);
        if( cmd != null )
            return mapCommands.get(cmd);
        Station station = getStation(position);
        if( station.getDistance() > 0.0F )
            return String.format("%s @ %.1f km", station.getName(), station.getDistance()/1000);
        return station.getName();
    }
}
