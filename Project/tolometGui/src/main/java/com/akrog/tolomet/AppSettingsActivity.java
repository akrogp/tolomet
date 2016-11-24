package com.akrog.tolomet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class AppSettingsActivity extends SettingsActivity {
    private final Intent resultIntent = new Intent();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onCreate(savedInstanceState,R.xml.preferences);
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
    }
}