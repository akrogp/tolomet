package com.akrog.tolomet;

import java.util.Calendar;

public interface WindProvider {
	public String getUrl( Station station, Calendar past, Calendar now );
	public void updateStation( Station station, String data );
	public int getRefresh();
}