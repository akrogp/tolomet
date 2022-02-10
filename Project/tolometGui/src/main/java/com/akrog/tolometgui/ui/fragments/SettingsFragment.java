package com.akrog.tolometgui.ui.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.akrog.tolometgui.model.AppSettings;
import com.akrog.tolometgui.ui.activities.BaseActivity;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceGroup;

public abstract class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    protected abstract int getResource();

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(getResource(), rootKey);
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
        if( key.equals(AppSettings.SELECTED_LANGUAGE) ) {
            recreate();
            return;
        }
        if( key.equals(AppSettings.PREF_UNIT) )
            setPreferenceEntries();
        if( key.equals(AppSettings.PREF_ORIG_EUSKALMET) )
            AppSettings.refreshCore();
        Preference pref = findPreference(key);
        setSummary(pref);
        ((BaseActivity)getActivity()).onSettingsChanged(key);
    }

    private void recreate() {
        new Handler().post(() -> {
            Intent intent = getActivity().getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            getActivity().overridePendingTransition(0, 0);
            getActivity().finish();
            getActivity().overridePendingTransition(0, 0);
            startActivity(intent);
        });
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
