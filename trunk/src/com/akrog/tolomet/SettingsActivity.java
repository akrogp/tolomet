package com.akrog.tolomet;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.akrog.tolomet.R;

public class SettingsActivity extends PreferenceActivity {
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}