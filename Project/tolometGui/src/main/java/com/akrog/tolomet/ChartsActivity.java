package com.akrog.tolomet;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.akrog.tolomet.data.AppSettings;
import com.akrog.tolomet.data.DbMeteo;
import com.akrog.tolomet.gae.GaeManager;
import com.akrog.tolomet.presenters.MyCharts;
import com.akrog.tolomet.presenters.MySummary;
import com.akrog.tolomet.presenters.Presenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChartsActivity extends BaseActivity {
	
	// Creation and state
	
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);

        createView(savedInstanceState, R.layout.activity_tolomet,
				R.id.favorite_item, R.id.refresh_item,
				R.id.info_item, R.id.map_item, R.id.origin_item,
				R.id.share_item, R.id.whatsapp_item,
				R.id.fly_item,
				R.id.settings_item, R.id.about_item, R.id.report_item);

        gaeManager.initialize(this);
        presenters.add(charts);
        presenters.add(summary);
        for( Presenter presenter : presenters)
        	presenter.initialize(this, savedInstanceState);

        if( model.checkStation() )
            DbMeteo.getInstance().refresh(model.getCurrentStation());

        createTimer();
    }
        
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	for( Presenter presenter : presenters)
    		presenter.save(outState);
    }

    @Override
    protected void onStop() {
        cancelTimer();
        if (thread != null) {
            model.cancel();
            thread.cancel(true);
            thread = null;
        }
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if( intent != null )
            setIntent(intent);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
        Intent intent = getIntent();
        String stationId = intent.getStringExtra(EXTRA_STATION_ID);
        if( stationId != null ) {
            String country = intent.getStringExtra(EXTRA_COUNTRY);
            if( country != null )
                spinner.setCountry(country);
            intent.removeExtra(EXTRA_STATION_ID);
            intent.removeExtra(EXTRA_COUNTRY);
            Station station = model.findStation(stationId);
            if( station != null )
                spinner.selectStation(station);
        }
    	if( settings.getUpdateMode() >= AppSettings.SMART_UPDATES && model.isOutdated() )
    		downloadData();
    }
    
    private void createTimer() {
    	cancelTimer();
    	if( settings.getUpdateMode() != AppSettings.AUTO_UPDATES )
    		return;	
    	timer = new Runnable() {				
    		@Override
    		public void run() {
    			if( model.checkStation() )
    				downloadData();					
    		}
    	};
    	timer.run();
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
    
    // Actions

	@Override
    public void redraw() {
		super.redraw();
    	for( Presenter presenter : presenters)
    		presenter.updateView();
    }
    
    private void downloadData() {
		if (thread != null)
			return;
		if (alertNetwork()) {
            model.loadCache();
            return;
        }
        thread = new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                beginProgress();
            }
            @Override
            protected Void doInBackground(Void... params) {
                model.refresh();
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                endProgress();
                onDownloaded();
            }
            @Override
            protected void onCancelled() {
                super.onCancelled();
                onPostExecute(null);
            }
        }.execute();
	}

	@Override
    public void onRefresh() {
    	if( !model.isOutdated() ) {
    		if( charts.getZoomed() )
    			charts.updateView();
    		else {
	    		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
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
    		downloadData();
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
	public void onBrowser() {
	}

	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode,resultCode,data);
    	if( requestCode != SETTINGS_REQUEST )
    		return;
		onChangedSettings();
    }

	@Override
	public void onChangedSettings() {
		createTimer();
		redraw();
	}

	@Override
	public void onSelected(Station station) {
		redraw();		
		if( station.isSpecial() )
			return;
		if( settings.getUpdateMode() >= AppSettings.SMART_UPDATES && model.isOutdated() )
			downloadData();
	}

	@Override
	public String getScreenShotSubject() {
		return getString(R.string.ShareSubject);
	}

	@Override
	public String getScreenShotText() {
		return String.format("%s %s%s",
				getString(R.string.ShareTextPre), model.getCurrentStation().getName(), getString(R.string.ShareTextPost));
	}

	public void onDownloaded() {		
		thread = null;
		postTimer();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		model.getCurrentStation().getMeteo().clear(cal.getTimeInMillis());
        redraw();
        gaeManager.checkMotd();
    }
	
	// Fields
	private final List<Presenter> presenters = new ArrayList<Presenter>();
	private final MySummary summary = new MySummary();
	private final MyCharts charts = new MyCharts(summary);
	private final GaeManager gaeManager = new GaeManager();
	private final Handler handler = new Handler();
	private Runnable timer;
    private AsyncTask<Void, Void, Void> thread;

	public static String EXTRA_STATION_ID = "com.akrog.tolomet.stationId";
    public static String EXTRA_COUNTRY = "com.akrog.tolomet.country";
	public static final int SETTINGS_REQUEST = 0;
	public static final int MAP_REQUEST = 1;
}