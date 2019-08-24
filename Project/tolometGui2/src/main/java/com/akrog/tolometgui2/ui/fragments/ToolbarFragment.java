package com.akrog.tolometgui2.ui.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.akrog.tolometgui2.ui.activities.ToolbarActivity;
import com.akrog.tolometgui2.ui.viewmodels.MainViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

public abstract class ToolbarFragment extends ProgressFragment {
    protected MainViewModel model;
    protected ToolbarActivity activity;
    protected Menu menu;

    protected abstract int getMenuResource();

    protected abstract int[] getLiveMenuItems();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        inflater.inflate(getMenuResource(), menu);
        updateEnabled();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = (ToolbarActivity)getActivity();
        model = ViewModelProviders.of(activity).get(MainViewModel.class);
    }

    @Override
    public boolean beginProgress() {
        if( !super.beginProgress() )
            return false;
        updateEnabled(false);
        return true;
    }

    @Override
    public boolean endProgress() {
        if( !super.endProgress() )
            return false;
        updateEnabled();
        return true;
    }

    protected void updateEnabled() {
        updateEnabled(model.checkStation());
    }

    protected void updateEnabled(boolean enabled) {
        if( menu != null )
            for( int itemId : getLiveMenuItems() )
                setEnabled(menu.findItem(itemId), enabled);
    }

    protected void setEnabled(MenuItem item, boolean enabled ) {
        item.setEnabled(enabled);
        item.getIcon().setAlpha(enabled?0xFF:0x42);
    }

    public abstract String getScreenshotSubject();

    public abstract String getScreenshotText();

    public String getRelativeLink() {
        return null;
    }
}
