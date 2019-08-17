package com.akrog.tolometgui2.ui.presenters;

import android.os.Bundle;

import com.akrog.tolometgui2.ui.activities.ToolbarActivity;


public interface Presenter {
	void initialize(ToolbarActivity activity, Bundle bundle);
	void updateView();
	void save(Bundle bundle);
	void setEnabled(boolean enabled);
	void onSettingsChanged();
}
