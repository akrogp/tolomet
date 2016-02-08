package com.akrog.tolomet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.akrog.tolomet.data.Bundler;
import com.akrog.tolomet.data.Settings;
import com.akrog.tolomet.gae.GaeManager;
import com.akrog.tolomet.presenters.Downloader;
import com.akrog.tolomet.presenters.MyCharts;
import com.akrog.tolomet.presenters.MySpinner;
import com.akrog.tolomet.presenters.MySummary;
import com.akrog.tolomet.presenters.MyToolbar;
import com.akrog.tolomet.presenters.Presenter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Tolomet extends AppCompatActivity {
	
	// Creation and state
	
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_tolomet);

        settings.initialize(this, model);
        gaeManager.initialize(this);
        presenters.add(spinner);
        presenters.add(toolbar);
        presenters.add(charts);
        presenters.add(summary);
        for( Presenter presenter : presenters)
        	presenter.initialize(this, savedInstanceState);
        
        if( savedInstanceState != null )
        	Bundler.loadStations(model.getAllStations(), savedInstanceState);
        
        createTimer();
    }
        
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	for( Presenter presenter : presenters)
    		presenter.save(outState);
    	Bundler.saveStations(model.getAllStations(), outState);
    	cancelTimer();
    	if( downloading )
    		model.cancel();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if( settings.getUpdateMode() >= Settings.SMART_UPDATES && model.isOutdated() )
    		downloadData();
    	else
    		redraw();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		toolbar.inflateMenu(menu);
		return true;
	}

	@Override
	protected boolean onPrepareOptionsPanel(View view, Menu menu) {
		Log.i(getClass().getSimpleName(), "called");
		if (menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					Log.e(getClass().getSimpleName(), "onMenuOpened...unable to set icons for overflow menu", e);
				}
			}
		}
		return super.onPrepareOptionsPanel(view, menu);
	}
    
    private void createTimer() {
    	cancelTimer();
    	if( settings.getUpdateMode() != Settings.AUTO_UPDATES )
    		return;	
    	timer = new Runnable() {				
    		@Override
    		public void run() {
    			if( model.checkCurrent() )
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
    	if( timer == null || settings.getUpdateMode() != Settings.AUTO_UPDATES )
    		return false;
    	handler.removeCallbacks(timer);
    	int minutes = 1;
    	if( model.checkCurrent() && !model.getCurrentStation().isEmpty() ) {
			int dif = (int)((System.currentTimeMillis()-model.getCurrentStation().getStamp())/60/1000L);
			minutes = dif >= model.getRefresh() ? 1 : model.getRefresh()-dif;
		}
    	handler.postDelayed(timer, minutes*60*1000);
    	return true;
    }
    
    // Actions
    
    public void redraw() {
    	for( Presenter presenter : presenters)
    		presenter.updateView();
    }
    
    private void downloadData() {
    	if( downloading )
    		return;
    	if( alertNetwork() )
			return;
    	Downloader downloader = new Downloader(this, model);
    	downloader.execute();
    	downloading = true;
    }
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
	
	public boolean alertNetwork() {
		if( !isNetworkAvailable() ) {
    		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setMessage( getString(R.string.NoNetwork) );
			alertDialog.show();
			return true;
    	}
		return false;
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public Manager getModel() {
		return model;
	}
    
    // Events
    
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if( requestCode != SETTINGS_REQUEST )
    		return;
    	createTimer();
    	redraw();
    }
    
	public void onSpinner(Station station) {
		redraw();		
		if( station.isSpecial() )
			return;
		if( settings.getUpdateMode() >= Settings.SMART_UPDATES && model.isOutdated() )
			downloadData();
		else
			redraw();
	}
	
	public void onDownloaded() {		
		downloading = false;
		postTimer();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		//model.getCurrentStation().getMeteo().clear(System.currentTimeMillis()-24L*60*60*1000);
		model.getCurrentStation().getMeteo().clear(cal.getTimeInMillis());
        redraw();
        gaeManager.checkMotd();
    }
	
	public void onCancelled() {		
		downloading = false;
		postTimer();
		redraw();
	}	
	
	// Fields
	private final List<Presenter> presenters = new ArrayList<Presenter>();
	private final MySummary summary = new MySummary();
	private final MyCharts charts = new MyCharts(summary);
	private final MySpinner spinner = new MySpinner();
	private final MyToolbar toolbar = new MyToolbar();
	private final GaeManager gaeManager = new GaeManager();
	private final Manager model = new Manager();
	private final Settings settings = new Settings();
	private final Handler handler = new Handler();
	private Runnable timer;
	private boolean downloading = false;

	public static final int SETTINGS_REQUEST = 0;
}