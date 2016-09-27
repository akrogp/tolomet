package com.akrog.tolomet;

import com.akrog.tolomet.providers.WindProviderType;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Manager {
	private Station currentStation;
	private final List<Station> countryStations = new ArrayList<Station>();
	private final List<Station> selStations = new ArrayList<Station>();
	private final List<Region> regions = new ArrayList<Region>();
	private final List<Country> countries = new ArrayList<Country>();
	private String[] directions;
	private final String lang;
	private String country;
	private Map<String,Station> mapStations;
	
	public Manager() {
		this(Locale.getDefault().getLanguage(), Locale.getDefault().getCountry(), true);
	}
	
	public Manager( String lang, String country, boolean filterBroken ) {
		this.lang = lang;
		this.country = country;
		loadStations();
		loadDirections();
		loadRegions();
		loadCountries();
	}		
	
	private void loadDirections() {
		BufferedReader rd = new BufferedReader(new InputStreamReader(getLocalizedResource("directions.csv")));
		try {
			directions = rd.readLine().split(",");
			rd.close();
		} catch (IOException e) {
		}		
	}

	private void loadStations() {
		mapStations = null;
		countryStations.clear();
		Station station;
		DataInputStream dis = null;
		try {
			dis = new DataInputStream(getCountryResource("stations.dat"));
			//dis = new DataInputStream(new FileInputStream("/home/gorka/MyProjects/Android/Tolomet/Docs/stations.world.dat"));
			while( true ) {
				station = new Station();
				station.setCode(dis.readUTF());
				station.setName(dis.readUTF());
				station.setProviderType(WindProviderType.values()[dis.readInt()]);
				station.setCountry(dis.readUTF());
				station.setRegion(dis.readInt());
				station.setLatitude(dis.readDouble());
				station.setLongitude(dis.readDouble());				
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
		countries.clear();
		BufferedReader rd = new BufferedReader(new InputStreamReader(getLocalizedResource("countries.csv")));
		String line, code;
		String[] fields;
		try {
			while( (line=rd.readLine()) != null ) {
				fields = line.split("\\t");
				code = fields[0];
				if(getClass().getResource(String.format("/res/stations_%s.dat", code)) == null )
					continue;
				Country country = new Country();
				country.setCode(code);
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

	public Station findStation( String id ) {
		if( mapStations == null ) {
			mapStations = new HashMap<>(countryStations.size());
			for( Station station : countryStations )
				mapStations.put(station.getId(), station);
		}
		return mapStations.get(id);
	}

	public Station findStation( WindProviderType type, String code ) {
		return findStation(Station.buildId(type,code));
	}

	public void selectAll() {
		selStations.clear();
		selStations.addAll(countryStations);
	}
	
	public void selectNone() {
		selStations.clear();
	}
	
	public void setCountry( String code ) {
		if( code.equals(country) )
			return;
		selStations.clear();
		country = code;
		loadStations();
		loadRegions();
	}

	public String getCountry() {
		return country;
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
		for( Station station : countryStations )
			if( station.isFavorite() )
				selStations.add(station);
	}
	
	public void selectNearest() {
		selStations.clear();
		for( Station station : countryStations )
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
		return countryStations;
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

	public String getUserUrl() {
		if( !checkCurrent() )
			return null;
		return currentStation.getProvider().getUserUrl(currentStation.getCode());
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
		return getSummary(null, large);
	}
	
	public String getSummary( Long stamp, boolean large ) {
		if( !checkCurrent() )
			return null;

		stamp = currentStation.getMeteo().getStamp(stamp);
		if( stamp == null )
			return "";
		
		String strDir = null;
		Number dir = currentStation.getMeteo().getWindDirection().getAt(stamp);
		if( dir != null )
			strDir = parseDirection(dir.intValue());
		Number med = currentStation.getMeteo().getWindSpeedMed().getAt(stamp);
		Number max = currentStation.getMeteo().getWindSpeedMax().getAt(stamp);
		Number hum = currentStation.getMeteo().getAirHumidity().getAt(stamp);
		Number temp = currentStation.getMeteo().getAirTemperature().getAt(stamp);

		StringBuilder str = new StringBuilder(getStamp(stamp));
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

	private Number findClosest(Measurement meas, Long stamp) {
		Number closest = meas.getLast();
		if( stamp == null )
			return closest;
		long diff = Math.abs(stamp-meas.getStamp());
		long tmp;
		for( int i = 0; i < meas.size(); i++ ) {
			tmp = Math.abs(stamp - meas.getTimes()[i]);
			if( tmp < diff ) {
				diff = tmp;
				closest = meas.getValues()[i];
			}
		}
		return closest;
	}

	public String getStamp() {
		return getStamp(null);
	}
		
	public String getStamp(Long stamp) {
		if( !checkCurrent() )
			return null;
		if( stamp == null )
			stamp = currentStation.getMeteo().getStamp();
		Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(stamp);
        return String.format("%02d:%02d", cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
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