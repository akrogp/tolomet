package com.akrog.tolomet.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.akrog.tolomet.providers.WindProviderType;

import android.os.Bundle;

public class Station {		
	public List<Number> listDirection, listHumidity, listSpeedMed, listSpeedMax, listTemperature, listPressure;
	public String name, code;
	public int region;
	public double latitude, longitude;
	public WindProviderType provider;
	public boolean favorite;
	public int special;
	public float distance;
		
	public Station() {
		this("none","none",1,false,WindProviderType.Aemet,0,0);
	}
	
	public Station( String name, int special ) {
		this(name,"none",1,false,WindProviderType.Aemet,0,0);
		this.special = special;		
	}
	
	public Station( String str ) {		
		String[] fields = str.split(":");
    	this.code = fields[0];
    	this.name = fields[1];
    	this.provider = WindProviderType.values()[Integer.parseInt(fields[2])];
    	this.region = Integer.parseInt(fields[3]);    	
    	this.latitude = Double.parseDouble(fields[4]);
    	this.longitude = Double.parseDouble(fields[5]);
    	this.special = -1;
    	this.distance = -1.0F;
    	createArrays();    	
	}	
	
	public Station( String name, String code, int region, boolean favorite, WindProviderType provider, double lat, double lon ) {
		this.name = name;
		this.code = code;
		this.region = region;
		this.favorite = favorite;
		this.provider = provider;
		this.latitude = lat;
		this.longitude = lon;
		this.special = -1;
		this.distance = -1.0F;
		createArrays();
	}
	
	public Station( Station station ) {
		this( station.name, station.code, station.region, station.favorite, station.provider, station.latitude, station.longitude );
		replace(station);
	}
	
	public Station( Bundle bundle, String code, boolean fav ) {
		this();
		loadState(bundle, code, fav);
	}
	
	private void createArrays() {
		this.listDirection = new ArrayList<Number>();
		this.listHumidity = new ArrayList<Number>();
		this.listSpeedMed = new ArrayList<Number>();
		this.listSpeedMax = new ArrayList<Number>();
		this.listTemperature = new ArrayList<Number>();
		this.listPressure = new ArrayList<Number>();
	}	
	
	@Override
	public String toString() {
		if( isSpecial() )
			return this.name;
		//String str = this.code + " - " + this.name;
		String str = this.provider.getCode() + " - " + this.name;
		if( this.distance > 0.0F )
			str += " (" + String.format("%.1f",this.distance/1000.0F) + " km)";
		return str;
	}
	
	public void clear() {
		this.listDirection.clear();
		this.listHumidity.clear();
		this.listSpeedMed.clear();
		this.listSpeedMax.clear();
		this.listTemperature.clear();
		this.listPressure.clear();
	}
	
	public void add( Station station ) {
		if( station == null )
			return;
		this.listDirection.addAll(station.listDirection);
		this.listHumidity.addAll(station.listHumidity);
		this.listSpeedMed.addAll(station.listSpeedMed);
		this.listSpeedMax.addAll(station.listSpeedMax);
		this.listTemperature.addAll(station.listTemperature);
		this.listPressure.addAll(station.listPressure);
	}
	
	public void replace( Station station ) {
		clear();
		add(station);
		this.name = station.name;
		this.code = station.code;
		this.region = station.region;
		this.provider = station.provider;
		this.favorite = station.favorite;
		this.latitude = station.latitude;
		this.longitude = station.longitude;
		this.special = station.special;
	}
	
	public boolean isEmpty() {
		return this.listDirection == null || this.listDirection.size() < 2;
	}
	
	public boolean isSpecial() {
		return this.special != -1;
	}
	
	public Number getStamp() {
		if( isEmpty() )
			return null;
		return this.listDirection.get(this.listDirection.size()-2);
	}
	
	public boolean isOutdated() {
		if( isEmpty() )
			return true;
		long stamp = (Long)getStamp();
		if( Calendar.getInstance().getTimeInMillis()-stamp > 220*60*1000)
			return true;
		return false;
	}	
	
