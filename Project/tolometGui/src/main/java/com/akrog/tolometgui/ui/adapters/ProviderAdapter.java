package com.akrog.tolometgui.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.db.DbTolomet;
import com.akrog.tolometgui.model.db.SpotEntity;
import com.akrog.tolometgui.ui.services.ResourceService;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProviderAdapter extends ArrayAdapter<ProviderAdapter.ProviderWrapper> {
    private final Context context;
    private final ProviderWrapper[] providers;
    private final AdapterView.OnItemClickListener listener;

    public ProviderAdapter(Context context, ProviderWrapper[] providers, AdapterView.OnItemClickListener listener) {
        super(context, -1, providers);
        this.context = context;
        this.providers = providers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View itemView, @NonNull ViewGroup parent) {
        if( itemView == null ) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_provider, parent, false);
        }
        ProviderWrapper provider = providers[position];

        ImageView icon = itemView.findViewById(R.id.icon);
        boolean enabled = provider.getIconId() > 0;
        icon.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
        if( enabled )
            icon.setImageResource(provider.getIconId());

        TextView textView = itemView.findViewById(R.id.name);
        textView.setText(provider.getName());

        textView = itemView.findViewById(R.id.date);
        textView.setText(provider.getDate());
        textView.setVisibility(provider.getDate() == null ? View.GONE : View.VISIBLE);

        textView = itemView.findViewById(R.id.count);
        textView.setVisibility(provider.getCount() < 0 ? View.GONE : View.VISIBLE);
        textView.setText(provider.getCount()+" "+context.getString(R.string.stations));

        CheckBox checkBox = itemView.findViewById(R.id.checkbox);
        checkBox.setChecked(provider.isChecked());
        checkBox.setVisibility(provider.isDynamic() ? View.VISIBLE : View.GONE);
        checkBox.setOnClickListener(view -> {
            if( !provider.isDynamic() )
                return;
            provider.setChecked(((CompoundButton)view).isChecked());
            listener.onItemClick(null, null, position, providers.length);
        });
        itemView.setOnClickListener(view -> checkBox.performClick());

        return itemView;
    }

    public static class ProviderWrapper implements Comparable<ProviderWrapper> {
        private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        private final DbTolomet.ProviderInfo info;
        private final int iconId;
        private final String date;
        private boolean checked;

        public ProviderWrapper(DbTolomet.ProviderInfo info) {
            this.info = info;
            if( info.getWindProviderType() != null ) {
                Integer tmp = ResourceService.getProviderIcon(info.getWindProviderType());
                iconId = tmp == null ? 0 : tmp;
            } else if (info.getSpotProviderType() != null )
                iconId = R.drawable.ic_wind;
            else
                iconId = 0;
            this.date = info.getDate() == null ? null : DATE_FORMAT.format(info.getDate());
        }

        public String getName() {
            return info.getProvider();
        }

        public int getIconId() {
            return iconId;
        }

        public int getCount() {
            return info.getCount();
        }

        public String getDate() {
            return date;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public boolean isChecked() {
            return checked;
        }

        public boolean isDynamic() {
            if( info.getWindProviderType() != null )
                return info.getWindProviderType().isDynamic();
            return info.getSpotProviderType() != null;
        }

        @Override
        public int compareTo(ProviderWrapper p2) {
            ProviderWrapper p1 = this;
            if( p1.isDynamic() && !p2.isDynamic() )
                return -1;
            if( p2.isDynamic() && !p1.isDynamic() )
                return 1;
            if( p1.getCount() != p2.getCount() )
                return p2.getCount() - p1.getCount();
            /*if( p1.getIconId() > 0 && p2.getIconId() <= 0 )
                return -1;
            if( p2.getIconId() > 0 && p1.getIconId() <= 0 )
                return 1;*/
            if( p1.info.getWindProviderType() != null && p2.info.getWindProviderType() != null
                    && p1.info.getWindProviderType().getQuality() != p2.info.getWindProviderType().getQuality() )
                return p1.info.getWindProviderType().getQuality().ordinal() - p2.info.getWindProviderType().getQuality().ordinal();
            return p1.getName().compareTo(p2.getName());
        }

        public void download() {
            if( info.getWindProviderType() != null ) {
                List<Station> stations = info.getWindProviderType().getProvider().downloadStations();
                if( stations != null )
                    DbTolomet.getInstance().updateStations(info.getWindProviderType(), stations);
            } else if( info.getSpotProviderType() != null ) {
                List<SpotEntity> spots = info.getSpotProviderType().getProvider().downloadSpots();
                if( spots != null )
                    DbTolomet.getInstance().updateSpots(info.getSpotProviderType(), spots);
            }
        }
    }
}
