package com.akrog.tolomet.presenters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.akrog.tolomet.BaseActivity;
import com.akrog.tolomet.Country;
import com.akrog.tolomet.Manager;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Region;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.data.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MySpinner implements OnItemSelectedListener, Presenter {
	private BaseActivity activity;
	private Manager model;
	private Settings settings;
	private Spinner spinner;
	private ArrayAdapter<Station> adapter;
	private Type spinnerType;
	private char vowel = '#';
	private int region = -1;
	private String newCountry, country;
	private final List<Station> choices = new ArrayList<Station>();
	private Station selectItem, startItem, favItem, regItem, nearItem, indexItem, allItem, countryItem;

	@Override
	public void initialize(BaseActivity activity, Bundle bundle) {
		this.activity = activity;
		model = activity.getModel();
		settings = activity.getSettings();
		
        spinner = (Spinner)activity.findViewById(R.id.station_spinner);
        adapter = new ArrayAdapter<Station>(activity,android.R.layout.simple_spinner_item,choices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	spinner.setOnItemSelectedListener(this);
        
        selectItem = new Station("--- " + this.activity.getString(R.string.select) + " ---", 0);
        startItem = new Station("["+this.activity.getString(R.string.menu_start)+"]", Type.StartMenu.getValue());
        favItem = new Station(this.activity.getString(R.string.menu_fav), Type.Favorite.getValue());
		regItem = new Station(this.activity.getString(R.string.menu_reg),Type.Regions.getValue());
		nearItem = new Station(this.activity.getString(R.string.menu_close),Type.Nearest.getValue());
		indexItem = new Station(this.activity.getString(R.string.menu_index),Type.Vowels.getValue());
		allItem = new Station(this.activity.getString(R.string.menu_all),Type.All.getValue());
		countryItem = new Station(this.activity.getString(R.string.menu_country),Type.Countries.getValue());
        
        loadState( bundle );
	}
	
	public void loadState( Bundle bundle ) {
		State state = settings.loadSpinner();
		vowel = state.getVowel();
		region = state.getRegion();
		String country = state.getCountry();
		if( country == null )
			country = guessCountry();		
		if( !setCountry(country) )
			updateFavorites();
		Type type = state.getType();
		if( type == Type.StartMenu || type == Type.Countries || type == Type.Regions || type == Type.Vowels || type == Type.Nearest ||
			(type == Type.Region && model.getRegions().isEmpty()) ) {
			type = Type.StartMenu;
			state.setPos(0);
		}
				
		selectMenu(type, false);
		selectItem(state.getPos(), false);
	}

	public void changeStation( Station station ) {
		selectMenu(station.isFavorite() ? Type.Favorite : Type.All, false);
		spinner.setSelection(adapter.getPosition(station));
	}
	
	private boolean setCountry( String country ) {
		if( country == null || country.equals(this.country) )
			return false;
		model.setCountry(country);
		regItem.setName(country.equals("ES") ? this.activity.getString(R.string.menu_ccaa) : this.activity.getString(R.string.menu_reg));
		updateFavorites();
		this.country = country;
		return true;
	}

	private void updateFavorites() {
		Set<String> favs = settings.getFavorites();
		for( Station station : model.getAllStations() )
			station.setFavorite(favs.contains(station.getCode()));
	}

	public void setFavorite(boolean fav) {
		Station station = model.getCurrentStation();
		if( station.isSpecial() )
			return;
		station.setFavorite(fav);
		settings.setFavorite(station.getCode(), fav);
		if( fav )
			selectMenu(Type.Favorite, false);
		else if( getType() == Type.Favorite )
			selectMenu(Type.All, false);
		spinner.setSelection(adapter.getPosition(station));
	}
	
	private String guessCountry() {
		Locale locale = Locale.getDefault();
		String code;
		try {
			Location ll = getLocation(false);
			Geocoder geocoder = new Geocoder(activity, locale);
			List<Address> addresses = geocoder.getFromLocation(ll.getLatitude(), ll.getLongitude(), 1);
			code = addresses.get(0).getCountryCode();
		} catch(Exception e) {
			code = locale.getCountry();
		}
		return code;
	}
	
	@Override
	public void save(Bundle bundle) {
    	State state = new State();
    	state.setType(spinnerType);
    	state.setPos(spinner.getSelectedItemPosition());
    	state.setVowel(vowel);
    	state.setRegion(region);
    	state.setCountry(country);
    	settings.saveSpinner(state);
	}
	
	public Type getType() {
		return spinnerType;
	}
	
	private void selectMenu( Type type, boolean popup ) {
		spinnerType = type;
		resetChoices(type!=Type.StartMenu);
		switch( type ) {
			case All: model.selectAll(); break;
			case Favorite:
				updateFavorites();
				model.selectFavorites();
				if( model.getSelStations().isEmpty() ) {
					showFavoriteDialog();
					popup = false;
					selectRegions();
				}
				break;
			case Nearest:
				if( !selectNearest() ) {
					popup = false;
					selectRegions();
				}
				break;
			case Region: model.selectRegion(region); break;
			case Regions: selectRegions(); break;
			case StartMenu: selectStart(); break;
			case Vowel: model.selectVowel(vowel); break;
			case Vowels: selectVowels(); break;
			case Countries: selectCountries(); break;
			case Country: selectCountry(); break;
			default: break;
		}
		choices.addAll(model.getSelStations());
		adapter.notifyDataSetChanged();
		selectItem(0, popup);
	}
	
	private void select( Station station ) {
		if( station == model.getCurrentStation() )
			return;		
		model.setCurrentStation(station);
		save(null);
		if( !station.isSpecial() )			
			return;
		
		clearDistance();
		if( station.getSpecial() > 500 ) {
			vowel=(char)(station.getSpecial()-500);
			spinnerType = Type.Vowel;
		} else if( station.getSpecial() >= 200 ) {
			newCountry = model.getCountries().get(station.getSpecial()-200).getCode();
			spinnerType = Type.Country;
		} else if( station.getSpecial() < Type.StartMenu.getValue() ) {
			region=station.getSpecial();
			spinnerType = Type.Region;
		} else
			spinnerType = Type.values()[station.getSpecial()-Type.StartMenu.getValue()];
		
		selectMenu(spinnerType, true);
	}
	
	private void selectItem( int pos, boolean popup ) {
		if( pos >= choices.size() )
			pos = 0;
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
	
	private boolean selectNearest() {
    	Location ll = getLocation(true);
    	if( ll == null ) {
    		Toast.makeText(activity, activity.getString(R.string.error_gps),Toast.LENGTH_SHORT).show();
    		return false;
    	}
    	setCountry(guessCountry());
    	float[] dist = new float[1];
    	for( Station station : model.getAllStations() ) {    		
    		Location.distanceBetween(ll.getLatitude(), ll.getLongitude(), station.getLatitude(), station.getLongitude(), dist);
    		station.setDistance(dist[0]);
    	}
		model.selectNearest();
		if( model.getSelStations().isEmpty() ) {
			Toast.makeText(activity, activity.getString(R.string.warn_near),Toast.LENGTH_SHORT).show();
    		return false;
		}
		return true;
	}
	
	private Location getLocation( boolean warning ) {
		Location locationGps = null;
		Location locationNet = null;
	    try {
	    	LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
	    	boolean isGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	    	boolean isNet = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); 
	        if( isGps )
	        	locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	        if( isNet )
	            locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	        else if( !isGps && warning )
	            showLocationDialog();
	    } catch (Exception e) {
	    }
	    
	    if( locationGps == null )
	    	return locationNet;
	    if( locationNet == null )
	    	return locationGps;
	    return locationGps.getTime() >= locationNet.getTime() ? locationGps : locationNet;
	}

	private void showLocationDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setMessage(activity.getString(R.string.warn_gps));
        dialog.setPositiveButton(activity.getString(R.string.gps_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                activity.startActivity(myIntent);
            }
        });
        dialog.setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        });
        dialog.setIcon(android.R.drawable.ic_dialog_alert);
        dialog.show();
	}
	
	private void showFavoriteDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setMessage(activity.getString(R.string.warn_fav));
        dialog.setPositiveButton(activity.getString(R.string.fav_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        });
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.show();
	}
	
	private void clearDistance() {
		for( Station station : model.getAllStations() )
			station.setDistance(-1.0F);
	}
	
	private void selectStart() {		
		choices.add(favItem);
		if( !model.getRegions().isEmpty() )
			choices.add(regItem);    	    	   
		choices.add(nearItem);
		choices.add(indexItem);
		choices.add(allItem);
		choices.add(countryItem);
	}
	
	private void selectRegions() {
		for( Region region : model.getRegions() )
			choices.add(new Station(region.getName(), region.getCode()));
	}
	
	private void selectCountries() {
		List<Country> list = model.getCountries();
		int len = list.size();
		for( int i = 0; i < len; i++ ) {
			Country country = list.get(i);
			choices.add(new Station(country.getName(), 200+i));
		}
	}
	
	private void selectCountry() {
		setCountry(newCountry);
		if( model.getRegions().isEmpty() )
			model.selectAll();
		else
			selectRegions();
	}
	
	private void selectVowels() {
		for( char c='A'; c <= 'Z'; c++ )
    		choices.add(new Station(""+c,500+c));
	}	
	
	public Station getSelectedItem() {
		return (Station)this.spinner.getSelectedItem();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		Station station = getSelectedItem();
		select(station);
		activity.onSelected(station);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {	
	}
	
	@Override
	public void updateView() {
	}
	
	public static enum Type {
		StartMenu(100),
		All(101),
		Favorite(102),
		Nearest(103),
		Regions(104),
		Vowels(105),
		Vowel(106),
		Region(107),
		Country(108),
		Countries(109);
		
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
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		private Type type = Type.StartMenu;
		private int pos = 0;
		private char vowel = '#';
		private int region = -1;
		private String country;
	}
}
