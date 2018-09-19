package com.akrog.tolomet.ui.activities;

import android.os.Bundle;

import com.akrog.tolomet.R;

/**
 * Created by gorka on 4/03/16.
 */
public class InfoActivity extends BrowserActivity {
    public static final String PATH = "info";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createSpinnerView(savedInstanceState, R.layout.activity_browser,
                R.id.favorite_item, R.id.refresh_item,
                R.id.charts_item, R.id.map_item, R.id.origin_item, R.id.browser_item,
                R.id.share_item, //R.id.whatsapp_item,
                R.id.help_item, R.id.about_item, R.id.report_item);
    }

    @Override
    protected String getUrl() {
        return model.getInforUrl();
    }

    @Override
    public String getRelativeLink() {
        return String.format("%s/%s", PATH, model.getCurrentStation().getId());
    }
}
