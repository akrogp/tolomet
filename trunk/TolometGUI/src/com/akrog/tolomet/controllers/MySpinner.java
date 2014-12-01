package com.akrog.tolomet.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Region;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Settings;

public class MySpinner implements OnItemSelectedListener, Controller {
	private Tolomet tolomet;
	private Manager model;
	private Settings settings;
	private Spinner spinner;
	private ArrayAdapter<Station> adapter;
	private Type spinnerType;
	private char vowel = '#';
	private int region = -1;	
	private final List<Station> choices = new ArrayList<Station>();
	private Station selectItem, startItem, favItem, regItem, nearItem, indexItem, allItem;

	@Override
	public void initialize(Tolomet tolomet, Bundle bundle) {
		this.tolomet = tolomet;
		model = tolomet.getModel();
		settings = tolomet.getSettings();
		
        spinner = (Spinner)tolomet.findViewById(R.id.spinner1);        
        adapter = new ArrayAdapter<Station>(tolomet,android.R.layout.simple_spinner_item,choices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(this);
        
        selectItem = new Station("--- " + this.tolomet.getString(R.string.select) + " ---", 0);
        startItem = new Station("["+this.tolomet.getString(R.string.menu_start)+"]", Type.StartMenu.getValue());
        favItem = new Station(this.tolomet.getString(R.string.menu_fav), Type.Favorite.getValue());
		regItem = new Station(this.tolomet.getString(R.string.menu_reg),Type.Regions.getValue());    	    	   
		nearItem = new Station(this.tolomet.getString(R.string.menu_close),Type.Nearest.getValue());
		indexItem = new Station(this.tolomet.getString(R.string.menu_index),Type.Vowels.getValue());
		allItem = new Station(this.tolomet.getString(R.string.menu_all),Type.All.getValue());
        
        loadState( bundle );        
	}
	
	private void loadState( Bundle bundle ) {						
		Set<String> favs = settings.getFavorites();
		for( Station station : model.getAllStations() )
			station.setFavorite(favs.contains(station.getCode()));
		
		State state = settings.loadSpinner();
		vowel = state.getVowel();
		region = state.getRegion();
		
		selectMenu(state.getType(), false);
		selectItem(state.getPos(), false);
	}
	
	@Override
	public void save(Bundle bundle) {
    	State state = new State();
    	state.setType(spinnerType);
    	state.setPos(spinner.getSelectedItemPosition());
    	state.setVowel(vowel);
    	state.setRegion(region);
    	settings.saveSpinner(state);
	}
	
	public Type getType() {
		return spinnerType;
	}
	
	public void selectMenu( Type type, boolean popup ) {
		spinnerType = type;
		resetChoices(type!=Type.StartMenu);
		switch( type ) {
			case All: model.selectAll(); break;
			case Favorite: model.selectFavorites(); break;
			case Nearest: selectNearest(); break;
			case Region: model.selectRegion(region); break;
			case Regions: selectRegions(); break;
			case StartMenu: selectStart(); break;
			case Vowel: model.selectVowel(vowel); break;
			case Vowels: selectVowels(); break;
			default: break;
		}
		choices.addAll(model.getSelStations());
		adapter.notifyDataSetChanged();
		selectItem(0, popup);
	}
	
	public void select( Station station ) {
		if( station == model.getCurrentStation() )
			return;
		clearDistance();
		model.setCurrentStation(station);
		save(null);
		if( !station.isSpecial() )			
			return;
		
		if( station.getSpecial() > 200 ) {
			vowel=(char)(station.getSpecial()-200);
			spinnerType = Type.Vowel;
		} else if( station.getSpecial() < Type.StartMenu.getValue() ) {
			region=station.getSpecial();
			spinnerType = Type.Region;
		} else
			spinnerType = Type.values()[station.getSpecial()-Type.StartMenu.getValue()];
		
		selectMenu(spinnerType, true);
	}
	
	private void selectItem( int pos, boolean popup ) {
		model.setCurrentStation(choices.get(pos));    	
    	spinner.setSelection(pos);
    	if( popup )
    		spinner.performClick();
	}

	private void resetChoices( boolean showStart ) {
		model.selectNone();
		choices.clear();
		choices.add(selectItem);
		if( showStart )
			choices.add(startItem);
	}
	
	private void selectNearest() {
    	LocationManager locationManager = (LocationManager)this.tolomet.getSystemService(Context.LOCATION_SERVICE);
    	Location ll = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    	float[] dist = new float[1];
    	for( Station station : model.getAllStations() ) {    		
    		Location.distanceBetween(ll.getLatitude(), ll.getLongitude(), station.getLatitude(), station.getLongitude(), dist);
    		station.setDistance(dist[0]);
    	}
		model.selectNearest();
	}
	
	private void clearDistance() {
		for( Station station : model.getAllStations() )
			station.setDistance(-1.0F);
	}
	
	private void selectStart() {		
		choices.add(favItem);
		choices.add(regItem);    	    	   
		choices.add(nearItem);
		choices.add(indexItem);
		choices.add(allItem);
	}
	
	private void selectRegions() {
		for( Region region : model.getRegions() )
			choices.add(new Station(region.getName(), region.getCode()));
	}
	
	private void selectVowels() {
		for( char c='A'; c <= 'Z'; c++ )
    		choices.add(new Station(""+c,200+c));
	}	
	
	public Station getSelectedItem() {
		return (Station)this.spinner.getSelectedItem();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Station station = getSelectedItem();
		select(station);
		tolomet.onSpinner(station);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {	
	}
	
	@Override
	public void redraw() {		
	}
	
	public static enum Type {
		StartMenu(100),
		All(101),
		Favorite(102),
		Nearest(103),
		Regions(104),
		Vowels(105),
		Vowel(106),
		Region(107);
		
		private final int value;
		
		private Type(int value) {
	        this.value = value;
	    }
		
		public int getValue() {
	        return value;
	    }
	}
	
	public static class State {
		public Type getType() {
			return type;
		}
		public void setType(Type type) {
			this.type = type;
		}
		public int getPos() {
			return pos;
		}
		public void setPos(int pos) {
			this.pos = pos;
		}
		public char getVowel() {
			return vowel;
		}
		public void setVowel(char vowel) {
			this.vowel = vowel;
		}
		public int getRegion() {
			return region;
		}
		public void setRegion(int region) {
			this.region = region;
		}
		private Type type = Type.StartMenu;
		private int pos = 0;
		private char vowel = '#';
		private int region = -1;
	}
}
