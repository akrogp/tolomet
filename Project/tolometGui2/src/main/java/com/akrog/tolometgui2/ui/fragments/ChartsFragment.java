package com.akrog.tolometgui2.ui.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.model.AppSettings;
import com.akrog.tolometgui2.ui.viewmodels.ChartsViewModel;
import com.akrog.tolometgui2.ui.viewmodels.MainViewModel;

public class ChartsFragment extends Fragment {
    private AppSettings settings;
    private MainViewModel model;
    private ChartsViewModel chartsModel;

    private final Handler handler = new Handler();
    private Runnable timer;

    public static ChartsFragment newInstance() {
        return new ChartsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.charts_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settings = AppSettings.getInstance();
        model = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        chartsModel = ViewModelProviders.of(this).get(ChartsViewModel.class);
        createTimer();
    }

    private void createTimer() {
        cancelTimer();
        if( settings.getUpdateMode() != AppSettings.AUTO_UPDATES )
            return;
        timer = new Runnable() {
            @Override
            public void run() {
                if( model.checkStation() )
                    downloadData();
            }
        };
        //timer.run();
    }

    private void downloadData() {
    }

    private void cancelTimer() {
        if( timer != null ) {
            handler.removeCallbacks(timer);
            timer = null;
        }
    }

    private boolean postTimer() {
        if( timer == null || settings.getUpdateMode() != AppSettings.AUTO_UPDATES )
            return false;
        handler.removeCallbacks(timer);
        int minutes = 1;
        if( model.checkStation() && !model.getCurrentStation().isEmpty() ) {
            int dif = (int)((System.currentTimeMillis()-model.getCurrentStation().getStamp())/60/1000L);
            minutes = dif >= model.getRefresh() ? 1 : model.getRefresh()-dif;
        }
        handler.postDelayed(timer, minutes*60*1000);
        return true;
    }
}
