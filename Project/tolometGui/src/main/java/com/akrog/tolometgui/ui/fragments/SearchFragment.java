package com.akrog.tolometgui.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.db.DbTolomet;
import com.akrog.tolometgui.ui.adapters.SearchAdapter;
import com.akrog.tolometgui.ui.services.WeakTask;
import com.akrog.tolometgui.ui.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

public class SearchFragment extends DialogFragment {
    private MainViewModel model;
    private ListView listView;

    private void showKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        model = ViewModelProviders.of(getActivity()).get(MainViewModel.class);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.search_dialog, null);

        listView = view.findViewById(R.id.search_list);
        listView.setOnItemClickListener((parent, view1, pos, l) -> {
            hideKeyboard();
            Station station = (Station)parent.getItemAtPosition(pos);
            model.selectStation(station);
            dismiss();
        });

        EditText editText = view.findViewById(R.id.search_text);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if( charSequence.length() < 3 ) {
                    SearchAdapter adapter = new SearchAdapter(getActivity(), R.layout.spinner_row, new ArrayList<>());
                    listView.setAdapter(adapter);
                } else
                    new DbTask(SearchFragment.this).execute(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        editText.requestFocus();
        showKeyboard();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
            .setTitle(R.string.menu_search)
            .setView(view)
            .setNegativeButton(R.string.cancel, (dialogInterface, i) -> hideKeyboard());
        return builder.create();
    }

    private static class DbTask extends WeakTask<SearchFragment, String, Void, List<Station>> {
        DbTask(SearchFragment context) {
            super(context);
        }

        @Override
        protected List<Station> doInBackground(SearchFragment fragment, String... params) {
            return DbTolomet.getInstance().stationDao().searchStations(params[0]);
        }

        @Override
        protected void onPostExecute(SearchFragment fragment, List<Station> stations) {
            SearchAdapter adapter = new SearchAdapter(fragment.getActivity(), R.layout.spinner_row, stations);
            fragment.listView.setAdapter(adapter);
        }
    }
}
