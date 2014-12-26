package com.akrog.tolomet;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;

@SuppressWarnings("deprecation")
public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*PreferenceManager manager = getPreferenceManager(); 
		manager.setSharedPreferencesMode(MODE_PRIVATE);
		manager.setSharedPreferencesName("kk");*/
		addPreferencesFromResource(R.xml.preferences);
		initSummaries(this.getPreferenceScreen());
	}

	private void initSummaries( PreferenceGroup pg ) {
		for( int i = 0; i < pg.getPreferenceCount(); i++ ) {
			Preference p = pg.getPreference(i);
			if( p instanceof PreferenceGroup )
				initSummaries((PreferenceGroup)p);
			else
				setSummary(p);
		}
	}

	private void setSummary(Preference pref) {
		if( pref instanceof ListPreference ) {
			ListPreference listPref = (ListPreference)pref;
			pref.setSummary(listPref.getEntry());
		}
	}

	public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
		Preference pref = findPreference(key);
		setSummary(pref);
	}	

	@Override
	protected void onResume() {
		super.onResume();
		getPreferenceScreen().getSharedPreferences()
				.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences()
				.unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onBackPressed() {
		setResult(RESULT_OK);
		super.onBackPressed();
	}
}