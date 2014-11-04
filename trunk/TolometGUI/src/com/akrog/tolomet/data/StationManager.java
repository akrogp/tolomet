package com.akrog.tolomet.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.view.SpinnerType;

public class StationManager {
	public Station current;
	public List<Station> spinner, all, favorites, nearest, regions, options, vowels;
	private Tolomet tolomet;
	
	public StationManager( Tolomet tolomet, Bundle bundle ) {
		this.tolomet = tolomet;
		this.spinner = new ArrayList<Station>();
        this.all = new ArrayList<Station>();
        this.favorites = new ArrayList<Station>();
        this.nearest = new ArrayList<Station>();
        this.regions = new ArrayList<Station>();
        this.options  = new ArrayList<Station>();
        this.vowels  = new ArrayList<Station>();
        this.current = new Station();
        loadState(bundle);
	}
	
	private void loadState( Bundle bundle ) {
		if( this.all.isEmpty() ) {
        	Station start = new Station("--- " + this.tolomet.getString(R.string.select) + " ---", 0);
        	this.options.add(start);
        	this.all.add(start);
        	this.favorites.add(start);
        	this.nearest.add(start);
        	this.regions.add(start);
        	this.vowels.add(start);
        	start = new Station("["+this.tolomet.getString(R.string.menu_start)+"]", Type.StartMenu.getValue());
        	this.all.add(start);
        	this.favorites.add(start);
        	this.nearest.add(start);
        	this.regions.add(start);
        	this.vowels.add(start);
    		loadStations();
    		loadRegions();
    		loadOptions();
    		loadVowels();
    	}

    	if( bundle != null ) {
    		SharedPreferences settings = this.tolomet.getPreferences(0);
	    	for( Station station : this.all )
	    		if( !station.isSpecial() )
	    			station.loadState(bundle, settings.contains(station.code));
    	}
	}
	
	private void loadStations() {
    	SharedPreferences settings = this.tolomet.getPreferences(0);
    	InputStream inputStream = this.tolomet.getResources().openRawResource(R.raw.stations);
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader rd = new BufferedReader(in);
		String line;
		Station station;
		int favs = 0;
		try {
			while( (line=rd.readLine()) != null ) {
				station = new Station(line);
				this.all.add(station);
				if( settings.contains(station.code) ) {
					station.favorite = true;
					this.favorites.add(station);
					favs++;
				}
			}
			rd.close();
			if( favs == 0 ) {
	    		addFavorite("C072");	// Orduña
	    		addFavorite("C042");	// Punta Galea
	    	}
		} catch( Exception e ) {			
		}    	
    }
    
    private void loadRegions() {
    	InputStream inputStream = this.tolomet.getResources().openRawResource(R.raw.regions);
		InputStreamReader in = new InputStreamReader(inputStream);
		BufferedReader rd = new BufferedReader(in);
		String line;
		String[] fields;
		Station station;
		try {
			while( (line=rd.readLine()) != null ) {
				fields = line.split(",");
				station = new Station(fields[0],Integer.parseInt(fields[1]));
				this.regions.add(station);
			}
		} catch (IOException e) {}
	}
    
    private void loadOptions() {
    	this.options.add(new Station(this.tolomet.getString(R.string.menu_fav),Type.FavoriteStations.getValue()));
    	this.options.add(new Station(this.tolomet.getString(R.string.menu_reg),Type.Regions.getValue()));    	    	   
    	this.options.add(new Station(this.tolomet.getString(R.string.menu_close),Type.CloseStations.getValue()));
    	this.options.add(new Station(this.tolomet.getString(R.string.menu_index),Type.Vowels.getValue()));
    	this.options.add(new Station(this.tolomet.getString(R.string.menu_all),Type.AllStations.getValue()));
	}
    
    private void loadVowels() {
    	for( char c='A'; c <= 'Z'; c++ )
    		this.vowels.add(new Station(""+c,200+c));
	}
    
    public void saveState(Bundle outState) {
    	for( Station station : this.all )
    		if( !station.isSpecial() && !station.isEmpty() )
    			station.saveState(outState);
    }
    
    public void loadCloseStations() {
    	this.nearest.clear();    	
    	LocationManager locationManager = (LocationManager)this.tolomet.getSystemService(Context.LOCATION_SERVICE);
    	Location ll = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    	float[] dist = new float[1];
    	for( Station station : this.all ) {    		
    		Location.distanceBetween(ll.getLatitude(), ll.getLongitude(), station.latitude, station.longitude, dist);
    		station.distance = dist[0];
    		if( station.distance < 50000.0F )
    			this.nearest.add(station);
    	}
    	Collections.sort(this.nearest, new StationComparator());
    	this.nearest.add(0,this.all.get(0));
    	this.nearest.add(1,this.all.get(1));
    }
    
    public void loadVowelStations( char ch ) {
    	this.nearest.clear();
    	this.nearest.add(this.all.get(0));
    	this.nearest.add(this.all.get(1));
    	for( Station s : this.all )
			if( s.name.startsWith(""+ch) )
				this.nearest.add(s);
    }
    
    public void loadRegionStations( int region ) {
    	this.nearest.clear();
    	this.nearest.add(this.all.get(0));
    	this.nearest.add(this.all.get(1));
    	for( Station s : this.all )
			if( s.region == region )
				this.nearest.add(s);
    }
    
    public void clearDistance() {
    	for( Station station : this.all )
    		station.distance = -1.0F;
    }
    
    public boolean addFavorite() {
    	return addFavorite( this.current.code );
    }
    
    public boolean addFavorite( String code ) {
    	// Redundancy check
    	if( code.equals("none") )
    		return false;
    	SharedPreferences settings = this.tolomet.getPreferences(0);
    	if( settings.contains(code) )
    		return false;
    	
    	// List
    	this.favorites.clear();
    	this.favorites.add(this.all.get(0));
    	this.favorites.add(this.all.get(1));
    	for( Station station : this.all ) {
    		if( station.code.equals(code) )
    			station.favorite = true;
    		if( station.favorite )
    			this.favorites.add(station);
    	}
    	
    	// State
    	SharedPreferences.Editor editor = settings.edit();
    	editor.putBoolean(code, true);
    	editor.commit();
    	
    	return true;
	}

	public boolean removeFavorite() {
		// Redundancy check
		SharedPreferences settings = this.tolomet.getPreferences(0);
		if( !settings.contains(this.current.code) )
    		return false;
		
		// List
    	for( Station station : this.favorites )
    		if( station.code.equals(this.current.code) ) {
    			station.favorite = false;
    			this.favorites.remove(station);
    			break;
    		}
    	
    	// State
    	SharedPreferences.Editor editor = settings.edit();
    	editor.remove(this.current.code);
    	editor.commit();
    	
    	return true;
	}
}