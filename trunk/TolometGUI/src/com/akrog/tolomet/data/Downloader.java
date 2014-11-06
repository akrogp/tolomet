package com.akrog.tolomet.data;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.widget.Toast;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;

public class Downloader extends AsyncTask<Void, Void, Void> {
	private Tolomet tolomet;
	private ProgressDialog progress;
	private Manager model;

	public Downloader( Tolomet tolomet, Manager model ) {
		this.tolomet = tolomet;
		this.model = model;
		progress = new ProgressDialog(this.tolomet);
		progress.setMessage( this.tolomet.getString(R.string.Downloading)+"..." );
        progress.setTitle( "" );//getString(R.string.Progress) );
        progress.setIndeterminate(true);
        progress.setCancelable(true);
        progress.setOnCancelListener(new OnCancelListener(){
        	public void onCancel(DialogInterface dialog) {
        		cancel(true);
        	}
        });
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();	        
        progress.show();
    }
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		//this.progress.dismiss();
		Toast.makeText(tolomet,tolomet.getString(R.string.DownloadCancelled),Toast.LENGTH_SHORT).show();
		tolomet.onCancelled();
	}	
	
	@Override
	protected Void doInBackground(Void... params) {
		model.refresh();
		return null;
	}
	
	@Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);	        
        progress.dismiss();
        tolomet.onDownloaded();
    }
}
