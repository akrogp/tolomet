package com.akrog.tolomet;

import android.os.Bundle;

/**
 * Created by gorka on 4/03/16.
 */
public class InfoActivity extends BrowserActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createSpinnerView(savedInstanceState, R.layout.activity_browser,
                R.id.favorite_item, R.id.refresh_item,
                R.id.charts_item, R.id.map_item, R.id.origin_item, R.id.browser_item,
                R.id.share_item, R.id.whatsapp_item,
                R.id.help_item, R.id.about_item, R.id.report_item);
    }

    @Override
    protected String getUrl() {
        return model.getInforUrl();
    }
}
