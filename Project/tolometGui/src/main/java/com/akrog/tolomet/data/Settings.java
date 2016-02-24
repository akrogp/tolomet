package com.akrog.tolomet.data;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.SparseArray;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.Measurement;
import com.akrog.tolomet.ModelActivity;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.presenters.MySpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Settings {	
	private SharedPreferences settings;
	private ModelActivity activity;
	private Manager model;
	
	public void initialize( ModelActivity activity, Manager model ) {
		this.activity = activity;
		this.model = model;
		settings = PreferenceManager.getDefaultSharedPreferences(activity);
		
		migrate();
		fixValues();
		setDefaultsAuto();
		
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt("cfg", VERSION);
		editor.commit();		
	}
	
	private void setDefaultsAuto() {
		String autoKeys[] = {"pref_speedRange","pref_minTemp","pref_maxTemp","pref_minPres","pref_maxPres"};
		for( String key : autoKeys ) {
			if( settings.getString(key, null) == null ) {
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(key, INVALID+"");
				editor.commit();
			}
		}
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
    	editor.putString("spinner-country", state.getCountry());
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
			state.setCountry(settings.getString("spinner-country", Locale.getDefault().getCountry()));
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
	
	private int getPrefValue( String key, int idDefault, int idArray, boolean max, Measurement meas ) {
		int res = Integer.parseInt(settings.getString(key, activity.getString(idDefault)));
		if( res != INVALID )
			return res;
		if( meas.isEmpty() )
			return Integer.parseInt(activity.getString(idDefault));
		
		List<Integer> values = mapArrays.get(idArray);
		if( values == null ) {
			values = new ArrayList<Integer>();
			String[] strings = activity.getResources().getStringArray(idArray);
			for( int i = 0; i < strings.length; i++ ) {
				int value = Integer.parseInt(strings[i]);
				if( value != INVALID )
					values.add(value);
			}				
			mapArrays.put(idArray, values);
		}
		
		int auto = max ? meas.getMaximum().intValue() : meas.getMinimum().intValue();
		for( int i = 0; i < values.size(); i++ ) {
			int value = values.get(i);
			if( value <= auto )
				continue;
			if( !max )
				i = i > 1 ? i - 1 : 0;
			res = values.get(i);
			break;
		}
		
		if( res == INVALID )
			res = values.get(values.size()-1);
		
		return res;
	}
	
	public int getSpeedRange(Measurement meas) {
		return getPrefValue("pref_speedRange", R.string.pref_speedRangeDefault, R.array.pref_rangeValues, true, meas);
	}
	
	public int getMinMarker() {		
		return Integer.parseInt(settings.getString("pref_minMarker", activity.getString(R.string.pref_minMarkerDefault)));
	}
	
	public int getMaxMarker() {
		return Integer.parseInt(settings.getString("pref_maxMarker", activity.getString(R.string.pref_maxMarkerDefault)));
	}
	
	public int getMinTemp(Measurement meas) {
		return getPrefValue("pref_minTemp", R.string.pref_minTempDefault, R.array.pref_minTempValues, false, meas);
	}
	
	public int getMaxTemp(Measurement meas) {
		return getPrefValue("pref_maxTemp", R.string.pref_maxTempDefault, R.array.pref_maxTempValues, true, meas);
	}
	
	public int getMinPres(Measurement meas) {
		return getPrefValue("pref_minPres", R.string.pref_minPresDefault, R.array.pref_minPresValues, false, meas);
	}
	
	public int getMaxPres(Measurement meas) {
		return getPrefValue("pref_maxPres", R.string.pref_maxPresDefault, R.array.pref_maxPresValues, true, meas);
	}
	
	public boolean isSimpleMode() {		
		return settings.getString("pref_modeGraphs", activity.getString(R.string.pref_modeGraphsDefault)).equals("0");
	}
	
	public int getUpdateMode() {		
		return Integer.parseInt(settings.getString("pref_modeUpdate", activity.getString(R.string.pref_modeUpdateDefault)));
	}

	public void setUpdateMode(int mode) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("pref_modeUpdate",""+mode);
		editor.commit();
	}
	
	private void migrate() {
		if( getConfigVersion() == 0 )
			migrateFavs();
	}
	
	private void fixValues() {
		fixValues("pref_modeGraphs", R.array.pref_modeGraphsValues);
		fixValues("pref_modeUpdate", R.array.pref_modeUpdateValues );
		fixValues("pref_minTemp", R.array.pref_minTempValues );
		fixValues("pref_maxTemp", R.array.pref_maxTempValues );
		fixValues("pref_minPres", R.array.pref_minPresValues );
		fixValues("pref_maxPres", R.array.pref_maxPresValues );
		fixValues("pref_speedRange", R.array.pref_rangeValues );
		fixValues("pref_minMarker", R.array.pref_minSpeedValues );
		fixValues("pref_maxMarker", R.array.pref_maxSpeedValues );
	}
	
	private void fixValues( String key, int idArray ) {
		String pref = settings.getString(key, null);
		if( pref == null )
			return;
		String[] values = activity.getResources().getStringArray(idArray);
		for( String value : values )
			if( pref.equals(value) )
				return;
		SharedPreferences.Editor editor = settings.edit();
		editor.remove(key);
		editor.commit();
	}
	
	private void migrateFavs() {
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
	private final static int INVALID = -1000;
	private final SparseArray<List<Integer>> mapArrays = new SparseArray<List<Integer>>();
	public final static int MANUAL_UPDATES=0;
	public final static int SMART_UPDATES=1;
	public final static int AUTO_UPDATES=2;
}
