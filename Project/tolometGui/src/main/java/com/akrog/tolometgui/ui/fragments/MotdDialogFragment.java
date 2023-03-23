package com.akrog.tolometgui.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.backend.Motd;
import com.akrog.tolometgui.ui.adapters.MotdAdapter;

import java.util.List;

public class MotdDialogFragment extends DialogFragment {
    private final List<Motd> motds;

    public MotdDialogFragment(List<Motd> motds) {
        this.motds = motds;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        RecyclerView view = new RecyclerView(getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        view.setAdapter(new MotdAdapter(motds));
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setView(view)
            .setTitle(R.string.motd)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setPositiveButton(R.string.ok, null);
        return builder.create();
    }
}