	public void saveState( Bundle outState ) {
		//outState.putString(this.code+"-name", this.name);
		//outState.putInt(this.code+"-region", this.region);
		//outState.putInt(this.code+"-type", this.provider.getValue());
		outState.putBoolean(this.code+"-star", this.favorite);
		//outState.putDouble(this.code+"-lat", this.latitude);
		//outState.putDouble(this.code+"-lon", this.longitude);
		saveLongArray(outState, "dirx", this.listDirection, 0);
		saveIntArray(outState, "diry", this.listDirection, 1);
		saveLongArray(outState, "humx", this.listHumidity, 0);
		saveFloatArray(outState, "humy", this.listHumidity, 1);
		saveLongArray(outState, "medx", this.listSpeedMed, 0);
		saveFloatArray(outState, "medy", this.listSpeedMed, 1);
		saveLongArray(outState, "maxx", this.listSpeedMax, 0);
		saveFloatArray(outState, "maxy", this.listSpeedMax, 1);
		saveLongArray(outState, "tempx", this.listTemperature, 0);
		saveFloatArray(outState, "tempy", this.listTemperature, 1);
		saveLongArray(outState, "presx", this.listPressure, 0);
		saveFloatArray(outState, "presy", this.listPressure, 1);
	}
	
	public boolean loadState( Bundle bundle, String code, boolean fav ) {
		if( bundle == null )
			return false;
		this.code = code;
		return loadState(bundle, fav);
	}
	
	public boolean loadState( Bundle bundle, boolean fav ) {
		if( bundle == null )
			return false;
		//this.name = bundle.getString(this.code+"-name");
		//this.region = bundle.getInt(this.code+"-region");
		//this.favorite = bundle.getBoolean(this.code+"-star");
		//this.provider = WindProviderType.values()[bundle.getInt(this.code+"-type")];
		//this.latitude = bundle.getDouble(this.code+"-lat");
		//this.longitude = bundle.getDouble(this.code+"-lon");
		this.favorite = fav;
		loadLongInt( bundle, "dirx", "diry", this.listDirection );
		loadLongFloat( bundle, "humx", "humy", this.listHumidity );
		loadLongFloat( bundle, "medx", "medy", this.listSpeedMed );
		loadLongFloat( bundle, "maxx", "maxy", this.listSpeedMax );
		loadLongFloat( bundle, "tempx", "tempy", this.listTemperature );
		loadLongFloat( bundle, "presx", "presy", this.listPressure );
		return true;
	}
	
	private void loadLongInt( Bundle bundle, String name1, String name2, List<Number> list ) {
		long[] x = bundle.getLongArray(this.code+"-"+name1);
		int[] y = bundle.getIntArray(this.code+"-"+name2);
		list.clear();
		if( x == null || y == null )
			return;
		for( int i = 0; i < x.length; i++ ) {
			list.add(x[i]);
			list.add(y[i]);
		}
	}

	private void loadLongFloat( Bundle bundle, String name1, String name2, List<Number> list ) {
		long[] x = bundle.getLongArray(this.code+"-"+name1);
		float[] y = bundle.getFloatArray(this.code+"-"+name2);
		list.clear();
		if( x == null || y == null )
			return;
		for( int i = 0; i < x.length; i++ ) {
			list.add(x[i]);
			list.add(y[i]);
		}
	}

	private void saveLongArray( Bundle outState, String name, List<Number> list, int off ) {
		int len = list.size()/2;
		long[] data = new long[len];
		int j = 0;
				
		for( int i = off; i < list.size(); i+= 2 )
			data[j++] = (Long)list.get(i);
		
		outState.putLongArray(this.code+"-"+name, data);
	}
	
	private void saveFloatArray( Bundle outState, String name, List<Number> list, int off ) {
		int len = list.size()/2;
		float[] data = new float[len];
		int j = 0;
				
		for( int i = off; i < list.size(); i+= 2 )
			data[j++] = (Float)list.get(i);
		
		outState.putFloatArray(this.code+"-"+name, data);
	}
	
	private void saveIntArray( Bundle outState, String name, List<Number> list, int off ) {
		int len = list.size()/2;
		int[] data = new int[len];
		int j = 0;
				
		for( int i = off; i < list.size(); i+= 2 )
			data[j++] = (Integer)list.get(i);
		
		outState.putIntArray(this.code+"-"+name, data);
	}
}