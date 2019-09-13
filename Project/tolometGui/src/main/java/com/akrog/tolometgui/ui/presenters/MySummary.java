package com.akrog.tolometgui.ui.presenters;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import com.akrog.tolometgui.R;
import com.akrog.tolometgui.model.AppSettings;
import com.akrog.tolometgui.ui.activities.ToolbarActivity;
import com.akrog.tolometgui.ui.viewmodels.MainViewModel;
import com.akrog.tolometgui.ui.views.Axis;

import androidx.lifecycle.ViewModelProviders;

public class MySummary implements Presenter, Axis.ChangeListener {
	private ToolbarActivity activity;
	private AppSettings settings;
	private MainViewModel model;
	private TextView summary;
	private Long stamp = null;
	
	@Override
	public void initialize(ToolbarActivity activity, Bundle bundle) {
		this.activity = activity;
		model = ViewModelProviders.of(activity).get(MainViewModel.class);
		settings = AppSettings.getInstance();

		summary = (TextView)activity.findViewById(R.id.textSummary);
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
		if( model.getCurrentStation() == null || model.getCurrentStation().isEmpty() )
    		summary.setText(activity.getString(R.string.NoData));
    	else
    		summary.setText(model.getSummary(
    				stamp, activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE,
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
