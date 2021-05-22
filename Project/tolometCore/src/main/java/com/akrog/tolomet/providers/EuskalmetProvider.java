package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;

import java.util.List;

public class EuskalmetProvider implements WindProvider {
	
	@Override
	public String getInfoUrl(String code) {
		return origProvider.getInfoUrl(code);
	}

	@Override
	public String getUserUrl(String code) {
		return newProvider.getUserUrl(code);
	}

	@Override
	public List<Station> downloadStations() {
		return newProvider.downloadStations();
	}

	@Override
	public void refresh(Station station) {
		provider = origProvider;
		provider.refresh(station);
		if( station.isEmpty() ) {
			provider = newProvider;
			provider.refresh(station);
		}
	}

	@Override
	public boolean travel(Station station, long date) {
		provider = origProvider;
        provider.travel(station, date);
        if( station.isEmpty() ) {
			provider = newProvider;
			provider.travel(station, date);
		}
		return true;
	}

	@Override
	public void cancel() {
		provider.cancel();
	}

	@Override
	public int getRefresh(String code) {
		return 10;
	}

	private final EuskalmetProviderNew newProvider = new EuskalmetProviderNew();
	private final EuskalmetProviderOrig origProvider = new EuskalmetProviderOrig();
	private WindProvider provider;
}
