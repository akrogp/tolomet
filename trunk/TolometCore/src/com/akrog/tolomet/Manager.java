package com.akrog.tolomet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.akrog.tolomet.providers.WindProviderType;

public class Manager {
	private Station currentStation;
	private final List<Station> allStations = new ArrayList<Station>();
	private final List<Station> selStations = new ArrayList<Station>();
	private final List<Region> regions = new ArrayList<Region>();
	
	public Manager() {
		loadStations();
		loadRegions();
		setCurrentStation(allStations.get(0));	// just for first tests
	}	

	private void loadStations() {
		BufferedReader rd = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/res/stations.csv")));
		String line;
		String[] fields;
		Station station;
		try {
			while( (line=rd.readLine()) != null ) {
				station = new Station();
				fields = line.split(":");
				station.setCode(fields[0]);
				station.setName(fields[1]);
				station.setProviderType(WindProviderType.values()[Integer.parseInt(fields[2])]);
				station.setRegion(Integer.parseInt(fields[3]));
				station.setLatitude(Double.parseDouble(fields[4]));
				station.setLongitude(Double.parseDouble(fields[5]));
				allStations.add(station);
			}
			rd.close();
		} catch( Exception e ) {
		}
    }
	
	private void loadRegions() {
		BufferedReader rd = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/res/regions.csv")));
		String line;
		String[] fields;
		try {
			while( (line=rd.readLine()) != null ) {
				fields = line.split(",");
				Region region = new Region();
				region.setName(fields[0]);
				region.setCode(Integer.parseInt(fields[1]));
				regions.add(region);
			}
		} catch (IOException e) {}
	}
	
	public void selectAll() {
		selStations.clear();
		selStations.addAll(allStations);
	}
	
	public void selectNone() {
		selStations.clear();
	}
	
	public void selectRegion( int code ) {
		selStations.clear();
		for( Station station : allStations )
			if( station.getRegion() == code )
				selStations.add(station);
	}
	
	public void selectVowel( char vowel ) {
		selStations.clear();
		for( Station station : allStations )
			if( station.getName().charAt(0) == vowel )
				selStations.add(station);
	}
	
	public void selectFavorites() {
		selStations.clear();
		for( Station station : allStations )
			if( station.isFavorite() )
				selStations.add(station);
	}
	
	public void selectNearest() {
		selStations.clear();
		for( Station station : allStations )
			if( station.getDistance() < 50000.0F )
				selStations.add(station);
		Collections.sort(selStations, new Comparator<Station>() {
			@Override
			public int compare(Station s1, Station s2) {
				return (int)Math.signum(s1.getDistance()-s2.getDistance());
			}
		});
	}
	
	public void setCurrentStation( Station station ) {
		currentStation = station;
	}

	public Station getCurrentStation() {
		return currentStation;
	}

	public List<Station> getAllStations() {
		return allStations;
	}

	public List<Region> getRegions() {
		return regions;
	}

	public List<Station> getSelStations() {
		return selStations;
	}
	
	public int getRefresh() {
		if( !checkCurrent() )
			return 10;
		return currentStation.getProvider().getRefresh(currentStation.getCode());
	}
	
	public String getInforUrl() {
		if( !checkCurrent() )
			return null;
		return currentStation.getProvider().getInfoUrl(currentStation.getCode());
	}
	
	public boolean refresh() {
		if( !checkCurrent() )
			return false;
		if( !currentStation.isEmpty() && ((System.currentTimeMillis()-currentStation.getStamp())/60000L < getRefresh()) )
			return false;
		try {
			currentStation.getProvider().refresh(currentStation);
		} catch( Exception e ) {
			currentStation.clear();
			return false;
		}
		return true;
	}
	
	public void cancel() {
		if( !checkCurrent() )
			return;
		currentStation.getProvider().cancel();
	}
	
	public String getSummary( boolean large ) {
		if( !checkCurrent() )
			return null;
				
		int dir = currentStation.getMeteo().getWindDirection().getLast().intValue();
		String strDir = parseDirection(dir);
		float med = currentStation.getMeteo().getWindSpeedMed().getLast().floatValue();
		float max = currentStation.getMeteo().getWindSpeedMax().getLast().floatValue();
		Number hum = currentStation.getMeteo().getAirHumidity().getLast();
		Number temp = currentStation.getMeteo().getAirTemperature().getLast();
		
		StringBuilder str = new StringBuilder(getStamp());
		if( large ) {
			str.append(String.format(" | %dº (%s) | ", dir, strDir));
			if( hum != null )
				str.append(String.format("%.0f %% | ", hum));
			if( temp != null )
				str.append(String.format("%.1f ºC | ", temp));
			str.append(String.format(" %.1f~%.1f km/h", med, max));
		} else {
			str.append(String.format("|%dº(%s)|", dir, strDir));
			if( hum != null )
				str.append(String.format("%.0f%%|", hum));
			if( temp != null )
				str.append(String.format("%.1fºC|", temp));
			str.append(String.format("%.1f~%.1f", med, max));
		}
		return str.toString();
	}
		
	public String getStamp() {
		if( !checkCurrent() )
			return null;
		
		Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentStation.getMeteo().getWindDirection().getStamp());
        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("HH:mm");
        return df.format(cal.getTime());
	}
	
	public boolean isOutdated() {
		if( currentStation.isSpecial() || currentStation.isEmpty() )
			return true;
		long stamp = currentStation.getStamp();
		if( Calendar.getInstance().getTimeInMillis()-stamp > getRefresh()*60*1000)
			return true;
		return false;
	}
	
	public static String parseDirection( int degrees ) {
		String[] vals = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        double deg = degrees + 11.25;
		while( deg >= 360.0 )
			deg -= 360.0;
		int index = (int)(deg/22.5);
		if( index < 0 )
			index = 0;
		else if( index >= 16 )
			index = 15;
		return vals[index];
	}
	
	public boolean checkCurrent() {
		return currentStation != null && !currentStation.isSpecial();
	}
}