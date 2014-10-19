package com.akrog.tolomet.data.providers;

import java.util.List;

import android.os.AsyncTask;

import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Downloader;
import com.akrog.tolomet.data.Station;
import com.akrog.tolomet.data.WindProvider;

public abstract class AbstractProvider implements WindProvider {
	public AbstractProvider( Tolomet tolomet ) {
		this.tolomet = tolomet;
	}

	public void cancelDownload() {
		if( this.downloader != null && this.downloader.getStatus() != AsyncTask.Status.FINISHED )
			this.downloader.cancel(true);
	}

	public void onCancelled() {
		this.tolomet.onCancelled();
	}

	public void onDownloaded(String result) {
		try {
			updateStation(result);
		} catch( Exception e ) {
			this.station.clear();
			e.printStackTrace();
		}
		this.tolomet.onDownloaded();
	}	
	
	protected abstract void updateStation(String data);
	
	protected void updateList(List<Number> list, Number date, Number val) {
		long stamp;
		int location = -2;
		boolean equal = false;
		for( int i = 0; i < list.size(); i+=2 ) {
			stamp = (Long)list.get(i);
			if( (Long)date >= stamp ) {
				equal = (Long)date == stamp ? true : false;
				location = i;				
			}
		}
		if( equal ) {
			list.remove(location+1);
			list.add(location+1,val);
		} else {
			list.add(location+2,date);
			list.add(location+3,val);
		}
	}

	protected Tolomet tolomet;
	protected Downloader downloader;
	protected Station station;
}
