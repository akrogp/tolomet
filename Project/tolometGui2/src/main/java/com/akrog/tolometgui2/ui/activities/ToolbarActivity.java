package com.akrog.tolometgui2.ui.activities;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.model.Model;
import com.akrog.tolometgui2.ui.adapters.SpinnerAdapter;

public abstract class ToolbarActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {
    private Model model;
    private Spinner spinner;
    private SpinnerAdapter spinnerAdapter;
    private boolean autoSelected;

    protected Toolbar configureToolbar() {
        autoSelected = true;
        model = ViewModelProviders.of(this).get(Model.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        spinner = toolbar.findViewById(R.id.spinner);
        spinnerAdapter = new SpinnerAdapter(this, model.getSelStations());
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
        return toolbar;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Station station = (Station)adapterView.getSelectedItem();
        model.setCurrentStation(station);
        if( i == SpinnerAdapter.Command.FAV.ordinal() )
            model.selectFavorites();
        else if( i == SpinnerAdapter.Command.NEAR.ordinal() )
            selectNearest(() -> {}, () -> {
                spinnerAdapter.notifyDataSetChanged();
                spinner.performClick();
            });
        if( station == null ) {
            spinnerAdapter.notifyDataSetChanged();
            if( autoSelected )
                autoSelected = false;
            else
                spinner.performClick();
        }
    }

    private void selectNearest(Runnable onNothing, Runnable onFound) {
        final Context activity = this;
        askLocation(ll -> {
            if( ll == null ) {
                Toast.makeText(activity, R.string.error_gps, Toast.LENGTH_SHORT).show();
                onNothing.run();
            } else {
                model.selectNearest(ll.getLatitude(), ll.getLongitude());
                if (model.getSelStations().isEmpty()) {
                    Toast.makeText(activity, R.string.warn_near, Toast.LENGTH_SHORT).show();
                    onNothing.run();
                } else
                    onFound.run();
            }
        }, () -> {
            Toast.makeText(activity, R.string.warn_near,Toast.LENGTH_SHORT).show();
            onNothing.run();
        }, true);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
