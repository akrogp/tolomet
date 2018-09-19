package com.akrog.tolomet.ui.activities;

import android.os.Bundle;

import com.akrog.tolomet.R;
import com.akrog.tolomet.Station;

public class HelpActivity extends BaseActivity {
    public static final String PATH = "help";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createTitleView(savedInstanceState, R.layout.activity_help,
                R.id.share_item, //R.id.whatsapp_item,
                R.id.about_item, R.id.report_item);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onBrowser() {
    }

    @Override
    public void onSettingsChanged() {
    }

    @Override
    public void onSelected(Station station) {
    }

    @Override
    public String getScreenShotSubject() {
        return getString(R.string.help_subject);
    }

    @Override
    public String getScreenShotText() {
        return getString(R.string.help_body);
    }

    @Override
    public String getRelativeLink() {
        return PATH;
    }
}