package com.akrog.tolomet.data;

import java.util.Calendar;
import java.util.TimeZone;

import android.content.Context;

public class WindProviderManager {
	public WindProviderManager( Context context ) {
		mProviders = new WindProvider[WindProviderType.values().length];
		mProviders[0] = new EuskalmetProvider( context );
		mProviders[1] = new MeteoNavarraProvider();
		mProviders[2] = new AemetProvider(); 
	}
	
	public String getUrl( Station station ) {
		return mProviders[station.Provider.getValue()].getUrl(station, mPast, mNow);
	}
	
	public String getInfoUrl( Station station ) {
		return mProviders[station.Provider.getValue()].getInfoUrl(station.Code);
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
		
		// First execution
		if( station.isEmpty() ) {
			mPast.set(Calendar.HOUR_OF_DAY,0);
			mPast.set(Calendar.MINUTE,0);
			return true;
		}
		
		// Check update interval
		mPast.setTimeInMillis((Long)station.ListDirection.get(station.ListDirection.size()-2));
		if( (mNow.getTimeInMillis()-mPast.getTimeInMillis()) <= 10*60*1000 )
			return false;
		
		// Clear cache
		if( mPast.get(Calendar.YEAR) == mNow.get(Calendar.YEAR) && mPast.get(Calendar.DAY_OF_YEAR) == mNow.get(Calendar.DAY_OF_YEAR) )
			return true;
		mPast.setTimeInMillis(mNow.getTimeInMillis());
		mPast.set(Calendar.HOUR_OF_DAY,0);
		mPast.set(Calendar.MINUTE, 0);
		mPast.set(Calendar.SECOND, 0);
		station.clear();			
		return true;
	}		
		
	private Calendar mNow, mPast;
	private WindProvider[] mProviders;
}
