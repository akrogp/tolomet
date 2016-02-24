package com.akrog.tolomet;

import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.akrog.tolomet.data.Settings;

/**
 * Created by gorka on 24/02/16.
 */
public abstract class ModelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings.initialize(this, model);
    }

    public Settings getSettings() {
        return settings;
    }

    public Manager getModel() {
        return model;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean alertNetwork() {
        if( !isNetworkAvailable() ) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage( getString(R.string.NoNetwork) );
            alertDialog.show();
            return true;
        }
        return false;
    }

    public abstract void redraw();

    public abstract void onRefresh();

    public abstract void onChangedSettings();

    public abstract void onSelected(Station station);

    protected final Manager model = new Manager();
    protected final Settings settings = new Settings();
}
