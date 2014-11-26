package com.akrog.tolomet;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.akrog.tolomet.controllers.MySpinner;
import com.akrog.tolomet.data.Downloader;
import com.akrog.tolomet.gae.GaeClient;
import com.akrog.tolomet.gae.Motd;
import com.akrog.tolomet.view.AboutDialog;
import com.akrog.tolomet.view.MyCharts;

public class Tolomet extends Activity implements View.OnClickListener, OnCheckedChangeListener, OnSharedPreferenceChangeListener {
	
	// Creation and state
	
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_tolomet);

        // TO-DO: load state from savedInstanceState in model
        gaeClient = new GaeClient(this);
        
        spinner = new MySpinner(this, model, savedInstanceState);
        
        summary = (TextView)findViewById(R.id.textView1);

        buttonRefresh = (ImageButton)findViewById(R.id.button1);
        buttonRefresh.setOnClickListener(this);
        buttonInfo = (ImageButton)findViewById(R.id.button2);
        buttonInfo.setOnClickListener(this);
        
        favorite = (CheckBox)findViewById(R.id.favorite_button);
        favorite.setChecked(false);
        favorite.setOnCheckedChangeListener(this);
        
        charts = new MyCharts(this, model);
        
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).registerOnSharedPreferenceChangeListener(this);
    }
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	};
	
	@Override
    protected void onPause() {
    	super.onPause();
    }
        
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	/*this.stations.saveState(outState);
    	this.spinner.saveState();
    	this.provider.cancelDownload(this.stations.current);*/
    }
    
    // Actions

	private void downloadData() {
    	if( !model.getCurrentStation().isOutdated() ) {
    		if( charts.getZoomed() )
    			charts.redraw();
    		else {
	    		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
				alertDialog.setMessage( getString(R.string.Impatient) + " " + model.getRefresh() + " " + getString(R.string.minutes) );
				alertDialog.show();
    		}
			return;
    	}
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
    
    public void redraw() {
    	charts.redraw();
        updateSummary();
        favorite.setChecked(model.getCurrentStation().isFavorite());
    }    
    
    /*public void postRedraw() {
    	runOnUiThread(new Runnable() {
            public void run() {
            	redraw();
            }
        });
    }*/
    
    private void updateSummary() {
    	if( model.getCurrentStation().isEmpty() )
    		summary.setText(getString(R.string.NoData));
    	else
    		summary.setText(model.getSummary(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE));
    }
    
    // Buttons events
    
    public void onClick(View v) {
    	if( model.getCurrentStation().isSpecial() )
    		return;
    	switch( v.getId() ) {
    		case R.id.button1:
    			downloadData();
    			break;
    		case R.id.button2:
    			if( alertNetwork() )
    				return;
    			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getInforUrl())));
    			break;
    	}
	}
    
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	model.getCurrentStation().setFavorite(isChecked);
	}
    
    // Menu events

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.activity_tolomet, menu);
        return true;
    }
    
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
    
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    	this.charts.redraw();
	}
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	this.charts.redraw();
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
	public void onSpinner(Station station) {
		favorite.setChecked(station.isFavorite());
		buttonRefresh.setEnabled(!station.isSpecial());
		buttonInfo.setEnabled(!station.isSpecial());
		favorite.setEnabled(!station.isSpecial());
		
		if( station.isSpecial() )
			redraw();
		else {		
			charts.setRefresh(model.getRefresh());
			if( station.isOutdated() )
				downloadData();
			else
				redraw();	
		}
	}
	
	// Downloader events
	
	public void onDownloaded() {
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

	private ImageButton buttonRefresh, buttonInfo;
	private MyCharts charts;
	@SuppressWarnings("unused")
	private MySpinner spinner;
	private TextView summary;
	private CheckBox favorite;
	private GaeClient gaeClient;
	private final Manager model = new Manager();
	public static Tolomet instance = null;	
}