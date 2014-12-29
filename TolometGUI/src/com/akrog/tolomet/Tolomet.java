package com.akrog.tolomet;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

public class Tolomet extends Activity {
	
	// Creation and state
	
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_tolomet);
        
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
        
        updateTimer();
    }
        
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	for( Controller controller : controllers )
    		controller.save(outState);
    	Bundler.saveStations(model.getAllStations(), outState);
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	if( settings.getUpdateMode() >= Settings.SMART_UPDATES && model.isOutdated() )
    		downloadData();
    	else
    		redraw();
    }
    
    private void updateTimer() {
    	if( settings.getUpdateMode() == Settings.AUTO_UPDATES ) {
    		timer = new Runnable() {				
				@Override
				public void run() {
					if( settings.getUpdateMode() != Settings.AUTO_UPDATES )
						timer = null;
					if( timer == null )
						return;
					if( !model.checkCurrent() )
						return;
					downloadData();
					int minutes = 1;
					if( !model.getCurrentStation().isEmpty() ) {
						int dif = (int)((System.currentTimeMillis()-model.getCurrentStation().getStamp())/60*1000L);
						minutes = dif >= model.getRefresh() ? 1 : model.getRefresh();
					}
					handler.postDelayed(timer, minutes*60*1000);
				}
			};
			timer.run();
    	} else if( timer != null ) {
    		handler.removeCallbacks(timer);
    		timer = null;
    	}
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
				alertDialog.setMessage( getString(R.string.Impatient) + " " + model.getRefresh() + " " + getString(R.string.minutes) );
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
    	updateTimer();
    	redraw();
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
    	super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_tolomet, menu);
        return true;
    }
    
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
		model.getCurrentStation().getMeteo().clear(System.currentTimeMillis()-24L*60*60*1000);
        redraw();
        gaeManager.checkMotd();
    }
	
	public void onCancelled() {
		downloading = false;
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