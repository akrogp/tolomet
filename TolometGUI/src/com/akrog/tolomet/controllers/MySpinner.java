package com.akrog.tolomet.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
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

public class MySpinner implements OnItemSelectedListener, Controller {
	private Tolomet tolomet;
	private Manager model;
	private Spinner spinner;
	private ArrayAdapter<Station> adapter;
	private Type spinnerType;
	private char vowel = '#';
	private int region = -1;	
	private final List<Station> choices = new ArrayList<Station>();
	private Station selectItem, startItem, favItem, regItem, nearItem, indexItem, allItem;

	@Override
	public void initialize(Tolomet tolomet, Manager model, Bundle bundle) {
		this.tolomet = tolomet;
		this.model = model;
		
        spinner = (Spinner)tolomet.findViewById(R.id.spinner1);        
        adapter = new ArrayAdapter<Station>(tolomet,android.R.layout.simple_spinner_item,choices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	//setSpinnerType(spinnerState.Type, spinnerState.Selection, savedInstanceState == null ? true : false);
    	//setType(spinnerState.getType(), spinnerState.getSelection(), false);
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
		/*SharedPreferences settings = tolomet.getPreferences(0);
    	State spinner = new State();
		spinner.setType(Type.values()[settings.getInt("spinner-type", Type.StartMenu.getValue())-Type.StartMenu.getValue()]);
    	spinner.setSelection(settings.getInt("spinner-sel", 0));
    	vowel = settings.getString("spinner-vowel", "#").charAt(0);
    	region = settings.getInt("spinner-region", -1);
    	if( spinner.getType() == Type.Vowel )
    		model.selectVowel(vowel);
    	else if( spinner.getType() == Type.Region )
    		model.selectRegion(region);*/
		
		SharedPreferences settings = this.tolomet.getPreferences(0);
		if( tolomet.getConfigVersion() == 0 )
			migrateFavs(settings);
		else {
			Set<String> set = fromCsv(settings.getString("fav", ""));
			for( Station station : model.getAllStations() )
				station.setFavorite(set.contains(station.getCode()));
		}
		
		select(startItem);
	}
	
	private void migrateFavs( SharedPreferences settings ) {
		Set<String> set = new HashSet<String>(); 
		for( Station station : model.getAllStations() )
			if( settings.contains(station.getCode()) ) {
				station.setFavorite(true);
				set.add(station.getCode());
			}
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("fav", toCsv(set));
		editor.commit();
	}
	
	private Set<String> fromCsv( String str ) {
		Set<String> set = new HashSet<String>();
		set.addAll(Arrays.asList(str.split(",")));
		return set;
	}
	
	private String toCsv( Set<String> set ) {
		StringBuilder str = new StringBuilder();
		for( String code : set ) {
			str.append(code);
			str.append(',');
		}
		return str.toString().replaceAll(",$", "");
	}
	
	@Override
	public void save(Bundle bundle) {
		SharedPreferences settings = this.tolomet.getPreferences(0);
    	SharedPreferences.Editor editor = settings.edit();
    	//editor.putString("selection", mStation.Code);
    	editor.putInt("spinner-type", this.spinnerType.getValue());
    	editor.putInt("spinner-sel", this.spinner.getSelectedItemPosition());
    	if( this.vowel != '#' )
    		editor.putString("spinner-vowel", ""+this.vowel);
    	if( this.region != -1 )
    		editor.putInt("spinner-region", this.region);
    	editor.commit();
	}
	
	public Type getType() {
		return spinnerType;
	}
	
	public void select( Station station ) {
		if( station == model.getCurrentStation() )
			return;		
		model.setCurrentStation(station);
		if( !station.isSpecial() )			
			return;
		resetChoices(station!=startItem);
		if( station.getSpecial() > 200 ) // Vowel
			selectVowel((char)(station.getSpecial()-200));
		else if( station.getSpecial() < Type.StartMenu.getValue() ) // Region
			selectRegion(station.getSpecial());
		else switch (Type.values()[station.getSpecial()-Type.StartMenu.getValue()]) {
			case StartMenu: selectStart(); break;
			case All: selectAll(); break;
			case Favorite: selectFavorites(); break;
			case Nearest: selectNearest(); break;			
			case Regions: selectRegions(); break;
			case Vowels: selectVowels(); break;
			default: break;
		}
		choices.addAll(model.getSelStations());
		adapter.notifyDataSetChanged();
		selectItem(0, true);
	}

	private void resetChoices( boolean showStart ) {
		model.selectNone();
		choices.clear();
		choices.add(selectItem);
		if( showStart )
			choices.add(startItem);
	}
	
	private void selectVowel( char vowel ) {
		this.vowel = vowel;
		model.selectVowel(vowel);
		spinnerType = Type.Vowel;
	}
	
	private void selectFavorites() {
		model.selectFavorites();
		spinnerType = Type.Favorite;
	}
	
	private void selectAll() {
		model.selectAll();
		spinnerType = Type.All;
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
		spinnerType = Type.Nearest;
	}
	
	private void selectRegion( int region ) {
		this.region = region;
		model.selectRegion(region);
		spinnerType = Type.Region;
	}
	
	private void selectStart() {		
		choices.add(favItem);
		choices.add(regItem);    	    	   
		choices.add(nearItem);
		choices.add(indexItem);
		choices.add(allItem);
		spinnerType = Type.StartMenu;
	}
	
	private void selectRegions() {
		for( Region region : model.getRegions() )
			choices.add(new Station(region.getName(), region.getCode()));
		spinnerType = Type.Regions;
	}
	
	private void selectVowels() {
		for( char c='A'; c <= 'Z'; c++ )
    		choices.add(new Station(""+c,200+c));
		spinnerType = Type.Vowels;
	}
	
	private void selectItem( int pos, boolean popup ) {
		model.setCurrentStation(choices.get(pos));    	
    	spinner.setSelection(pos);
    	if( popup )
    		spinner.performClick();
	}
	
	public void notifyDataSetChanged() {
		this.adapter.notifyDataSetChanged();
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
}
