package com.akrog.tolomet.providers;

import com.akrog.tolomet.Station;

public interface WindProvider {
	public void refresh( Station station );
	public void cancel();
	public int getRefresh( String code );
	public String getInfoUrl( String code );
}