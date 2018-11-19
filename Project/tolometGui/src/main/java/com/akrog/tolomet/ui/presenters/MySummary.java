package com.akrog.tolomet.ui.presenters;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import com.akrog.tolomet.ui.activities.ToolbarActivity;
import com.akrog.tolomet.viewmodel.AppSettings;
import com.akrog.tolomet.viewmodel.Model;
import com.akrog.tolomet.R;
import com.akrog.tolomet.ui.view.Axis;

public class MySummary implements Presenter, Axis.ChangeListener {
	private ToolbarActivity activity;
	private AppSettings settings;
	private final Model model = Model.getInstance();
	private TextView summary;
	private Long stamp = null;
	
	@Override
	public void initialize(ToolbarActivity activity, Bundle bundle) {
		this.activity = activity;
		settings = activity.getSettings();

		summary = (TextView)activity.findViewById(R.id.textView1);
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
		if( model.getCurrentStation().isEmpty() )
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
