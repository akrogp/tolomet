package com.akrog.tolomet.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;

import com.akrog.tolomet.viewmodel.AppSettings;

/**
 * Created by gorka on 17/05/16.
 */
public abstract class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    protected void onCreate(Bundle savedInstanceState, int preferencesResId) {
        addPreferencesFromResource(preferencesResId);
        setPreferenceEntries();
        initSummaries(this.getPreferenceScreen());
    }

    private void setPreferenceEntries() {
        CharSequence unit = ((ListPreference)findPreference(AppSettings.PREF_UNIT)).getEntry();
        setPreferenceEntries(AppSettings.PREF_SPEED_RANGE, unit);
        setPreferenceEntries(AppSettings.PREF_MARKER_MIN, unit);
        setPreferenceEntries(AppSettings.PREF_MARKER_MAX, unit);
    }

    private void setPreferenceEntries(CharSequence key, CharSequence unit) {
        ListPreference pref = (ListPreference)findPreference(key);
        CharSequence[] values = pref.getEntryValues();
        String[] entries = new String[values.length];
        for( int i = 0; i < values.length; i++ )
            if( Integer.parseInt(values[i].toString()) < 0 )
                entries[i] = "Auto";
            else
                entries[i] = values[i].toString() + " " + unit;
        pref.setEntries(entries);
        setSummary(pref);
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
        if( key.equals(AppSettings.PREF_UNIT) )
            setPreferenceEntries();
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