package com.akrog.tolomet.widget;

import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;

import com.akrog.tolomet.ChartsActivity;
import com.akrog.tolomet.Model;
import com.akrog.tolomet.R;
import com.akrog.tolomet.SettingsActivity;
import com.akrog.tolomet.Station;
import com.akrog.tolomet.data.AppSettings;
import com.akrog.tolomet.data.FlySpot;
import com.akrog.tolomet.data.WidgetSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class WidgetSettingsActivity extends SettingsActivity {

    protected abstract int getWidgetSize();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreate(savedInstanceState, R.xml.widget_preferences);
        model.setCountry(appSettings.getCountry());

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if( extras != null )
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        if( appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID )
            finish();
        else {
            Set<String> favs = appSettings.getFavorites();
            List<String> entries = new ArrayList<>();
            List<String> values = new ArrayList<>();
            for( String fav : favs ) {
                if( fav.isEmpty() )
                    continue;
                Station station = model.findStation(fav);
                if( station != null ) {
                    entries.add(station.getName());
                    values.add(station.getId());
                }
            }
            if( entries.isEmpty() )
                showFavoriteDialog();
            else {
                ListPreference listPreference = (ListPreference) findPreference(STATION_KEY);
                listPreference.setEntries(entries.toArray(new String[0]));
                listPreference.setEntryValues(values.toArray(new String[0]));
                onSharedPreferenceChanged(null,listPreference.getKey());
            }
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        super.onSharedPreferenceChanged(sp, key);
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

    private void showFavoriteDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.warn_fav));
        dialog.setPositiveButton(getString(R.string.fav_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                finish();
                startActivity(new Intent(getApplicationContext(), ChartsActivity.class));
            }
        });
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if( appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID ) {
            FlySpot spot = appSettings.getSpot();
            if( spot.isValid() ) {
                WidgetSettings widgetSettings = new WidgetSettings(this, appWidgetId);
                widgetSettings.setSpot(spot);
                Intent result = new Intent();
                result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                setResult(RESULT_OK, result);
                Intent intent = new Intent(WidgetReceiver.FORCE_WIDGET_UPDATE);
                intent.putExtra(WidgetReceiver.EXTRA_WIDGET_SIZE, getWidgetSize());
                sendBroadcast(intent);
            } else
                setResult(RESULT_CANCELED);
        }
        super.onBackPressed();
    }

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private final Model model = Model.getInstance();
    private final AppSettings appSettings = AppSettings.getInstance();

    public static String STATION_KEY = "wstation0";
    public static String SPOT_KEY = "wspot";
}
