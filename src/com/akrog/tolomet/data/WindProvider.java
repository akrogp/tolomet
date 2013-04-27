package com.akrog.tolomet.data;

import java.util.Calendar;



public interface WindProvider {
	//public String getUrl( Station station, Calendar past, Calendar now );
	public void download( Station station, Calendar past, Calendar now );
	public void cancelDownload();
	public void updateStation( Station station, String data );
	public int getRefresh();
	public String getInfoUrl( String code );
}