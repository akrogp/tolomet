package com.akrog.tolomet.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.controllers.MySpinner;

public class Settings {	
	private SharedPreferences settings;
	private Tolomet tolomet;
	
	public void initialize( Tolomet tolomet, Manager model ) {
		this.tolomet = tolomet;
		//settings = tolomet.getPreferences(0);
		settings = PreferenceManager.getDefaultSharedPreferences(tolomet);
		//settings = tolomet.getSharedPreferences("kk", 0);
		//settings = tolomet.getSharedPreferences("com.akrog.tolomet", Tolomet.MODE_PRIVATE);
		migrate(model);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("cfg", VERSION);
		editor.commit();		
	}
	
	public int getConfigVersion() {
		return settings.getInt("cfg", 1);
	}
	
	public Set<String> getFavorites() {
		Set<String> result;
		try {
			result = fromCsv(settings.getString("fav", ""));
		} catch( Exception e ) {
			e.printStackTrace();
			result = new HashSet<String>();
		}
		return result;
	}
	
	public void addFavorite(String code) {
		Set<String> favs = getFavorites();
		favs.add(code);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("fav", toCsv(favs));
		editor.commit();
	}
	
	public void removeFavorite(String code) {
		Set<String> favs = getFavorites();
		favs.remove(code);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("fav", toCsv(favs));
		editor.commit();
	}
	
	public void setFavorite(String code, boolean fav) {
		if( fav )
			addFavorite(code);
		else
			removeFavorite(code);
	}
	
	public void saveSpinner(MySpinner.State state) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("spinner-type", state.getType().getValue());
    	editor.putInt("spinner-sel", state.getPos());
    	editor.putString("spinner-vowel", ""+state.getVowel());
    	editor.putInt("spinner-region", state.getRegion());
		editor.commit();
	}
	
	public MySpinner.State loadSpinner() {
		MySpinner.State state = new MySpinner.State();
		try {
			int value = settings.getInt("spinner-type", MySpinner.Type.StartMenu.getValue());
			state.setType(MySpinner.Type.values()[value-MySpinner.Type.StartMenu.getValue()]);
			state.setPos(settings.getInt("spinner-sel", 0));
			state.setVowel(settings.getString("spinner-vowel", "#").charAt(0));
			state.setRegion(settings.getInt("spinner-region", 0));
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return state;
	}
	
	public long getGaeStamp() {
		return settings.getLong("gae:last", 0);
	}
	
	public void saveGaeStamp( long stamp ) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putLong("gae:last", stamp);
		editor.commit();
	}
	
	public int getSpeedRange() {
		return Integer.parseInt(settings.getString(tolomet.getString(R.string.pref_speedRange), tolomet.getString(R.string.pref_speedRangeDefault)));
	}
	
	public int getMinMarker() {
		return Integer.parseInt(settings.getString(tolomet.getString(R.string.pref_minMarker), tolomet.getString(R.string.pref_minMarkerDefault)));
	}
	
	public int getMaxMarker() {
		return Integer.parseInt(settings.getString(tolomet.getString(R.string.pref_maxMarker), tolomet.getString(R.string.pref_maxMarkerDefault)));
	}
	
	public int getMinTemp() {
		return Integer.parseInt(settings.getString(tolomet.getString(R.string.pref_minTemp), tolomet.getString(R.string.pref_minTempDefault)));
	}
	
	public int getMaxTemp() {
		return Integer.parseInt(settings.getString(tolomet.getString(R.string.pref_maxTemp), tolomet.getString(R.string.pref_maxTempDefault)));
	}
	
	public int getMinPres() {
		return Integer.parseInt(settings.getString(tolomet.getString(R.string.pref_minPres), tolomet.getString(R.string.pref_minPresDefault)));
	}
	
	public int getMaxPres() {
		return Integer.parseInt(settings.getString(tolomet.getString(R.string.pref_maxPres), tolomet.getString(R.string.pref_maxPresDefault)));
	}
	
	private void migrate( Manager model ) {
		if( getConfigVersion() == 0 )
			migrateFavs(model);
	}
	
	private void migrateFavs( Manager model ) {
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
	
	private final static int VERSION = 2;	
}
