package com.akrog.tolometgui2.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.model.AppSettings;
import com.akrog.tolometgui2.ui.activities.ToolbarActivity;
import com.akrog.tolometgui2.ui.presenters.MyCharts;
import com.akrog.tolometgui2.ui.presenters.MySummary;
import com.akrog.tolometgui2.ui.viewmodels.ChartsViewModel;
import com.akrog.tolometgui2.ui.viewmodels.MainViewModel;

public class ChartsFragment extends BaseFragment {
    private AppSettings settings;
    private MainViewModel model;
    private ChartsViewModel chartsModel;
    private Menu menu;
    private final Handler handler = new Handler();
    private Runnable timer;
    private AsyncTask<Void, Void, Boolean> thread;
    private MyCharts charts;
    private MySummary summary;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.charts_fragment, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        inflater.inflate(R.menu.charts, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ToolbarActivity activity = (ToolbarActivity)getActivity();

        settings = AppSettings.getInstance();
        model = ViewModelProviders.of(activity).get(MainViewModel.class);
        chartsModel = ViewModelProviders.of(this).get(ChartsViewModel.class);

        summary = new MySummary();
        summary.initialize(activity, savedInstanceState);

        charts = new MyCharts(summary);
        charts.initialize(activity, savedInstanceState);

        createTimer();
        model.liveCurrentStation().observe(this, station -> downloadData());
        model.liveCurrentMeteo().observe(this, station -> {
            redraw();
            if( station.isEmpty() )
                askSource();
        });
    }

    @Override
    public void onStop() {
        cancelTimer();
        if (thread != null) {
            model.cancel();
            thread.cancel(true);
            thread = null;
        }
        super.onStop();
    }

    private void createTimer() {
        cancelTimer();
        if( settings.getUpdateMode() != AppSettings.AUTO_UPDATES )
            return;
        timer = () -> {
            if( model.checkStation() )
                downloadData();
        };
    }

    private void downloadData() {
        if (thread != null)
            return;
        if (alertNetwork())
            return;
        if( !beginProgress() )
            return;
        thread = new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                return model.refresh();
            }
            @Override
            protected void onPostExecute(Boolean ok) {
                super.onPostExecute(ok);
                endProgress();
                onDownloaded();
            }
            @Override
            protected void onCancelled() {
                super.onCancelled();
                //logFile("onCancelled1");
                onPostExecute(false);
                //logFile("onCancelled2");
            }
        };
        thread.execute();
    }

    public void onDownloaded() {
        thread = null;
        if( isStopped() )
            return;
        postTimer();
    }

    private void redraw() {
        charts.updateView();
        summary.updateView();
    }

    private void askSource() {
        //logFile("No data => station:" + model.getCurrentStation().getId() + " empty:" + model.getCurrentStation().isEmpty());
        new AlertDialog.Builder(getActivity()).setTitle(R.string.NoData)
            .setMessage(R.string.RedirectWeb)
            .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    //startActivity(new Intent(ChartsActivity.this, ProviderActivity.class));
                }
            })
            .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .create().show();
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
