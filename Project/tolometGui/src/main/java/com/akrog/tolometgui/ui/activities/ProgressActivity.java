package com.akrog.tolometgui.ui.activities;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akrog.tolometgui.R;
import com.akrog.tolometgui.Tolomet;

import java.util.ArrayList;
import java.util.List;

public abstract class ProgressActivity extends BaseActivity {
    public ProgressBar getProgressBar() {
        return (ProgressBar)findViewById(R.id.progressbar);
    }

    public boolean beginProgress() {
        if( inProgress )
            return false;
        lockScreenOrientation();
        ProgressBar progressBar = getProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
        inProgress = true;
        return true;
    }

    public boolean endProgress() {
        if( !inProgress )
            return false;
        ProgressBar progressBar = getProgressBar();
        progressBar.setVisibility(View.GONE);
        inProgress = false;
        listCancel.clear();
        unlockScreenOrientation();
        return true;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    @Override
    public void onBackPressed() {
        if( inProgress )
            onCancel();
        else
            super.onBackPressed();
    }

    public void onCancel() {
        Toast.makeText(Tolomet.getAppContext(),getString(R.string.DownloadCancelled),Toast.LENGTH_SHORT).show();
        for( Runnable listenner : listCancel )
            listenner.run();
        endProgress();
    }

    public void addCancelListenner( Runnable listenner ) {
        listCancel.add(listenner);
    }

    private boolean inProgress = false;
    private final List<Runnable> listCancel = new ArrayList<>();
}
