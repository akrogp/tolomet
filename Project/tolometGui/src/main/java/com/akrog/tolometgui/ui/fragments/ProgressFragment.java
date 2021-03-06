package com.akrog.tolometgui.ui.fragments;

import android.os.Bundle;

import com.akrog.tolometgui.ui.activities.ProgressActivity;

import androidx.annotation.Nullable;

public abstract class ProgressFragment extends BaseFragment {
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

    public void onCancel() {
        activity.onCancel();
    }

    public void addCancelListenner( Runnable listenner ) {
        activity.addCancelListenner(listenner);
    }
}
