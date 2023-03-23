package com.akrog.tolometgui.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.backend.Motd;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MotdAdapter extends RecyclerView.Adapter<MotdAdapter.ViewHolder> {
    private List<Motd> motds;
    private final SimpleDateFormat sdf = new SimpleDateFormat();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateView;
        private final TextView motdView;

        public ViewHolder(View view) {
            super(view);
            dateView = view.findViewById(R.id.textDate);
            motdView = view.findViewById(R.id.textMotd);
        }

        public TextView getDateView() {
            return dateView;
        }

        public TextView getMotdView() {
            return motdView;
        }
    }

    public MotdAdapter(List<Motd> motds) {
        this.motds = motds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_motd, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Motd motd = motds.get(position);
        holder.getDateView().setText(sdf.format(new Date(motd.getStamp())));
        holder.getMotdView().setText(motd.getMsg());
    }

    @Override
    public int getItemCount() {
        return motds.size();
    }
}
