package com.akrog.tolomet.gae;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask.Status;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.AppSettings;

public class GaeManager {
	private Tolomet tolomet;
	private AppSettings settings;
	private GaeClient gaeClient;
	
	public void initialize( Tolomet tolomet ) {
		this.tolomet = tolomet;
		settings = tolomet.getSettings();
	}
	
	public void checkMotd() {
		if( (gaeClient != null && gaeClient.getStatus() == Status.RUNNING) || !tolomet.isNetworkAvailable() )
    		return;
		
		// Once a day
		long stamp = settings.getGaeStamp();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(stamp);
		Calendar cal2 = Calendar.getInstance();
		if( cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
			cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) )
			return;
				
		int version = 0;
		try {
			version = tolomet.getPackageManager().getPackageInfo(tolomet.getPackageName(), 0).versionCode;
		} catch( Exception e ) {}				
    	
    	gaeClient = new GaeClient(this);
    	gaeClient.execute(String.format(
    		"http://tolomet-gae.appspot.com/rest/motd?version=%s&stamp=%s&lang=%s",
    		version, stamp, Locale.getDefault().getLanguage()));
	}
	
	public void onMotd(Motd motd) {		
		settings.saveGaeStamp(System.currentTimeMillis());
		
		if( motd.getVersion() != null ) {		
			new AlertDialog.Builder(tolomet)
		    	.setTitle(R.string.newversion)
		    	.setMessage(getChanges(motd))
		    	.setPositiveButton(R.string.update,
			    new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			        	dialog.dismiss();
			            Intent marketIntent = new Intent(
			            Intent.ACTION_VIEW,
			            Uri.parse("http://market.android.com/details?id=com.akrog.tolomet"));
			            tolomet.startActivity(marketIntent);
			        }
		    })
		    .setNegativeButton(R.string.tomorrow,
		    new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		            dialog.dismiss();
		        }
		    }).create().show();
		} else if( motd.getMotd() != null) {
			settings.saveGaeStamp(motd.getStamp());
			new AlertDialog.Builder(tolomet)
		    	.setTitle(R.string.motd)
		    	.setMessage(motd.getMotd())
		    	.create().show();			
		}				
	}
	
	private String getChanges( Motd motd  ) {
		StringWriter string = new StringWriter();
		PrintWriter writer = new PrintWriter(string);
		if( motd.getChanges() != null ) {
			writer.println(tolomet.getString(R.string.improvements)+" v"+motd.getVersion()+":");
			for( String str : motd.getChanges() )
				writer.println("* "+str);
		}
		writer.close();
		return string.toString();
	}
}
