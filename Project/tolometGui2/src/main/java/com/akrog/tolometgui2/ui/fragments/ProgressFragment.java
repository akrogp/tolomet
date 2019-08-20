package com.akrog.tolometgui2.ui.fragments;

import android.os.Bundle;

import com.akrog.tolometgui2.ui.activities.ProgressActivity;

import androidx.annotation.Nullable;

public class ProgressFragment extends BaseFragment {
    private ProgressActivity activity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (ProgressActivity)getActivity();
    }

    public boolean beginProgress() {
        return activity.beginProgress();
    }

    public boolean endProgress() {
        return activity.endProgress();
    }
}
