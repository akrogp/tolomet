package com.akrog.tolometgui2.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.model.AppSettings;
import com.akrog.tolometgui2.model.db.DbMeteo;
import com.akrog.tolometgui2.ui.activities.ToolbarActivity;
import com.akrog.tolometgui2.ui.presenters.MyCharts;
import com.akrog.tolometgui2.ui.presenters.MySummary;
import com.akrog.tolometgui2.ui.services.WeakTask;
import com.akrog.tolometgui2.ui.viewmodels.ChartsViewModel;
import com.akrog.tolometgui2.ui.viewmodels.MainViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public class ChartsFragment extends ToolbarFragment implements MyCharts.TravelListener {
    private static final DateFormat df = new SimpleDateFormat("EEE (dd/MMM)");
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
        updateEnabled();
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

        charts = new MyCharts(summary, this);
        charts.initialize(activity, savedInstanceState);

        createTimer();
        model.liveCurrentStation().observe(this, station -> {
            downloadData(null);
            updateEnabled();
        });
        model.liveCurrentMeteo().observe(this, station -> redraw());
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

    @Override
    public void onResume() {
        super.onResume();
        if( settings.getUpdateMode() >= AppSettings.SMART_UPDATES && model.isOutdated() )
            downloadData(null);
        redraw();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.refresh_item )
            onRefresh();

        return super.onOptionsItemSelected(item);
    }

    public void onRefresh() {
        if( !model.isOutdated() ) {
            if( charts.getZoomed() )
                new Thread(() -> DbMeteo.getInstance().trim()).start();
            else {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                int minutes = model.getRefresh();
                String message;
                Locale locale = Locale.getDefault();
                if( minutes > 60 && minutes%60 == 0 )
                    message = String.format(locale, "%s %d %s", getString(R.string.Impatient), minutes/60, getString(R.string.hours));
                else
                    message = String.format(locale, "%s %d %s", getString(R.string.Impatient), minutes, getString(R.string.minutes));
                alertDialog.setMessage(message);
                alertDialog.show();
            }
        } else
            downloadData(null);
    }

    @Override
    public void onTravel(long date) {
        downloadData(date);
    }

    @Override
    public void onCancel() {
        super.onCancel();
        if( thread == null )
            return;
        model.cancel();
        thread.cancel(true);
        postTimer();
        redraw();
    }

    @Override
    public void onSettingsChanged() {
        postTimer();
        redraw();
    }

    @Override
    public boolean beginProgress() {
        if( !super.beginProgress() )
            return false;
        updateEnabled(false);
        return true;
    }

    @Override
    public boolean endProgress() {
        if( !super.endProgress() )
            return false;
        updateEnabled();
        return true;
    }

    private void updateEnabled() {
        updateEnabled(model.checkStation());
    }

    private void updateEnabled(boolean enabled) {
        if( menu != null ) {
            setEnabled(menu.findItem(R.id.refresh_item), enabled);
            setEnabled(menu.findItem(R.id.fly_item), enabled);
        }
        charts.setEnabled(enabled);
        summary.setEnabled(enabled);
    }

    private void createTimer() {
        cancelTimer();
        if( settings.getUpdateMode() != AppSettings.AUTO_UPDATES )
            return;
        timer = () -> {
            if( model.checkStation() )
                downloadData(null);
        };
    }

    private void downloadData(Long date) {
        if (thread != null || !model.checkStation() )
            return;
        if (alertNetwork())
            return;
        if( !beginProgress() )
            return;
        thread = new DownloadTask(this, date);
        thread.execute();
    }

    public void onDownloaded() {
        thread = null;
        if( isStopped() )
            return;
        postTimer();
        Station station = model.getCurrentStation();
        if( station != null && station.isEmpty() )
            askSource();
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

    private static class DownloadTask extends WeakTask<ChartsFragment, Void, Void, Boolean> {
        private final Long date;

        DownloadTask(ChartsFragment fragment, Long date) {
            super(fragment);
            this.date = date;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            ChartsFragment fragment = getContext();
            if( fragment == null )
                return null;
            if( date == null ) {
                DbMeteo.getInstance().trim();
                return fragment.model.refresh();
            }
            return fragment.model.travel(date);
        }
        @Override
        protected void onPostExecute(Boolean ok) {
            ChartsFragment fragment = getContext();
            if( fragment == null )
                return;
            if( date != null && ok )
                Toast.makeText(fragment.getActivity(),
                    df.format(new Date(date)),
                    Toast.LENGTH_SHORT
                ).show();
            fragment.endProgress();
            fragment.onDownloaded();
        }
        @Override
        protected void onCancelled() {
            ChartsFragment fragment = getContext();
            if( fragment == null )
                return;
            fragment.model.cancel();
            //logFile("onCancelled1");
            onPostExecute(false);
            //logFile("onCancelled2");
        }
    }
}
