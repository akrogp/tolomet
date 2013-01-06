package com.akrog.tolomet;

import java.util.Calendar;
import java.util.TimeZone;

public class WindProviderManager {
	public WindProviderManager() {
		mProviders = new WindProvider[WindProviderType.values().length];
		mProviders[0] = new EuskalmetProvider();
		mProviders[1] = new MeteoNavarraProvider(); 
	}
	
	public String getUrl( Station station ) {
		return mProviders[station.Provider.getValue()].getUrl(station, mPast, mNow);
	}
	
	public void updateStation( Station station, String data ) {
		mProviders[station.Provider.getValue()].updateStation(station, data);
	}
	
	public int getRefresh( Station station ) {
		return mProviders[station.Provider.getValue()].getRefresh();
	}

	public boolean updateTimes( Station station ) {
		mNow = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		mPast = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		if( station.ListDirection.size() < 2 ) {
			mPast.set(Calendar.HOUR_OF_DAY,0);
			mPast.set(Calendar.MINUTE,0);
			return true;
		}		
		mPast.setTimeInMillis((Long)station.ListDirection.get(station.ListDirection.size()-2));
		if( mPast.get(Calendar.DAY_OF_MONTH) == mNow.get(Calendar.DAY_OF_MONTH) )
			if( (mNow.getTimeInMillis()-mPast.getTimeInMillis()) <= 10*60*1000 )
				return false;
		return true;
	}		
		
	private Calendar mNow, mPast;
	private WindProvider[] mProviders;
}
