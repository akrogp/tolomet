package com.akrog.tolomet;

import java.util.Calendar;

import com.akrog.tolomet.providers.WindProvider;
import com.akrog.tolomet.providers.WindProviderType;


public class Station {		
	private final Meteo meteo = new Meteo();
	private String name, code;
	private int region;
	private double latitude, longitude;
	private WindProviderType providerType;
	private boolean favorite;
	private int special;
	private float distance;
		
	public Station() {
		this("none","none",1,false,WindProviderType.Aemet,0,0);
	}
	
	public Station( String name, int special ) {
		this(name,"none",1,false,WindProviderType.Aemet,0,0);
		this.special = special;		
	}
	
	public Station( String name, String code, int region, boolean favorite, WindProviderType provider, double lat, double lon ) {
		this.name = name;
		this.code = code;
		this.region = region;
		this.favorite = favorite;
		this.providerType = provider;
		this.latitude = lat;
		this.longitude = lon;
		this.special = -1;
		this.distance = -1.0F;
	}
	
	public Station( Station station ) {
		this( station.name, station.code, station.region, station.favorite, station.providerType, station.latitude, station.longitude );
		replace(station);
	}
	
	@Override
	public String toString() {
		if( isSpecial() )
			return this.name;
		//String str = this.code + " - " + this.name;
		String str = this.providerType.getCode() + " - " + this.name;
		if( this.distance > 0.0F )
			str += " (" + String.format("%.1f",this.distance/1000.0F) + " km)";
		return str;
	}
	
	public void clear() {
		meteo.clear();
	}
	
	public void merge( Station station ) {
		if( station == null || station.isSpecial() )
			return;
		meteo.merge(station.getMeteo());
	}
	
	public void replace( Station station ) {
		clear();
		merge(station);
		this.name = station.name;
		this.code = station.code;
		this.region = station.region;
		this.providerType = station.providerType;
		this.favorite = station.favorite;
		this.latitude = station.latitude;
		this.longitude = station.longitude;
		this.special = station.special;
	}
	
	public boolean isEmpty() {
		return meteo.isEmpty();
	}
	
	public boolean isSpecial() {
		return this.special != -1;
	}
	
	public Long getStamp() {
		return meteo.getStamp();
	}
	
	public boolean isOutdated() {
		if( isEmpty() )
			return true;
		long stamp = getStamp();
		if( Calendar.getInstance().getTimeInMillis()-stamp > 220*60*1000)
			return true;
		return false;
	}
	
	public Meteo getMeteo() {
		return meteo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getRegion() {
		return region;
	}

	public void setRegion(int region) {
		this.region = region;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public WindProviderType getProviderType() {
		return providerType;
	}

	public void setProviderType(WindProviderType provider) {
		this.providerType = provider;
	}
	
	public WindProvider getProvider() {
		return providerType.getProvider();
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public int getSpecial() {
		return special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public float getDistance() {
		return distance;
	}

	public void setDistance(float distance) {
		this.distance = distance;
	}
}