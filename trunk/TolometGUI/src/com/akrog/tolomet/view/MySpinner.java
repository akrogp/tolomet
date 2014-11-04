package com.akrog.tolomet.view;

import java.util.ArrayList;
import java.util.List;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.Tolomet;

public class MySpinner implements OnItemSelectedListener {
	private Tolomet tolomet;
	private Manager model;
	private Spinner spinner;
	private ArrayAdapter<Station> adapter;
	private Type spinnerType;
	private char vowel = '#';
	private int region = -1;
	private final List<Station> stations = new ArrayList<Station>();
	
	public MySpinner( Tolomet tolomet, Manager model, Bundle bundle ) {
		this.tolomet = tolomet;
		this.model = model;
		
		State spinnerState = loadState( bundle );                       
        spinner = (Spinner)tolomet.findViewById(R.id.spinner1);        
        adapter = new ArrayAdapter<Station>(tolomet,android.R.layout.simple_spinner_item,stations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(adapter);
    	//setSpinnerType(spinnerState.Type, spinnerState.Selection, savedInstanceState == null ? true : false);
    	setType(spinnerState.getType(), spinnerState.getSelection(), false);
        spinner.setOnItemSelectedListener(this);
	}
	
	private State loadState( Bundle bundle ) {
		SharedPreferences settings = tolomet.getPreferences(0);
    	State spinner = new State();
		spinner.setType(Type.values()[settings.getInt("spinner-type", Type.StartMenu.getValue())-Type.StartMenu.getValue()]);
    	spinner.setSelection(settings.getInt("spinner-sel", 0));
    	vowel = settings.getString("spinner-vowel", "#").charAt(0);
    	region = settings.getInt("spinner-region", -1);
    	if( spinner.getType() == Type.VowelSations )
    		model.selectVowel(vowel);
    	else if( spinner.getType() == Type.RegionStations )
    		model.selectRegion(region);
		return spinner;
	}
	
	public void saveState() {
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
		return this.spinnerType;
	}
	
	private void setType( Type type, int sel ) {
    	setType(type, sel, true);
    }
	
	private void setType( Type type, int sel, boolean popup ) {
		stations.clear();
		switch( type ) {
			case AllStations:
				model.selectAll();
				stations.addAll(model.getSelStations());
				break;
			case CloseStations:
				//this.stations.loadCloseStations();
				model.selectNearest();
				stations.addAll(model.getSelStations());
				break;
			case FavoriteStations:
				model.selectFavorites();
				stations.addAll(model.getSelStations());
				break;
			case RegionStations:
				this.stations.spinner.addAll(this.stations.nearest);
				break;
			case VowelSations:
				this.stations.spinner.addAll(this.stations.nearest);
				break;
			case Regions:
				this.stations.spinner.addAll(this.stations.regions);
				break;
			case StartMenu:
				this.stations.spinner.addAll(this.stations.options);
				break;
			case Vowels:
				this.stations.spinner.addAll(this.stations.vowels);
				break;
		}		
    	this.spinnerType = type;
    	this.adapter.notifyDataSetChanged();    	
    	this.spinner.setSelection(sel);
    	if( popup )
    		this.spinner.performClick();
	}
	
	public void setType( Station station ) {
		if( station.special == 0 )
			return;
		if( station.special != Type.CloseStations.getValue() && this.spinnerType == Type.CloseStations ) 
			this.stations.clearDistance();
		if( station.special == Type.StartMenu.getValue() ) {
			if( this.spinnerType != Type.StartMenu )
				setType( Type.StartMenu, 0 );
			return;
		}
		if( station.special > 200 ) { // Vowel
			this.vowel = (char)(station.special-200);
			this.stations.loadVowelStations(this.vowel);
			setType( Type.VowelSations, 0 );
			return;
		}
		if( station.special < Type.StartMenu.getValue() ) {	// Region
			this.region = station.special;
			this.stations.loadRegionStations(this.region);
			setType( Type.RegionStations, 0 );			
			return;
		}
		setType( Type.values()[station.special-Type.StartMenu.getValue()], 0 );
	}
	
	public void notifyDataSetChanged() {
		this.adapter.notifyDataSetChanged();
	}
	
	public Station getSelectedItem() {
		return (Station)this.spinner.getSelectedItem();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	public static enum Type {
		StartMenu(100),
		AllStations(101),
		FavoriteStations(102),
		CloseStations(103),
		RegionStations(104),
		VowelSations(105),
		Regions(106),
		Vowels(107);
		
		private final int value;
		
		private Type(int value) {
	        this.value = value;
	    }
		
		public int getValue() {
	        return value;
	    }
	}
	
	public static class State {
		private int selection;
		private Type type;
		
		public State() {
			setSelection(0);
			setType(Type.StartMenu);
		}
		
		public int getSelection() {
			return selection;
		}
		public void setSelection(int selection) {
			this.selection = selection;
		}
		public Type getType() {
			return type;
		}
		public void setType(Type type) {
			this.type = type;
		}
	}
}
