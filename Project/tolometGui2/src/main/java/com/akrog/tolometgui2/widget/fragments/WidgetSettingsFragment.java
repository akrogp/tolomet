package com.akrog.tolometgui2.widget.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.akrog.tolomet.Station;
import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.Tolomet;
import com.akrog.tolometgui2.model.AppSettings;
import com.akrog.tolometgui2.model.DbTolomet;
import com.akrog.tolometgui2.ui.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

public class WidgetSettingsFragment extends PreferenceFragmentCompat {
    private AppSettings appSettings;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.widget_preferences, rootKey);

        appSettings = AppSettings.getInstance();
        buildFavorites();
        showSpeedUnits();
    }

    private void buildFavorites() {
        Set<String> favs = appSettings.getFavorites();
        List<String> entries = new ArrayList<>();
        List<String> values = new ArrayList<>();
        for( String fav : favs ) {
            if( fav.isEmpty() )
                continue;
            Station station = DbTolomet.getInstance().findStation(fav);
            if( station != null ) {
                entries.add(station.getName());
                values.add(station.getId());
            }
        }
        if( entries.isEmpty() )
            showFavoriteDialog();
        else {
            ListPreference listPreference = (ListPreference)findPreference(STATION_KEY);
            listPreference.setEntries(entries.toArray(new String[0]));
            listPreference.setEntryValues(values.toArray(new String[0]));
            onSharedPreferenceChanged(null,listPreference.getKey());
        }
    }

    private void showSpeedUnits() {
        String units = " ("+appSettings.getSpeedLabel()+")";
        EditTextPreference pref = (EditTextPreference) findPreference(WMAX_KEY);
        pref.setTitle(getString(R.string.max_wind_prompt)+units);
        pref = (EditTextPreference) findPreference(WMIN_KEY);
        pref.setTitle(getString(R.string.min_wind_prompt)+units);
    }

    private void showFavoriteDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(getString(R.string.warn_fav));
        dialog.setPositiveButton(getString(R.string.fav_ok), (paramDialogInterface, paramInt) -> {
            getActivity().finish();
            startActivity(new Intent(Tolomet.getAppContext(), MainActivity.class));
        });
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.show();
    }

    private void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        if( key.equals(STATION_KEY) ) {
            EditTextPreference name = (EditTextPreference)findPreference(SPOT_KEY);
            ListPreference station = (ListPreference)findPreference(STATION_KEY);
            if( station.getEntry() != null ) {
                //if( name.getText() != null && name.getText().isEmpty() ) {
                name.setText(station.getEntry().toString());
                onSharedPreferenceChanged(sp, SPOT_KEY);
            }
        }
    }

    public static String STATION_KEY = "wstation0";
    public static String SPOT_KEY = "wspot";
    public static String WMAX_KEY = "wmaxWind0";
    public static String WMIN_KEY = "wminWind0";
}
