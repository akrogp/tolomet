package com.akrog.tolometgui.ui.presenters;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;

import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.AppSettings;
import com.akrog.tolometgui.ui.activities.ToolbarActivity;
import com.akrog.tolometgui.ui.viewmodels.MainViewModel;
import com.akrog.tolometgui.ui.views.Axis;

public class MySummary implements Presenter, Axis.ChangeListener {
	private ToolbarActivity activity;
	private AppSettings settings;
	private MainViewModel model;
	private TextView summary;
	private Long stamp = null;
	
	@Override
	public void initialize(ToolbarActivity activity, View view) {
		this.activity = activity;
		model = new ViewModelProvider(activity).get(MainViewModel.class);
		settings = AppSettings.getInstance();

		summary = (TextView)view.findViewById(R.id.textSummary);
	}
	
	@Override
	public void save(Bundle bundle) {		
	}

	@Override
	public void setEnabled(boolean enabled) {
		summary.setEnabled(enabled);
	}

	@Override
	public void onSettingsChanged() {
	}

	@Override
	public void updateView() {
		summary.setTextSize(TypedValue.COMPLEX_UNIT_SP, settings.isFlying() ? 20 : 16);
		if( model.getCurrentStation() == null || model.getCurrentStation().isEmpty() )
    		summary.setText(activity.getString(R.string.NoData));
    	else
			summary.setText(model.getSummary(
					stamp, activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE,
					!settings.isFlying(),
					settings.getSpeedFactor(), settings.getSpeedLabel()
			));
	}

	@Override
	public void onNewLimit(Number value) {
		stamp = value.longValue();
		if( activity != null && model.getCurrentStation() != null )
			updateView();
	}
}
