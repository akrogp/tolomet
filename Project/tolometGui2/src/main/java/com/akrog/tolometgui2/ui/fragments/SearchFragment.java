package com.akrog.tolometgui2.ui.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.ui.adapters.SearchAdapter;
import com.akrog.tolometgui2.ui.viewmodels.MainViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class SearchFragment extends DialogFragment {
    private ListView listView;
    private MainViewModel model;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        model = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.search_dialog, null);
        listView = view.findViewById(R.id.search_list);

        SearchAdapter adapter = new SearchAdapter(getActivity(), R.layout.spinner_row, model.getSelStations());
        listView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle(R.string.menu_search)
            .setView(view)
            .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {});
        return builder.create();
    }
}
