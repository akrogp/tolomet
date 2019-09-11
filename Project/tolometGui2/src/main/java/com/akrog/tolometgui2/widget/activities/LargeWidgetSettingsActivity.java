package com.akrog.tolometgui2.widget.activities;

import com.akrog.tolometgui2.widget.providers.SpotWidgetProvider;

/**
 * Created by gorka on 31/05/16.
 */
public class LargeWidgetSettingsActivity extends WidgetSettingsActivity {
    @Override
    protected int getWidgetSize() {
        return SpotWidgetProvider.WIDGET_SIZE_LARGE;
    }
}
