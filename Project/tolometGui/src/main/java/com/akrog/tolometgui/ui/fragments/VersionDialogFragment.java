package com.akrog.tolometgui.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akrog.tolometgui.BuildConfig;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.backend.VersionUpdate;
import com.akrog.tolometgui.ui.adapters.VersionAdapter;

import java.util.List;

public class VersionDialogFragment extends DialogFragment {
    private final List<VersionUpdate> updates;

    public VersionDialogFragment(List<VersionUpdate> updates) {
        this.updates = updates;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        RecyclerView view = new RecyclerView(getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));
        view.setAdapter(new VersionAdapter(updates));
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setView(view)
            .setTitle(R.string.newversion)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setPositiveButton(R.string.update, (dialog, which) ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID))))
            .setNegativeButton(R.string.tomorrow, null);
        return builder.create();
    }
}
