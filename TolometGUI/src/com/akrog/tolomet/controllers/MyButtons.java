package com.akrog.tolomet.controllers;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;
import com.akrog.tolomet.data.Settings;

public class MyButtons implements OnClickListener, OnCheckedChangeListener, Controller {
	private Tolomet tolomet;
	private Manager model;
	private Settings settings;
	private ImageButton buttonRefresh, buttonInfo;
	private CheckBox favorite;

	@Override
	public void initialize(Tolomet tolomet, Bundle bundle) {
		this.tolomet = tolomet;
		model = tolomet.getModel();
		settings = tolomet.getSettings();
		
		buttonRefresh = (ImageButton)tolomet.findViewById(R.id.button1);
        buttonRefresh.setOnClickListener(this);
        buttonInfo = (ImageButton)tolomet.findViewById(R.id.button2);
        buttonInfo.setOnClickListener(this);
        
        favorite = (CheckBox)tolomet.findViewById(R.id.favorite_button);
        favorite.setChecked(false);
        favorite.setOnCheckedChangeListener(this);		
	}

	@Override
	public void onClick(View v) {
		if( model.getCurrentStation().isSpecial() )
    		return;
    	switch( v.getId() ) {
    		case R.id.button1:
    			tolomet.onRefresh();
    			break;
    		case R.id.button2:
    			tolomet.onInfoUrl();
    			break;
    	}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		model.getCurrentStation().setFavorite(isChecked);
		settings.setFavorite(model.getCurrentStation().getCode(), isChecked);
	}

	@Override
	public void redraw() {
		buttonRefresh.setEnabled(!model.getCurrentStation().isSpecial());
		buttonInfo.setEnabled(!model.getCurrentStation().isSpecial());
		favorite.setChecked(model.getCurrentStation().isFavorite());
		favorite.setEnabled(!model.getCurrentStation().isSpecial());		
	}

	@Override
	public void save(Bundle bundle) {	
	}
}
