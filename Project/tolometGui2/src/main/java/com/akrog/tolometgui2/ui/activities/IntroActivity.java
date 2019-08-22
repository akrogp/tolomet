package com.akrog.tolometgui2.ui.activities;

import android.os.Bundle;

import com.akrog.tolometgui2.R;
import com.akrog.tolometgui2.model.AppSettings;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        findViewById(R.id.help_ok).setOnClickListener(view -> {
            AppSettings.getInstance().setIntroAccepted(true);
            finish();
        });
    }
}
