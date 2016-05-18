package com.akrog.tolomet;

import android.os.Bundle;

public class AppSettingsActivity extends SettingsActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onCreate(savedInstanceState,R.xml.preferences);
	}

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}