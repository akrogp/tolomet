package com.akrog.tolometgui2.ui.fragments;

import android.view.MenuItem;

public abstract class ToolbarFragment extends ProgressFragment {
    protected void setEnabled(MenuItem item, boolean enabled ) {
        item.setEnabled(enabled);
        item.getIcon().setAlpha(enabled?0xFF:0x42);
    }
}
