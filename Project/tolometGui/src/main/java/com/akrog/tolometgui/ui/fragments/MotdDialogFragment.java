package com.akrog.tolometgui.ui.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
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
    private final int titleId;
    private final List<Motd> motds;
    private final DialogInterface.OnClickListener onClickListener;

    public MotdDialogFragment(int titleId, List<Motd> motds, DialogInterface.OnClickListener onClickListener) {
        this.titleId = titleId;
        this.motds = motds;
        this.onClickListener = onClickListener;
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
            .setTitle(titleId)
            .setIcon(android.R.drawable.ic_dialog_info)
            .setPositiveButton(R.string.ok, onClickListener);
        return builder.create();
    }
}
