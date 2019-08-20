package com.akrog.tolometgui2.ui.fragments;

import android.app.AlertDialog;

import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.ui.services.NetworkService;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    private boolean stopped;

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
}
