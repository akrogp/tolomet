package com.akrog.tolomet.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Station;
import com.akrog.tolomet.data.StationManager;

public class MySpinner {
	private Tolomet tolomet;
	private StationManager stations;
	private Spinner spinner;
	private ArrayAdapter<Station> adapter;
	private SpinnerType spinnerType;
	
	public MySpinner( Tolomet tolomet, StationManager data, Bundle bundle ) {
		this.tolomet = tolomet;
		this.stations = data;
		
		SpinnerState spinnerState = loadState( bundle );                       
        this.spinner = (Spinner)this.tolomet.findViewById(R.id.spinner1);        
        this.adapter = new ArrayAdapter<Station>(this.tolomet,android.R.layout.simple_spinner_item,this.stations.spinner);
        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	this.spinner.setAdapter(this.adapter);
    	//setSpinnerType(spinnerState.Type, spinnerState.Selection, savedInstanceState == null ? true : false);
    	setType(spinnerState.type, spinnerState.selection, false);
        this.spinner.setOnItemSelectedListener(this.tolomet);
	}
	
	private SpinnerState loadState( Bundle bundle ) {
		SharedPreferences settings = this.tolomet.getPreferences(0);
    	SpinnerState spinner = new SpinnerState();
		spinner.type = SpinnerType.values()[settings.getInt("spinner-type", SpinnerType.StartMenu.getValue())-SpinnerType.StartMenu.getValue()];
    	spinner.selection = settings.getInt("spinner-sel", 0);    	
		return spinner;
	}
	
	public void saveState() {
		SharedPreferences settings = this.tolomet.getPreferences(0);
    	SharedPreferences.Editor editor = settings.edit();
    	//editor.putString("selection", mStation.Code);
    	editor.putInt("spinner-type", this.spinnerType.getValue());
    	editor.putInt("spinner-sel", this.spinner.getSelectedItemPosition());
    	editor.commit();
	}

	public SpinnerType getType() {
		return this.spinnerType;
	}
	
	private void setType( SpinnerType type, int sel ) {
    	setType(type, sel, true);
    }
	
	private void setType( SpinnerType type, int sel, boolean popup ) {
		this.stations.spinner.clear();
		switch( type ) {
			case AllStations:
				this.stations.spinner.addAll(this.stations.all);
				break;
			case CloseStations:
				this.stations.loadCloseStations();
				this.stations.spinner.addAll(this.stations.nearest);
				break;
			case FavoriteStations:
				this.stations.spinner.addAll(this.stations.favorites);
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
		if( station.special != SpinnerType.CloseStations.getValue() && this.spinnerType == SpinnerType.CloseStations ) 
			this.stations.clearDistance();
		if( station.special == SpinnerType.StartMenu.getValue() ) {
			if( this.spinnerType != SpinnerType.StartMenu )
				setType( SpinnerType.StartMenu, 0 );
			return;
		}
		if( station.special < SpinnerType.StartMenu.getValue() || station.special > 200 ) {
			this.stations.nearest.clear();			
			this.stations.nearest.add(this.stations.all.get(0));
			this.stations.nearest.add(this.stations.all.get(1));
			if( station.special > 200 ) {
				for( Station s : this.stations.all )
					if( s.name.startsWith(""+(char)(station.special-200)) )
						this.stations.nearest.add(s);
				setType( SpinnerType.VowelSations, 0 );
			} else {
				for( Station s : this.stations.all )
					if( s.region == station.special )
						this.stations.nearest.add(s);
				setType( SpinnerType.RegionStations, 0 );
			}			
			return;
		}
		setType( SpinnerType.values()[station.special-SpinnerType.StartMenu.getValue()], 0 );
	}
	
	public void notifyDataSetChanged() {
		this.adapter.notifyDataSetChanged();
	}
	
	public Station getSelectedItem() {
		return (Station)this.spinner.getSelectedItem();
	}
}
