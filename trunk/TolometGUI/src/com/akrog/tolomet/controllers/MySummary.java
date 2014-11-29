package com.akrog.tolomet.controllers;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import com.akrog.tolomet.Manager;
import com.akrog.tolomet.R;
import com.akrog.tolomet.Tolomet;

public class MySummary implements Controller {
	private Tolomet tolomet;
	private Manager model;
	private TextView summary;
	
	@Override
	public void initialize(Tolomet tolomet, Manager model, Bundle bundle) {
		this.tolomet = tolomet;
		this.model = model;
		summary = (TextView)tolomet.findViewById(R.id.textView1);
	}
	
	@Override
	public void save(Bundle bundle) {		
	}
	
	@Override
	public void redraw() {
		if( model.getCurrentStation().isEmpty() )
    		summary.setText(tolomet.getString(R.string.NoData));
    	else
    		summary.setText(model.getSummary(tolomet.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE));
	}	
}
