package com.akrog.tolomet.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.akrog.tolomet.R;
import com.gunhansancar.android.sdk.helper.LocaleHelper;

public class AppSettingsActivity extends SettingsActivity {
    private final Intent resultIntent = new Intent();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        LocaleHelper.onCreate(this);
		onCreate(savedInstanceState, R.xml.preferences);
        if( getIntent() != null )
            resultIntent.putExtras(getIntent());
	}

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        super.onSharedPreferenceChanged(sp, key);
        resultIntent.putExtra(key,true);
        if( key.equals(LocaleHelper.SELECTED_LANGUAGE) ) {
            getIntent().putExtras(resultIntent);
            recreate();
        }
    }
}