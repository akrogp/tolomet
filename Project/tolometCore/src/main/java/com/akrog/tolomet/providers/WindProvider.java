package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;

import java.util.List;

public interface WindProvider {
	void refresh( Station station );
	boolean travel(Station station, long date );
	void cancel();
	int getRefresh( String code );
	String getInfoUrl( Station sta );
	String getUserUrl( Station sta );
	List<Station> downloadStations();
}