package com.akrog.tolomet;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;

/**
 * Created by gorka on 17/05/16.
 */
public abstract class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    protected void onCreate(Bundle savedInstanceState, int preferencesResId) {
        addPreferencesFromResource(preferencesResId);
        initSummaries(this.getPreferenceScreen());
    }

    private void initSummaries(PreferenceGroup pg) {
        for (int i = 0; i < pg.getPreferenceCount(); i++) {
            Preference p = pg.getPreference(i);
            if (p instanceof PreferenceGroup)
                initSummaries((PreferenceGroup) p);
            else
                setSummary(p);
        }
    }

    private void setSummary(Preference pref) {
        CharSequence summary = null;
        if (pref instanceof ListPreference)
            summary = ((ListPreference)pref).getEntry();
        else if( pref instanceof EditTextPreference )
            summary = ((EditTextPreference)pref).getText();
        if( summary != null )
            pref.setSummary(summary);
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
}