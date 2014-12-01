package com.akrog.tolomet;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.akrog.tolomet.controllers.Controller;
import com.akrog.tolomet.controllers.Downloader;
import com.akrog.tolomet.controllers.MyButtons;
import com.akrog.tolomet.controllers.MyCharts;
import com.akrog.tolomet.controllers.MySpinner;
import com.akrog.tolomet.controllers.MySummary;
import com.akrog.tolomet.data.Settings;
import com.akrog.tolomet.gae.GaeClient;
import com.akrog.tolomet.gae.Motd;

public class Tolomet extends Activity {
	
	// Creation and state
	
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_tolomet);
        
        settings.initialize(this, model);
        gaeClient = new GaeClient(this);
        controllers.add(spinner);
        controllers.add(buttons);
        controllers.add(charts);
        controllers.add(summary);        
        for( Controller controller : controllers )
        	controller.initialize(this, savedInstanceState);
    }
        
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	for( Controller controller : controllers )
    		controller.save(outState);
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
    	if( alertNetwork() )
			return;
    	Downloader downloader = new Downloader(this, model);
    	downloader.execute(); 
    }
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
	}
	
	private boolean alertNetwork() {
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
    	if( !model.getCurrentStation().isOutdated() ) {
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

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
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
    			startActivityForResult(new Intent(Tolomet.this, SettingsActivity.class),-1);
    			break;
    		default:
                return super.onOptionsItemSelected(item);
    	}
    	return true;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	charts.redraw();
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
	public void onSpinner(Station station) {
		redraw();		
		if( station.isSpecial() )
			return;
		charts.setRefresh(model.getRefresh());
		if( station.isOutdated() )
			downloadData();
		else
			redraw();
	}
	
	public void onDownloaded() {
		model.getCurrentStation().getMeteo().clear(System.currentTimeMillis()-24L*60*60*1000);
        redraw();
        checkMotd();
    }
	
	public void onCancelled() {
		redraw();
	}

	// GAE
	
	private String getChanges( Motd motd  ) {
		StringWriter string = new StringWriter();
		PrintWriter writer = new PrintWriter(string);
		if( motd.getChanges() != null ) {
			writer.println(getString(R.string.improvements)+" v"+motd.getVersion()+":");
			for( String str : motd.getChanges() )
				writer.println("* "+str);
		}
		writer.close();
		return string.toString();
	}
	
	public void onMotd(Motd motd) {
		SharedPreferences settings = getPreferences(0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong("gae:last", Calendar.getInstance().getTimeInMillis());
		editor.commit();
		
		if( motd.getVersion() != null ) {		
			new AlertDialog.Builder(this)
		    .setTitle(R.string.newversion)
		    .setMessage(getChanges(motd))
		    .setPositiveButton(R.string.update,
		    new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	dialog.dismiss();
		            Intent marketIntent = new Intent(
		            Intent.ACTION_VIEW,
		            Uri.parse("http://market.android.com/details?id=com.akrog.tolomet"));
		            startActivity(marketIntent);
		        }
		    })
		    .setNegativeButton(R.string.tomorrow,
		    new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		            dialog.dismiss();
		        }
		    }).create().show();
		} else if( motd.getMotd() != null) {
			editor.putLong("gae:stamp", motd.getStamp());
			editor.commit();
			new AlertDialog.Builder(this)
		    .setTitle(R.string.motd)
		    .setMessage(motd.getMotd())
		    .create().show();			
		}				
	}
	
	private void checkMotd() {
    	if( this.gaeClient.getStatus() == Status.RUNNING || !isNetworkAvailable() )
    		return;
    	
    	// Once a day
    	SharedPreferences settings = getPreferences(0);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(settings.getLong("gae:last", 0));
		Calendar cal2 = Calendar.getInstance();
		if( cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
			cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) )
			return;
		
		long stamp = settings.getLong("gae:stamp", 0);
		int version = 0;
		try {
			version = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
		} catch( Exception e ) {}				
    	
    	this.gaeClient = new GaeClient(this);
    	this.gaeClient.execute("http://tolomet-gae.appspot.com/rest/motd?version="+version+"&stamp="+stamp);
	}	
	
	// Fields
	private final List<Controller> controllers = new ArrayList<Controller>();
	private final MyCharts charts = new MyCharts();
	private final MySpinner spinner = new MySpinner();
	private final MyButtons buttons = new MyButtons();
	private final MySummary summary = new MySummary();
	private GaeClient gaeClient;
	private final Manager model = new Manager();
	private final Settings settings = new Settings();
	public static Tolomet instance = null;	
}