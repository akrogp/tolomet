package com.akrog.tolometgui.widget.activities;

import com.akrog.tolometgui.widget.providers.SpotWidgetProvider;

/**
 * Created by gorka on 31/05/16.
 */
public class SmallWidgetSettingsActivity extends WidgetSettingsActivity {
    @Override
    protected int getWidgetSize() {
        return SpotWidgetProvider.WIDGET_SIZE_SMALL;
    }
}
