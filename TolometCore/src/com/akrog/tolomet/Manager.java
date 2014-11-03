package com.akrog.tolomet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
	
	public void selectClose() {
		selStations.clear();
		for( Station station : allStations )
			if( station.getDistance() < 50000.0F )
				selStations.add(station);
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
		return currentStation.getProvider().getRefresh(currentStation.getCode());
	}
	
	public String getInforUrl() {
		return currentStation.getProvider().getInfoUrl(currentStation.getCode());
	}
	
	public boolean refresh() {
		if( !currentStation.isEmpty() && ((System.currentTimeMillis()-currentStation.getStamp())/60000L < getRefresh()) )
			return false;
		currentStation.getProvider().refresh(currentStation);
		return true;
	}
	
	public void cancel() {
		currentStation.getProvider().cancel();
	}
}
