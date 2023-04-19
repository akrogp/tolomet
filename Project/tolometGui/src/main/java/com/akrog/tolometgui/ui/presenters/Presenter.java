package com.akrog.tolometgui.ui.presenters;

import android.os.Bundle;
import android.view.View;

import com.akrog.tolometgui.ui.activities.ToolbarActivity;


public interface Presenter {
	void initialize(ToolbarActivity activity, View view);
	void updateView();
	void save(Bundle bundle);
	void setEnabled(boolean enabled);
	void onSettingsChanged();
}
