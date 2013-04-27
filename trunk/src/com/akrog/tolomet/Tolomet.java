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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;

import com.akrog.tolomet.data.Station;
import com.akrog.tolomet.data.StationManager;
import com.akrog.tolomet.data.WindProviderManager;
import com.akrog.tolomet.gae.GaeClient;
import com.akrog.tolomet.gae.Motd;
import com.akrog.tolomet.view.AboutDialog;
import com.akrog.tolomet.view.MyCharts;
import com.akrog.tolomet.view.MySpinner;
import com.akrog.tolomet.view.Summary;

public class Tolomet extends Activity
	implements OnItemSelectedListener, View.OnClickListener, OnCheckedChangeListener {
	
	// Creation and state
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_tolomet);

        this.stations = new StationManager(this, savedInstanceState);
        this.provider = new WindProviderManager(this);
        this.gaeClient = new GaeClient(this);
        this.summary = new Summary(this, this.stations);
        
        this.spinner = new MySpinner(this, this.stations, savedInstanceState);               

        this.buttonRefresh = (ImageButton)findViewById(R.id.button1);
        this.buttonRefresh.setOnClickListener(this);
        this.buttonInfo = (ImageButton)findViewById(R.id.button2);
        this.buttonInfo.setOnClickListener(this);
        
        this.favorite = (CheckBox)findViewById(R.id.favorite_button);
        this.favorite.setChecked(false);
        this.favorite.setOnCheckedChangeListener(this);
        
        this.charts = new MyCharts(this, this.stations);                
    }
	
	@Override
    protected void onPause() {
    	//this.spinner.saveState();
    	super.onPause();
    }
        
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	this.stations.saveState(outState);
    	this.spinner.saveState();
    	this.provider.cancelDownload(this.stations.current);
    }
    
    // Actions

	private void downloadData() {
    	if( !this.provider.updateTimes(this.stations.current) ) {
    		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setMessage( getString(R.string.Impatient) + " " + this.provider.getRefresh(this.stations.current) + " " + getString(R.string.minutes) );
			alertDialog.show();
			return;
    	}
    	if( alertNetwork() )
			return;
    	this.provider.download(this.stations.current); 
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
    	this.charts.redraw();
        this.summary.update();
        this.favorite.setChecked(this.stations.current.favorite);
    }
    
    /*public void postRedraw() {
    	runOnUiThread(new Runnable() {
            public void run() {
            	redraw();
            }
        });
    }*/
    
    // Buttons events
    
    public void onClick(View v) {
    	if( this.stations.current.isSpecial() )
    		return;
    	switch( v.getId() ) {
    		case R.id.button1:
    			downloadData();
    			break;
    		case R.id.button2:
    			if( alertNetwork() )
    				return;
    			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(this.provider.getInfoUrl(this.stations.current))));
    			break;
    	}
	}
    
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    	if( isChecked )
    		this.stations.addFavorite();
    	else if( this.stations.removeFavorite() )
    		this.spinner.notifyDataSetChanged();
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
    	}
    	return true;
    }

    // Spinner events
    
	public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
		Station station = this.spinner.getSelectedItem();
		this.stations.current.replace(station);
		this.favorite.setChecked(station.favorite);
		
		if( station.isSpecial() ) {
			this.stations.current.clear();
			this.buttonRefresh.setEnabled(false);
			this.buttonInfo.setEnabled(false);
			this.favorite.setEnabled(false);
			this.spinner.setType(station);
			redraw();
			return;
		}
		this.buttonRefresh.setEnabled(true);
		this.buttonInfo.setEnabled(true);
		this.favorite.setEnabled(true);
		charts.setZoom(this.provider.getRefresh(this.stations.current)<60);
		charts.setRefresh(this.provider.getRefresh(this.stations.current));
		if( this.stations.current.isOutdated() )
			downloadData();
		else
			redraw();	
	}
	
	public void onNothingSelected(AdapterView<?> arg0) {
		this.stations.current.clear();
	}
	
	// Downloader events
	
	public void onDownloaded() {
        Station sel = this.spinner.getSelectedItem();
        /*try {
        	this.provider.updateStation(this.stations.current, result);
        	sel.replace(this.stations.current);
        } catch (Exception e) {
			System.out.println( e.getMessage() );
			this.stations.current.replace(sel);
		}*/
        sel.replace(this.stations.current);
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
	private MySpinner spinner;
	private Summary summary;
	private CheckBox favorite;
	private GaeClient gaeClient;
	private StationManager stations;	
	private WindProviderManager provider;
}