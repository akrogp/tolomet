package com.akrog.tolomet.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.widget.Toast;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;

public class Downloader extends AsyncTask<String, Void, String> {
	private Tolomet tolomet;
	private ProgressDialog progress;

	public Downloader( Tolomet tolomet ) {
		this.tolomet = tolomet;
		this.progress = new ProgressDialog(this.tolomet);
        this.progress.setMessage( this.tolomet.getString(R.string.Downloading)+"..." );
        this.progress.setTitle( "" );//getString(R.string.Progress) );
        this.progress.setIndeterminate(true);
        this.progress.setCancelable(true);
        this.progress.setOnCancelListener(new OnCancelListener(){
        	public void onCancel(DialogInterface dialog) {
        		cancel(true);
        	}
        });
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();	        
        this.progress.show();
    }
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		//this.progress.dismiss();
		Toast.makeText(this.tolomet,R.string.DownloadCancelled,Toast.LENGTH_SHORT).show();
		this.tolomet.OnCancelled();
	}
	
	@Override
	protected String doInBackground(String... urls) {
		StringBuilder builder = new StringBuilder();
    	try {
    		URL url = new URL(urls[0]);
    		URLConnection con = url.openConnection();
    		//con.setRequestProperty("User-Agent","Mozilla/5.0 (Linux)");
    		BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
    		String line;
    		while( (line=rd.readLine()) != null && !isCancelled() )
    			builder.append(line);
    		rd.close();
    	} catch( Exception e ) {
    		System.out.println(e.getMessage());
    		onCancelled();
		}
    	return builder.toString();
	}		
	
	@Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);	        
        this.progress.dismiss();
        this.tolomet.onDownloaded(result);
    }
}
