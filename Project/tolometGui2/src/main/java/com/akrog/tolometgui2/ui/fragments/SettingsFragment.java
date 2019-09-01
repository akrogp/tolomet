package com.akrog.tolometgui2.ui.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.model.AppSettings;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        setPreferenceEntries();
        initSummaries(this.getPreferenceScreen());
    }

    private void setPreferenceEntries() {
        ListPreference pref = (ListPreference)findPreference(AppSettings.PREF_UNIT);
        if( pref == null )
            return;
        CharSequence unit = pref.getEntry();
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
        else if( pref instanceof EditTextPreference)
            summary = ((EditTextPreference)pref).getText();
        if( summary != null )
            pref.setSummary(summary);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        if( key.equals(AppSettings.PREF_UNIT) )
            setPreferenceEntries();
        Preference pref = findPreference(key);
        setSummary(pref);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
