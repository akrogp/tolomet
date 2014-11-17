package com.akrog.tolomet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import com.akrog.tolomet.providers.WindProviderType;

public class Manager {
	private Station currentStation;
	private final List<Station> allStations = new ArrayList<>();
	private final List<Station> selStations = new ArrayList<>();
	private final List<Region> regions = new ArrayList<>();
	
	public Manager() {
		loadStations();
		loadRegions();
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
		selStations.sort(new Comparator<Station>() {
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
			return 0;
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
		currentStation.getProvider().refresh(currentStation);
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
		
		Number hum = currentStation.getMeteo().getAirHumidity().getLast();
		String date = getStamp();
		int dir = currentStation.getMeteo().getWindDirection().getLast().intValue();
		String strDir = parseDirection(dir);
		float med = currentStation.getMeteo().getWindSpeedMed().getLast().floatValue();
		float max = currentStation.getMeteo().getWindSpeedMax().getLast().floatValue();
		if( hum == null )
			return String.format("%s | %dº (%s) | %.1f~%.1f km/h", date, dir, strDir, med, max );
		if( large )
    		return String.format("%s | %dº (%s) | %.0f %% | %.1f~%.1f km/h", date, dir, strDir, hum, med, max );
    	return String.format("%s|%dº(%s)|%.0f%%|%.1f~%.1f", date, dir, strDir, hum, med, max );
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
	
	private boolean checkCurrent() {
		return currentStation != null && !currentStation.isSpecial();
	}
}