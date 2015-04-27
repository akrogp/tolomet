package com.akrog.tolomet;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import com.akrog.tolomet.providers.WindProviderType;

public class Manager {
	private Station currentStation;
	private final List<Station> allStations = new ArrayList<Station>();
	private final List<Station> countryStations = new ArrayList<Station>();
	private final List<Station> selStations = new ArrayList<Station>();
	private final List<Region> regions = new ArrayList<Region>();
	private final List<Country> countries = new ArrayList<Country>();
	private String[] directions;
	private Boolean filterBroken;
	private final String lang;
	private String country;
	
	public Manager() {
		this(Locale.getDefault().getLanguage(), Locale.getDefault().getCountry(), true);
	}
	
	public Manager( String lang, String country, boolean filterBroken ) {
		this.lang = lang;
		this.country = country;
		filterBrokenStations(filterBroken);
		loadDirections();
		loadRegions();
		loadCountries();
	}		

	public void filterBrokenStations( boolean filter ) {
		if( filterBroken != null && filterBroken.equals(filter) )
			return;
		allStations.clear();
		countryStations.clear();
		selStations.clear();
		currentStation = null;
		loadStations("/res/stations.dat");
		if( !filter )
			loadStations("/res/stations-ko.dat");
	}
	
	private void loadDirections() {
		BufferedReader rd = new BufferedReader(new InputStreamReader(getLocalizedResource("directions.csv")));
		try {
			directions = rd.readLine().split(",");
			rd.close();
		} catch (IOException e) {
		}		
	}

	private void loadStations( String path ) {
		DataInputStream dis = new DataInputStream(getClass().getResourceAsStream(path));
		Station station;
		try {
			while( true ) {
				station = new Station();
				station.setCode(dis.readUTF());
				station.setName(dis.readUTF());
				station.setProviderType(WindProviderType.values()[dis.readInt()]);
				station.setCountry(dis.readUTF());
				station.setRegion(dis.readInt());
				station.setLatitude(dis.readDouble());
				station.setLongitude(dis.readDouble());				
				allStations.add(station);
				if( station.getCountry().equals(country) )
					countryStations.add(station);
			}		
		} catch( Exception e ) {
			if( dis != null )
				try {
					dis.close();
				} catch (IOException e1) {
				}
		}
    }
	
	private void loadRegions() {
		regions.clear();
		InputStream is = getCountryResource("regions.csv");
		if( is == null )
			return;
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
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
			rd.close();
		} catch (IOException e) {}
		Collections.sort(regions, new Comparator<Region>() {
			@Override
			public int compare(Region o1, Region o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
	}
	
	private void loadCountries() {
		BufferedReader rd = new BufferedReader(new InputStreamReader(getLocalizedResource("countries.csv")));
		String line;
		String[] fields;
		try {
			while( (line=rd.readLine()) != null ) {
				fields = line.split("\\t");
				Country country = new Country();
				country.setCode(fields[0]);
				country.setName(fields[1]);
				countries.add(country);
			}
			rd.close();
		} catch (IOException e) {}
		Collections.sort(countries, new Comparator<Country>() {
			@Override
			public int compare(Country o1, Country o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
	}
	
	private InputStream getLocalizedResource( String name ) {
		InputStream is = getClass().getResourceAsStream(String.format("/res/%s", name.replaceAll("\\.", String.format("_%s.", lang.toLowerCase()))));
		if( is == null )
			is = getClass().getResourceAsStream(String.format("/res/%s", name));
		return is;
	}
	
	private InputStream getCountryResource( String name ) {
		return getClass().getResourceAsStream(String.format("/res/%s", name.replaceAll("\\.", String.format("_%s.", country.toUpperCase()))));
	}
	
	public void selectAll() {
		selStations.clear();
		selStations.addAll(countryStations);
	}
	
	public void selectNone() {
		selStations.clear();
	}
	
	public void setCountry( String code ) {
		selStations.clear();
		countryStations.clear();
		for( Station station : allStations )
			if( station.getCountry().equalsIgnoreCase(code) )
				countryStations.add(station);
		country = code;
		loadRegions();
	}
	
	public void selectRegion( int code ) {
		selStations.clear();
		for( Station station : countryStations )
			if( station.getRegion() == code )
				selStations.add(station);
	}
	
	public void selectVowel( char vowel ) {
		selStations.clear();
		for( Station station : countryStations )
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
	
	public List<Country> getCountries() {
		return countries;
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
		
		String strDir = null;
		Number dir = currentStation.getMeteo().getWindDirection().getLast();
		if( dir != null )
			strDir = parseDirection(dir.intValue());
		Number med = currentStation.getMeteo().getWindSpeedMed().getLast();
		Number max = currentStation.getMeteo().getWindSpeedMax().getLast();
		Number hum = currentStation.getMeteo().getAirHumidity().getLast();
		Number temp = currentStation.getMeteo().getAirTemperature().getLast();
		
		StringBuilder str = new StringBuilder(getStamp());
		if( strDir != null )
			str.append(String.format(" | %dº (%s)", dir.intValue(), strDir));
		if( hum != null )
			str.append(String.format(" | %.0f %%", hum));
		if( temp != null )
			str.append(String.format(" | %.1f ºC", temp));
		if( med != null )
			str.append(String.format(" | %.1f", med));
		if( max != null )
			str.append(String.format("~%.1f", max));
		if( med != null || max != null )
			str.append(" km/h");

		return large ? str.toString() : str.toString().replaceAll(" ", "");
	}
		
	public String getStamp() {
		if( !checkCurrent() )
			return null;
		
		Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(currentStation.getMeteo().getStamp());
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
	
	public boolean checkCurrent() {
		return currentStation != null && !currentStation.isSpecial();
	}
}