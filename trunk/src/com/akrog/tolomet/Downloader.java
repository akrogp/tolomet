package com.akrog.tolomet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.widget.Toast;

public class Downloader extends AsyncTask<String, Void, String> {
	private Tolomet mTolomet;
	private ProgressDialog mProgress;

	public Downloader( Tolomet tolomet ) {
		mTolomet = tolomet;
		mProgress = new ProgressDialog(mTolomet);
        mProgress.setMessage( mTolomet.getString(R.string.Downloading)+"..." );
        mProgress.setTitle( "" );//getString(R.string.Progress) );
        mProgress.setIndeterminate(true);
        mProgress.setCancelable(true);
        mProgress.setOnCancelListener(new OnCancelListener(){
        	public void onCancel(DialogInterface dialog) {
        		cancel(true);
        	}
        });
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();	        
        mProgress.show();
    }
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		//mProgress.dismiss();
		Toast.makeText(mTolomet,R.string.DownloadCancelled,Toast.LENGTH_SHORT).show();
		mTolomet.OnCancelled();
	}
	
	@Override
	protected String doInBackground(String... urls) {
		StringBuilder builder = new StringBuilder();
    	try {
    		URL url = new URL(urls[0]);
    		URLConnection con = url.openConnection();
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
        mProgress.dismiss();
        mTolomet.onDownloaded(result);
    }
}
