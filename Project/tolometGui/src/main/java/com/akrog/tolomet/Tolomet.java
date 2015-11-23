package com.akrog.tolomet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.akrog.tolomet.controllers.Controller;
import com.akrog.tolomet.controllers.Downloader;
import com.akrog.tolomet.controllers.MyButtons;
import com.akrog.tolomet.controllers.MyCharts;
import com.akrog.tolomet.controllers.MySpinner;
import com.akrog.tolomet.controllers.MySummary;
import com.akrog.tolomet.data.Bundler;
import com.akrog.tolomet.data.Settings;
import com.akrog.tolomet.gae.GaeManager;

public class Tolomet extends AppCompatActivity {
	
	// Creation and state
	
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_tolomet);
		Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
    	setSupportActionBar(myToolbar);
		ActionBar ab = getSupportActionBar();
		ab.setLogo(R.drawable.ic_launcher);
		ab.setDisplayUseLogoEnabled(true);
		ab.setDisplayShowTitleEnabled(false);
        
        settings.initialize(this, model);
        gaeManager.initialize(this);
        controllers.add(spinner);
        controllers.add(buttons);
        controllers.add(charts);
        controllers.add(summary);        
        for( Controller controller : controllers )
        	controller.initialize(this, savedInstanceState);
        
        if( savedInstanceState != null )
        	Bundler.loadStations(model.getAllStations(), savedInstanceState);
        
        createTimer();
    }

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }
        
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	for( Controller controller : controllers )
    		controller.save(outState);
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
    	for( Controller controller : controllers )
    		controller.redraw();
    }    
    
    public void postRedraw() {
    	runOnUiThread(new Runnable() {
            public void run() {
            	redraw();
            }
        });
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
    			charts.redraw();
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
    
    public void onInfoUrl() {
    	if( alertNetwork() )
			return;
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getInforUrl())));
    }
    
    public void onSettings() {
    	//startActivity(new Intent(Tolomet.this, SettingsActivity.class));
    	startActivityForResult(new Intent(Tolomet.this, SettingsActivity.class), 0);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	if( requestCode != 0 )
    		return;
    	createTimer();
    	redraw();
    }

    /*@Override
    public boolean onCreateOptionsMenu( Menu menu ) {
    	super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_tolomet, menu);
        return true;
    }*/
    
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
    	switch( item.getItemId() ) {
    		case R.id.about:
    			AboutDialog about = new AboutDialog(this);
    			about.setTitle(getString(R.string.About));
    			about.show();
    			break;
    		case R.id.menu_settings:
    			onSettings();
    			break;
    		default:
                return super.onOptionsItemSelected(item);
    	}
    	return true;
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
	private final List<Controller> controllers = new ArrayList<Controller>();
	private final MyCharts charts = new MyCharts();
	private final MySpinner spinner = new MySpinner();
	private final MyButtons buttons = new MyButtons();
	private final MySummary summary = new MySummary();
	private final GaeManager gaeManager = new GaeManager();
	private final Manager model = new Manager();
	private final Settings settings = new Settings();
	private final Handler handler = new Handler();
	private Runnable timer;
	private boolean downloading = false;
}