package com.akrog.tolomet;

import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;

import com.akrog.tolomet.data.Settings;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WidgetSettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        addPreferencesFromResource(R.xml.widget_preferences);
        settings.initialize(this, model);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if( extras != null )
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        if( appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID )
            finish();
        else {
            Set<String> favs = settings.getFavorites();
            List<String> entries = new ArrayList<>();
            List<String> values = new ArrayList<>();
            for( String fav : favs ) {
                Station station = model.findStation(fav);
                if( station != null ) {
                    entries.add(station.getName());
                    values.add(station.getId());
                }
            }
            if( entries.isEmpty() )
                showFavoriteDialog();
            else {
                Intent result = new Intent();
                result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
                setResult(RESULT_OK, result);

                ListPreference listPreference = (ListPreference) findPreference(STATION_KEY);
                listPreference.setEntries(entries.toArray(new String[0]));
                listPreference.setEntryValues(values.toArray(new String[0]));
            }
        }
    }

    private void showFavoriteDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.warn_fav));
        dialog.setPositiveButton(getString(R.string.fav_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        });
        dialog.setIcon(android.R.drawable.ic_dialog_info);
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        if( appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID )
            sendBroadcast(new Intent(WidgetProvider.FORCE_WIDGET_UPDATE));
        super.onBackPressed();
    }

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private final Manager model = new Manager();
    private final Settings settings = new Settings();

    public static String STATION_KEY = "wpref_station";
}
