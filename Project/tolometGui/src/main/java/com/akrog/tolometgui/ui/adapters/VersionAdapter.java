package com.akrog.tolometgui.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.backend.VersionUpdate;

import java.util.List;

public class VersionAdapter extends RecyclerView.Adapter<VersionAdapter.ViewHolder> {
    private final List<VersionUpdate> updates;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView versionView;
        private final ViewGroup updates;

        public ViewHolder(View view) {
            super(view);
            versionView = view.findViewById(R.id.textVersion);
            updates = view.findViewById(R.id.updates);
        }

        public TextView getVersionView() {
            return versionView;
        }

        public ViewGroup getUpdates() {
            return updates;
        }
    }

    public VersionAdapter(List<VersionUpdate> updates) {
        this.updates = updates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_version, parent, false);
        return new VersionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VersionUpdate update = updates.get(position);
        holder.getVersionView().setText(update.getCodeName());
        holder.getUpdates().removeAllViews();
        for( String msg : update.getUpdates() ) {
            TextView textView = new TextView(holder.getUpdates().getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText("- " + msg);
            holder.getUpdates().addView(textView);
        }
    }

    @Override
    public int getItemCount() {
        return updates.size();
    }
}
