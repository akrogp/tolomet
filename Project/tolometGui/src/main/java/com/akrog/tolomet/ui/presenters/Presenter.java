package com.akrog.tolomet.ui.presenters;

import android.os.Bundle;

import com.akrog.tolomet.ui.activities.BaseActivity;

public interface Presenter {
	void initialize(BaseActivity activity, Bundle bundle);
	void updateView();
	void save(Bundle bundle);
	void setEnabled(boolean enabled);
	void onSettingsChanged();
}
