package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;
import com.akrog.tolomet.io.Downloader;
import com.akrog.tolomet.utils.DateUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
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
			e.printStackTrace();
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
	public boolean travel(Station station, long date) {
		downloader = new Downloader();
		Calendar cal = Calendar.getInstance();
		DateUtils.resetDay(cal);
		if( !configureDownload(downloader, station, cal.getTimeInMillis()) )
			return false;
		String data = downloader.download();
		if( data == null )
			return false;
		try {
			updateStation(station, data);
		} catch( Exception e ) {
		}
		return true;
	}

	public abstract void configureDownload(Downloader downloader, Station station );

	public abstract boolean configureDownload(Downloader downloader, Station station, long date );
	
	public abstract void updateStation(Station station, String data) throws Exception;

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

	@Override
	public List<Station> downloadStations() {
		return null;
	}

	protected Downloader downloader;
	private final int defRefresh;
	private final Map<String, Integer> mapRefresh = new HashMap<String, Integer>();
}
