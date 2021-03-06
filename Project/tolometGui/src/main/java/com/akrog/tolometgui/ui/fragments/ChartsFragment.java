package com.akrog.tolometgui.ui.fragments;

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
import android.view.WindowManager;
import android.widget.Toast;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.AppSettings;
import com.akrog.tolometgui.model.db.DbMeteo;
import com.akrog.tolometgui.ui.activities.BaseActivity;
import com.akrog.tolometgui.ui.activities.MainActivity;
import com.akrog.tolometgui.ui.presenters.MyCharts;
import com.akrog.tolometgui.ui.presenters.MySummary;
import com.akrog.tolometgui.ui.services.WeakTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ChartsFragment extends ToolbarFragment implements MyCharts.TravelListener {
    private static final DateFormat df = new SimpleDateFormat("EEE (dd/MMM)");
    private static int[] LIVE_ITEMS = {R.id.refresh_item, R.id.map_item, R.id.fly_item};
    private final Handler handler = new Handler();
    private Runnable timer;
    private AsyncTask<Void, Void, Station> thread;
    private MyCharts charts;
    private MySummary summary;
    private boolean flyNotified;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.charts_fragment, container, false);
    }

    @Override
    protected int getMenuResource() {
        return R.menu.charts;
    }

    @Override
    protected int[] getLiveMenuItems() {
        return LIVE_ITEMS;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        setScreenMode(settings.isFlying());
        updateEnabled();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        summary = new MySummary();
        summary.initialize(activity, savedInstanceState);

        charts = new MyCharts(summary, this);
        charts.initialize(activity, savedInstanceState);

        createTimer();
        model.liveCurrentStation().observe(getViewLifecycleOwner(), station -> {
            downloadData(null);
            updateEnabled();
        });
        model.liveCurrentMeteo().observe(getViewLifecycleOwner(), station -> redraw());
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
        else if( id == R.id.map_item )
            ((MainActivity)getActivity()).navigate(R.id.nav_maps);
        else if( id == R.id.fly_item )
            setScreenMode(!settings.isFlying());

        return super.onOptionsItemSelected(item);
    }

    private void onRefresh() {
        if( !model.isOutdated() ) {
            if( charts.getZoomed() ) {
                new Thread(() -> DbMeteo.getInstance().trim()).start();
                redraw();
            } else {
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

    private void setScreenMode(boolean flying) {
        if( menu == null )
            return;
        MenuItem itemMode = menu.findItem(R.id.fly_item);
        BaseActivity activity = (BaseActivity)getActivity();
        settings.setFlying(flying);
        if( flying ) {
            itemMode.setIcon(R.drawable.ic_land_mode);
            itemMode.setTitle(R.string.LandMode);
            activity.lockScreenOrientation();
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            settings.setUpdateMode(AppSettings.AUTO_UPDATES);
            Toast.makeText(activity,R.string.Takeoff,Toast.LENGTH_SHORT).show();
            flyNotified = true;
        } else {
            itemMode.setIcon(R.drawable.ic_flight_mode);
            itemMode.setTitle(R.string.FlyMode);
            activity.unlockScreenOrientation();
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            settings.setUpdateMode(AppSettings.SMART_UPDATES);
            if( flyNotified == true ) {
                Toast.makeText(activity, R.string.Landed, Toast.LENGTH_SHORT).show();
                flyNotified = false;
            }
        }
        //activity.onSettingsChanged(AppSettings.);
        onSettingsChanged();
    }

    @Override
    public void onTravel(long date) {
        if( settings.getUpdateMode() != 0 )
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
    protected void updateEnabled(boolean enabled) {
        super.updateEnabled(enabled);
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

    public void onDownloaded(Station station) {
        thread = null;
        if( isStopped() )
            return;
        postTimer();
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
                    ((MainActivity)activity).navigate(R.id.nav_origin);
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

    @Override
    public boolean needsScreenshotStation() {
        return true;
    }

    @Override
    public String getScreenshotSubject() {
        return getString(R.string.ShareSubject);
    }

    @Override
    public String getScreenshotText() {
        return String.format("%s %s%s",
            getString(R.string.ShareTextPre), model.getCurrentStation().getName(), getString(R.string.ShareTextPost));
    }

    private static class DownloadTask extends WeakTask<ChartsFragment, Void, Void, Station> {
        private final Long date;

        DownloadTask(ChartsFragment fragment, Long date) {
            super(fragment);
            this.date = date;
        }

        @Override
        protected Station doInBackground(ChartsFragment fragment, Void... params) {
            if( date == null ) {
                DbMeteo.getInstance().trim();
                return fragment.model.refresh();
            }
            return fragment.model.travel(date);
        }
        @Override
        protected void onPostExecute(ChartsFragment fragment, Station station) {
            if( date != null && station != null )
                Toast.makeText(fragment.getActivity(),
                    df.format(new Date(date)),
                    Toast.LENGTH_SHORT
                ).show();
            fragment.endProgress();
            fragment.onDownloaded(station);
        }

        @Override
        protected void onCancelled(ChartsFragment fragment, Station station) {
            fragment.model.cancel();
            //logFile("onCancelled1");
            onPostExecute(null);
            //logFile("onCancelled2");
        }
    }
}
