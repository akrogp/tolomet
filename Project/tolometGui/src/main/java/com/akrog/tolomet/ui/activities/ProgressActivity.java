package com.akrog.tolomet.ui.activities;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;

import java.util.ArrayList;
import java.util.List;

public abstract class ProgressActivity extends BaseActivity {
    public ProgressBar getProgressBar() {
        return (ProgressBar)findViewById(R.id.progressbar);
    }

    public boolean beginProgress() {
        if( inProgress )
            return false;
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
        return true;
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
