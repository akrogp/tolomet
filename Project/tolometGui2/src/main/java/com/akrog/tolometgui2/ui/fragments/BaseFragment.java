package com.akrog.tolometgui2.ui.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.model.AppSettings;
import com.akrog.tolometgui2.ui.activities.BaseActivity;
import com.akrog.tolometgui2.ui.services.NetworkService;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    protected AppSettings settings;
    private boolean stopped;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        settings = AppSettings.getInstance();
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

    public boolean alertNetwork() {
        if( !NetworkService.isNetworkAvailable() ) {
            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setMessage( getString(R.string.NoNetwork) );
            alertDialog.show();
            return true;
        }
        return false;
    }

    public void requestPermission(String permission, int rationale, Runnable onGranted, Runnable onDenied) {
        ((BaseActivity)getActivity()).requestPermission(permission, rationale, onGranted, onDenied);
    }

    public abstract void onSettingsChanged();
}
