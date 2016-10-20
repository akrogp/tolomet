package com.akrog.tolomet.presenters;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akrog.tolomet.ChartsActivity;
import com.akrog.tolomet.Model;
import com.akrog.tolomet.R;

public class Downloader extends AsyncTask<Void, Void, Void> {
	private ChartsActivity tolomet;
	private ProgressBar progressBar;
	private final Model model = Model.getInstance();

	public Downloader(ChartsActivity tolomet) {
		this.tolomet = tolomet;
		progressBar = tolomet.getProgressBar();
	}
	
	@Override
    protected void onPreExecute() {
        super.onPreExecute();
		progressBar.setIndeterminate(true);
		progressBar.setVisibility(View.VISIBLE);
    }
	
	@Override
	protected void onCancelled() {
		super.onCancelled();
		progressBar.setVisibility(View.GONE);
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
        try {
        	progressBar.setVisibility(View.GONE);
        } catch( Exception e ) {}
        tolomet.onDownloaded();
    }
}
