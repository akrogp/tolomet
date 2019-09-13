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

import com.akrog.tolomet.providers.WindProviderType;
import com.akrog.tolometgui.R;

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
        WindProviderType type = provider.getType();

        ImageView icon = itemView.findViewById(R.id.icon);
        boolean enabled = provider.getIconId() > 0;
        icon.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
        if( enabled )
            icon.setImageResource(provider.getIconId());

        TextView textView = itemView.findViewById(R.id.name);
        textView.setText(type.toString());

        textView = itemView.findViewById(R.id.date);
        textView.setText(provider.getDate());
        textView.setVisibility(provider.getDate() == null ? View.GONE : View.VISIBLE);

        textView = itemView.findViewById(R.id.count);
        textView.setVisibility(provider.getStations() < 0 ? View.GONE : View.VISIBLE);
        textView.setText(provider.getStations()+" "+context.getString(R.string.stations));

        CheckBox checkBox = itemView.findViewById(R.id.checkbox);
        checkBox.setChecked(provider.isChecked());
        checkBox.setVisibility(type.isDynamic() ? View.VISIBLE : View.GONE);
        checkBox.setOnClickListener(view -> {
            provider.setChecked(((CompoundButton)view).isChecked());
            listener.onItemClick(null, null, position, providers.length);
        });
        itemView.setOnClickListener(view -> checkBox.performClick());

        return itemView;
    }

    public static class ProviderWrapper {
        private final WindProviderType type;
        private int iconId;
        private int stations = -1;
        private String date;
        private boolean checked;

        public ProviderWrapper(WindProviderType type) {
            this.type = type;
        }

        public WindProviderType getType() {
            return type;
        }

        public void setIconId(int iconId) {
            this.iconId = iconId;
        }

        public int getIconId() {
            return iconId;
        }

        public void setStations(int stations) {
            this.stations = stations;
        }

        public int getStations() {
            return stations;
        }

        public void setDate(String date) {
            this.date = date;
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
    }
}
