package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;

import java.util.List;

public interface WindProvider {
	void refresh( Station station );
	boolean travel(Station station, long date );
	void cancel();
	int getRefresh( String code );
	String getInfoUrl( String code );
	String getUserUrl( String code );
	List<Station> downloadStations();
}