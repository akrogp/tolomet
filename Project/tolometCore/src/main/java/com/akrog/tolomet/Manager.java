package com.akrog.tolomet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Locale;

public class Manager {
	private final String[] directions;
	private final String lang;
	
	public Manager() {
		this(Locale.getDefault().getLanguage());
	}
	
	public Manager( String lang) {
		this.lang = lang;
		directions = loadDirections();
	}		
	
	private String[] loadDirections() {
		BufferedReader rd = new BufferedReader(new InputStreamReader(getLocalizedResource("directions.csv")));
		try {
			return rd.readLine().split(",");
		} catch (IOException e) {
		} finally {
            try {
                rd.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
	}

	private InputStream getLocalizedResource( String name ) {
		InputStream is = getClass().getResourceAsStream(String.format("/res/%s", name.replaceAll("\\.", String.format("_%s.", lang.toLowerCase()))));
		if( is == null )
			is = getClass().getResourceAsStream(String.format("/res/%s", name));
		return is;
	}
	
	private InputStream getCountryResource( String country, String name ) {
		return getClass().getResourceAsStream(String.format("/res/%s", name.replaceAll("\\.", String.format("_%s.", country.toUpperCase()))));
	}

	public int getRefresh( Station station ) {
		if( !checkStation(station) )
			return 10;
		return station.getProvider().getRefresh(station.getCode());
	}
	
	public String getInforUrl( Station station ) {
		if( !checkStation(station) )
			return null;
		return station.getProvider().getInfoUrl(station.getCode());
	}

	public String getUserUrl( Station station ) {
		if( !checkStation(station) )
			return null;
		return station.getProvider().getUserUrl(station.getCode());
	}
	
	public boolean refresh( Station station ) {
		if( !checkStation(station) )
			return false;
		if( !station.isEmpty() && ((System.currentTimeMillis()-station.getStamp())/60000L < getRefresh(station)) )
			return false;
		try {
			station.getProvider().refresh(station);
		} catch( Exception e ) {
			//station.clear();
			return false;
		}
		return true;
	}

	public boolean travel( Station station, long date ) {
		if( !checkStation(station) )
			return false;
		try {
			return station.getProvider().travel(station, date);
		} catch( Exception e ) {
		}
		return false;
	}
	
	public void cancel( Station station ) {
		if( !checkStation(station) )
			return;
		station.getProvider().cancel();
	}

	public String getSummary( Station station, boolean large ) {
		return getSummary(station, null, large);
	}
	
	public String getSummary( Station station, Long stamp, boolean large ) {
		if( !checkStation(station) )
			return null;

		stamp = station.getMeteo().getStamp(stamp);
		if( stamp == null )
			return "";
		
		String strDir = null;
		Number dir = station.getMeteo().getWindDirection().getAt(stamp);
		if( dir != null )
			strDir = parseDirection(dir.intValue());
		Number med = station.getMeteo().getWindSpeedMed().getAt(stamp);
		Number max = station.getMeteo().getWindSpeedMax().getAt(stamp);
		Number hum = station.getMeteo().getAirHumidity().getAt(stamp);
		Number temp = station.getMeteo().getAirTemperature().getAt(stamp);

		StringBuilder str = new StringBuilder(getStamp(station,stamp));
		if( temp != null )
			str.append(String.format(" | %.1f ºC", temp));
		if( hum != null )
			str.append(String.format(" | %.0f %%", hum));
		if( strDir != null )
			str.append(String.format(" | %dº (%s)", dir.intValue(), strDir));
		if( med != null )
			str.append(String.format(" | %.1f", med));
		if( max != null )
			str.append(String.format("~%.1f", max));
		if( med != null || max != null )
			str.append(" km/h");

		return large ? str.toString() : str.toString().replaceAll(" ", "");
	}

	public String getStamp(Station station) {
		return getStamp(station, null);
	}
		
	public String getStamp(Station station, Long stamp) {
		if( !checkStation(station) )
			return null;
		if( stamp == null )
			stamp = station.getMeteo().getStamp();
		Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(stamp);
        return String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	}
	
	public boolean isOutdated(Station station) {
		if( station.isSpecial() || station.isEmpty() )
			return true;
		long stamp = station.getStamp();
		if( Calendar.getInstance().getTimeInMillis()-stamp > getRefresh(station)*60*1000)
			return true;
		return false;
	}
	
	public String parseDirection( int degrees ) {
        double deg = degrees + 11.25;
		while( deg >= 360.0 )
			deg -= 360.0;
		int index = (int)(deg/22.5);
		if( index < 0 )
			index = 0;
		else if( index >= 16 )
			index = 15;
		return directions[index];
	}
	
	public boolean checkStation(Station station) {
		return station != null && !station.isSpecial();
	}
}