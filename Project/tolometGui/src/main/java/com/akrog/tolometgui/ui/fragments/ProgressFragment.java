package com.akrog.tolometgui.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.akrog.tolometgui.ui.activities.ProgressActivity;

public abstract class ProgressFragment extends BaseFragment {
    private ProgressActivity activity;

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
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
