package com.akrog.tolometgui2.ui.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.ui.activities.ProgressActivity;
import com.akrog.tolometgui2.ui.services.NetworkService;

public abstract class BaseFragment extends Fragment {
    private ProgressActivity activity;
    private boolean stopped;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (ProgressActivity)getActivity();
    }

    @Override
    public void onStart() {
        stopped = false;
        super.onStart();
    }

    @Override
    public void onStop() {
        stopped = true;
        super.onStop();
    }

    protected boolean isStopped() {
        return stopped;
    }

    public boolean beginProgress() {
        return activity.beginProgress();
    }

    public boolean endProgress() {
        return activity.endProgress();
    }


    public boolean alertNetwork() {
        if( !NetworkService.isNetworkAvailable() ) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setMessage( getString(R.string.NoNetwork) );
            alertDialog.show();
            return true;
        }
        return false;
    }
}
