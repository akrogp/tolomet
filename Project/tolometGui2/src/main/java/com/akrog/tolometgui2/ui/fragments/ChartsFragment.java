package com.akrog.tolometgui2.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.ui.viewmodels.ChartsViewModel;

public class ChartsFragment extends Fragment {

    private ChartsViewModel chartsModel;

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
        chartsModel = ViewModelProviders.of(this).get(ChartsViewModel.class);
        // TODO: Use the ViewModel
    }

}
