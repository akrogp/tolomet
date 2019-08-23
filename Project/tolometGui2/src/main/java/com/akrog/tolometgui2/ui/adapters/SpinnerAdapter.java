package com.akrog.tolometgui2.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.ui.services.ResourceService;
import com.akrog.tolometgui2.ui.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpinnerAdapter extends BaseAdapter implements android.widget.SpinnerAdapter {
    private final Map<MainViewModel.Command, String> mapCommands = new HashMap<>();
    private final List<MainViewModel.Command> listCommands = new ArrayList<>();
    private final Context context;
    private final List<Station> stations;
    private final LayoutInflater inflater;

    public SpinnerAdapter(@NonNull Context context, List<Station> stations, MainViewModel.Command command) {
        this.context = context;
        this.stations = stations == null ? new ArrayList<>(0) : stations;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mapCommands.put(MainViewModel.Command.SEL, "=== " + context.getString(R.string.select) + " ===");
        mapCommands.put(MainViewModel.Command.FAV, context.getString(R.string.menu_fav));
        mapCommands.put(MainViewModel.Command.NEAR, context.getString(R.string.menu_close));
        mapCommands.put(MainViewModel.Command.FIND, context.getString(R.string.menu_search));
        buildList(command);
    }

    private void buildList(MainViewModel.Command command) {
        mapCommands.put(MainViewModel.Command.SEP, command == null ? null : buildSeparator(command));
        listCommands.clear();
        for( MainViewModel.Command item : MainViewModel.Command.values() ) {
            if (command == null && item == MainViewModel.Command.SEP)
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
        MainViewModel.Command cmd = getCommand(i);
        return cmd == null ? getStation(i) : cmd;
    }

    public MainViewModel.Command getCommand(int i) {
        return i < listCommands.size() ? listCommands.get(i) : null;
    }

    public Station getStation(int i) {
        return i < listCommands.size() ? null : stations.get(i-listCommands.size());
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private String buildSeparator(MainViewModel.Command command) {
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
        return getCommand(position) != MainViewModel.Command.SEP;
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

        MainViewModel.Command cmd = getCommand(position);
        Station station = getStation(position);

        TextView textTitle = convertView.findViewById(R.id.station_title);
        textTitle.setText(getDropDownText(position));
        textTitle.setAlpha(cmd == MainViewModel.Command.SEP || cmd == MainViewModel.Command.SEL ? 0.6F : 1.0F);

        ImageView icon = convertView.findViewById(R.id.station_icon);
        Integer iconId = 0;
        if( cmd == MainViewModel.Command.FAV )
            iconId = R.drawable.ic_spinner_favorite;
        else if( cmd == MainViewModel.Command.NEAR )
            iconId = R.drawable.ic_spinner_gps;
        else if( cmd == MainViewModel.Command.FIND )
            iconId = R.drawable.ic_spinner_search;
        else if( station != null )
            iconId = ResourceService.getProviderIcon(station.getProviderType());
        if( iconId == null || iconId == 0)
            icon.setVisibility(View.GONE);
        else {
            icon.setImageResource(iconId);
            icon.setVisibility(View.VISIBLE);
        }

        TextView textType = convertView.findViewById(R.id.station_type);
        textType.setText(iconId == null ? station.getProviderType().getCode() : "");
        textType.setVisibility(iconId == null ? View.VISIBLE : View.GONE);

        return convertView;
    }

    private String getText(int position) {
        Station station = getStation(position);
        if( station == null )
            return context.getString(R.string.select);
        return station.toString();
    }

    private String getDropDownText(int position) {
        MainViewModel.Command cmd = getCommand(position);
        if( cmd != null )
            return mapCommands.get(cmd);
        Station station = getStation(position);
        if( station.getDistance() > 0.0F )
            return String.format("%s @ %.1f km", station.getName(), station.getDistance()/1000);
        return station.getName();
    }
}
