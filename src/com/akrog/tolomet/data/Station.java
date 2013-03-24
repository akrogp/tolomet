package com.akrog.tolomet.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;

public class Station {		
	public List<Number> ListDirection, ListHumidity, ListSpeedMed, ListSpeedMax;
	public String Name, Code;
	public int Region;
	public double Latitude, Longitude;
	public WindProviderType Provider;
	public boolean Favorite;
	public int Special;
	public float Distance;
		
	public Station() {
		this("none","none",1,false,WindProviderType.Aemet,0,0);
	}
	
	public Station( String name, int special ) {
		this(name,"none",1,false,WindProviderType.Aemet,0,0);
		Special = special;		
	}
	
	public Station( String str ) {		
		String[] fields = str.split(":");
    	Code = fields[0];
    	Name = fields[1];
    	Provider = WindProviderType.values()[Integer.parseInt(fields[2])];
    	Region = Integer.parseInt(fields[3]);    	
    	Latitude = Double.parseDouble(fields[4]);
    	Longitude = Double.parseDouble(fields[5]);
    	Special = -1;
    	Distance = -1.0F;
    	createArrays();    	
	}	
	
	public Station( String name, String code, int region, boolean favorite, WindProviderType provider, double lat, double lon ) {
		Name = name;
		Code = code;
		Region = region;
		Favorite = favorite;
		Provider = provider;
		Latitude = lat;
		Longitude = lon;
		Special = -1;
		Distance = -1.0F;
		createArrays();
	}
	
	public Station( Station station ) {
		this( station.Name, station.Code, station.Region, station.Favorite, station.Provider, station.Latitude, station.Longitude );
		replace(station);
	}
	
	public Station( Bundle bundle, String code, boolean fav ) {
		this();
		loadState(bundle, code, fav);
	}
	
	private void createArrays() {
		ListDirection = new ArrayList<Number>();
		ListHumidity = new ArrayList<Number>();
		ListSpeedMed = new ArrayList<Number>();
		ListSpeedMax = new ArrayList<Number>();
	}	
	
	@Override
	public String toString() {
		if( isSpecial() )
			return Name;
		return Code + " - " + Name;
	}
	
	public void clear() {
		ListDirection.clear();
		ListHumidity.clear();
		ListSpeedMed.clear();
		ListSpeedMax.clear();
	}
	
	public void add( Station station ) {
		if( station == null )
			return;
		ListDirection.addAll(station.ListDirection);
		ListHumidity.addAll(station.ListHumidity);
		ListSpeedMed.addAll(station.ListSpeedMed);
		ListSpeedMax.addAll(station.ListSpeedMax);
	}
	
	public void replace( Station station ) {
		clear();
		add(station);
		Name = station.Name;
		Code = station.Code;
		Region = station.Region;
		Provider = station.Provider;
		Favorite = station.Favorite;
		Latitude = station.Latitude;
		Longitude = station.Longitude;
		Special = station.Special;
	}
	
	public boolean isEmpty() {
		return ListDirection == null || ListDirection.size() < 2;
	}
	
	public boolean isSpecial() {
		return Special != -1;
	}
	
	public boolean isOutdated() {
		if( isEmpty() )
			return true;
		long stamp = (Long)ListDirection.get(ListDirection.size()-2);
		if( Calendar.getInstance().getTimeInMillis()-stamp > 220*60*1000)
			return true;
		return false;
	}
	
	public void saveState( Bundle outState ) {
		//outState.putString(Code+"-name", Name);
		//outState.putInt(Code+"-region", Region);
		//outState.putInt(Code+"-type", Provider.getValue());
		outState.putBoolean(Code+"-star", Favorite);
		//outState.putDouble(Code+"-lat", Latitude);
		//outState.putDouble(Code+"-lon", Longitude);
		saveLongArray(outState, "dirx", ListDirection, 0);
		saveIntArray(outState, "diry", ListDirection, 1);
		saveLongArray(outState, "humx", ListHumidity, 0);
		saveFloatArray(outState, "humy", ListHumidity, 1);
		saveLongArray(outState, "medx", ListSpeedMed, 0);
		saveFloatArray(outState, "medy", ListSpeedMed, 1);
		saveLongArray(outState, "maxx", ListSpeedMax, 0);
		saveFloatArray(outState, "maxy", ListSpeedMax, 1);
	}
	
	public boolean loadState( Bundle bundle, String code, boolean fav ) {
		if( bundle == null )
			return false;
		Code = code;
		return loadState(bundle, fav);
	}
	
	public boolean loadState( Bundle bundle, boolean fav ) {
		if( bundle == null )
			return false;
		//Name = bundle.getString(Code+"-name");
		//Region = bundle.getInt(Code+"-region");
		//Favorite = bundle.getBoolean(Code+"-star");
		//Provider = WindProviderType.values()[bundle.getInt(Code+"-type")];
		//Latitude = bundle.getDouble(Code+"-lat");
		//Longitude = bundle.getDouble(Code+"-lon");
		Favorite = fav;
		loadLongInt( bundle, "dirx", "diry", ListDirection );
		loadLongFloat( bundle, "humx", "humy", ListHumidity );
		loadLongFloat( bundle, "medx", "medy", ListSpeedMed );
		loadLongFloat( bundle, "maxx", "maxy", ListSpeedMax );
		return true;
	}
	
	private void loadLongInt( Bundle bundle, String name1, String name2, List<Number> list ) {
		long[] x = bundle.getLongArray(Code+"-"+name1);
		int[] y = bundle.getIntArray(Code+"-"+name2);
		list.clear();
		if( x == null || y == null )
			return;
		for( int i = 0; i < x.length; i++ ) {
			list.add(x[i]);
			list.add(y[i]);
		}
	}

	private void loadLongFloat( Bundle bundle, String name1, String name2, List<Number> list ) {
		long[] x = bundle.getLongArray(Code+"-"+name1);
		float[] y = bundle.getFloatArray(Code+"-"+name2);
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
		
		outState.putLongArray(Code+"-"+name, data);
	}
	
	private void saveFloatArray( Bundle outState, String name, List<Number> list, int off ) {
		int len = list.size()/2;
		float[] data = new float[len];
		int j = 0;
				
		for( int i = off; i < list.size(); i+= 2 )
			data[j++] = (Float)list.get(i);
		
		outState.putFloatArray(Code+"-"+name, data);
	}
	
	private void saveIntArray( Bundle outState, String name, List<Number> list, int off ) {
		int len = list.size()/2;
		int[] data = new int[len];
		int j = 0;
				
		for( int i = off; i < list.size(); i+= 2 )
			data[j++] = (Integer)list.get(i);
		
		outState.putIntArray(Code+"-"+name, data);
	}
}