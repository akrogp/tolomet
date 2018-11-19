package com.akrog.tolomet.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.AsyncTaskCompat;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.viewmodel.AppSettings;
import com.akrog.tolomet.viewmodel.DbMeteo;
import com.akrog.tolomet.gae.Updater;
import com.akrog.tolomet.ui.presenters.MyCharts;
import com.akrog.tolomet.ui.presenters.MySummary;
import com.akrog.tolomet.ui.presenters.Presenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChartsActivity extends ToolbarActivity {
	public static final String PATH = "chart";
	
	// Creation and state
	
	@Override
    public void onCreate(Bundle savedInstanceState) {		
        super.onCreate(savedInstanceState);

        createSpinnerView(savedInstanceState, R.layout.activity_tolomet,
				R.id.favorite_item, R.id.refresh_item,
				R.id.info_item, R.id.map_item, R.id.origin_item,
				R.id.share_item, //R.id.whatsapp_item,
				R.id.fly_item,
				R.id.help_item, R.id.settings_item, R.id.update_item, R.id.about_item, R.id.report_item);

        updater.initialize(this);
        presenters.add(charts);
        presenters.add(summary);
        for( Presenter presenter : presenters)
        	presenter.initialize(this, savedInstanceState);

        createTimer();
    }
        
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	for( Presenter presenter : presenters)
    		presenter.save(outState);
    }

    @Override
    protected void onStop() {
        cancelTimer();
        if (thread != null) {
            model.cancel();
            thread.cancel(true);
            thread = null;
        }
        super.onStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if( intent != null )
            setIntent(intent);
        receiveDynamicLinks();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
        if( model.checkStation() )
            DbMeteo.getInstance().refresh(model.getCurrentStation());
    	if( settings.getUpdateMode() >= AppSettings.SMART_UPDATES && model.isOutdated() )
    		downloadData();
		redraw();
    }
    
    private void createTimer() {
    	cancelTimer();
    	if( settings.getUpdateMode() != AppSettings.AUTO_UPDATES )
    		return;	
    	timer = new Runnable() {				
    		@Override
    		public void run() {
    			if( model.checkStation() )
    				downloadData();					
    		}
    	};
    	//timer.run();
    }
    
    private void cancelTimer() {
    	if( timer != null ) {
    		handler.removeCallbacks(timer);
    		timer = null;
    	}
    }
    
    private boolean postTimer() {
    	if( timer == null || settings.getUpdateMode() != AppSettings.AUTO_UPDATES )
    		return false;
    	handler.removeCallbacks(timer);
    	int minutes = 1;
    	if( model.checkStation() && !model.getCurrentStation().isEmpty() ) {
			int dif = (int)((System.currentTimeMillis()-model.getCurrentStation().getStamp())/60/1000L);
			minutes = dif >= model.getRefresh() ? 1 : model.getRefresh()-dif;
		}
    	handler.postDelayed(timer, minutes*60*1000);
    	return true;
    }
    
    // Actions

	@Override
    public void redraw() {
		super.redraw();
    	for( Presenter presenter : presenters)
    		presenter.updateView();
    }
    
    private void downloadData() {
		if (thread != null)
			return;
		if (alertNetwork()) {
            model.loadCache();
            return;
        }
        if( !beginProgress() )
            return;
        thread = new AsyncTask<Void, Void, Station>() {
            @Override
            protected Station doInBackground(Void... params) {
                return model.safeRefresh();
            }
            @Override
            protected void onPostExecute(Station station) {
                super.onPostExecute(station);
                endProgress();
				if( station != null )
					model.getCurrentStation().getMeteo().merge(station.getMeteo());
                onDownloaded();
            }
            @Override
            protected void onCancelled() {
                super.onCancelled();
				//logFile("onCancelled1");
                onPostExecute(null);
				//logFile("onCancelled2");
            }
        };
        AsyncTaskCompat.executeParallel(thread);
	}

	/*private void logFile(String msg) {
		try {
			File file = new File(Environment.getExternalStoragePublicDirectory(
					Environment.DIRECTORY_DOCUMENTS), "Tolomet.txt");
			PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
			DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			pw.println(String.format("%s %s",df.format(new Date()),msg));
			pw.close();
		} catch (Exception e) {}
	}*/

	@Override
    public void onRefresh() {
    	if( !model.isOutdated() ) {
    		if( charts.getZoomed() )
    			charts.updateView();
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

	@Override
	public void onCancel() {
        super.onCancel();
        if( thread == null )
            return;
        model.cancel();
        thread.cancel(true);
        postTimer();
        redraw();
	}

	@Override
	public void onBrowser() {
	}

	@Override
	public void onSettingsChanged() {
		for( Presenter presenter : presenters )
		    presenter.onSettingsChanged();
		createTimer();
		redraw();
	}

	@Override
	public void onSelected(Station station) {
		redraw();		
		if( station.isSpecial() )
			return;
		if( settings.getUpdateMode() >= AppSettings.SMART_UPDATES && model.isOutdated() )
			downloadData();
	}

	@Override
	public String getScreenShotSubject() {
		return getString(R.string.ShareSubject);
	}

	@Override
	public String getScreenShotText() {
		return String.format("%s %s%s",
				getString(R.string.ShareTextPre), model.getCurrentStation().getName(), getString(R.string.ShareTextPost));
	}

	@Override
	public String getRelativeLink() {
		return String.format("%s/%s", PATH, model.getCurrentStation().getId());
	}

	public void onDownloaded() {
		thread = null;
        if( isStopped() )
            return;
		if( !postTimer() && model.checkStation() && model.getCurrentStation().isEmpty() ) {
			//logFile("No data => station:" + model.getCurrentStation().getId() + " empty:" + model.getCurrentStation().isEmpty());
            new AlertDialog.Builder(this).setTitle(R.string.NoData)
                    .setMessage(R.string.RedirectWeb)
                    .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(new Intent(ChartsActivity.this, ProviderActivity.class));
                        }
                    })
                    .setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            model.getCurrentStation().getMeteo().clear(cal.getTimeInMillis());
        }
        redraw();
        updater.start();
    }

	@Override
	public boolean beginProgress() {
		if( !super.beginProgress() )
            return false;
		for( Presenter presenter : presenters)
			presenter.setEnabled(false);
		return true;
	}

	@Override
	public boolean endProgress() {
        if( !super.endProgress() )
            return false;
		for( Presenter presenter : presenters)
			presenter.setEnabled(true);
		return true;
	}

	// Fields
	private final List<Presenter> presenters = new ArrayList<Presenter>();
	private final MySummary summary = new MySummary();
	private final MyCharts charts = new MyCharts(summary);
	private final Updater updater = new Updater();
	private final Handler handler = new Handler();
	private Runnable timer;
    private AsyncTask<Void, Void, Station> thread;
}