package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseProvider implements WindProvider {

	public BaseProvider( int defRefresh ) {
		this.defRefresh = defRefresh;
	}
	
	@Override
	public void refresh(Station station) {
		downloader = new Downloader();
		configureDownload(downloader, station);
		String data = downloader.download();
		if( data == null )
			return;
		try {
			updateStation(station, data);
		} catch( Exception e ) {			
		}
		Integer refresh = station.getMeteo().getStep();
		if( refresh != null ) {
            refresh = Math.min(refresh, defRefresh);
			Integer prev = mapRefresh.get(station.getCode());
			if( prev == null || prev > refresh )
				mapRefresh.put(station.getCode(), refresh);
		}
	}

	@Override
	public boolean getHistory(Station station, long date) {
		return false;
	}

	public abstract void configureDownload(Downloader downloader, Station station );
	
	public abstract void updateStation(Station station, String data);

	@Override
	public void cancel() {
		if( downloader != null )
			downloader.cancel();
	}

	@Override
	public int getRefresh(String code) {
		Integer refresh = mapRefresh.get(code);
		return refresh == null ? defRefresh : refresh;
	}

	private Downloader downloader;
	private final int defRefresh;
	private final Map<String, Integer> mapRefresh = new HashMap<String, Integer>();
}
