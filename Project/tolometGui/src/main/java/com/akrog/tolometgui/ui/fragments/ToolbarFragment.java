package com.akrog.tolometgui.ui.fragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.akrog.tolometgui.ui.activities.ToolbarActivity;
import com.akrog.tolometgui.ui.viewmodels.MainViewModel;
import com.akrog.tolometgui.ui.views.AndroidUtils;

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
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        activity = (ToolbarActivity)requireActivity();
        model = new ViewModelProvider(activity).get(MainViewModel.class);
        model.liveCurrentStation().observe(getViewLifecycleOwner(), station -> updateEnabled());
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
                AndroidUtils.setMenuItemEnabled(menu.findItem(itemId), enabled);
    }

    public abstract boolean needsScreenshotStation();

    public abstract String getScreenshotSubject();

    public abstract String getScreenshotText();

    public String getRelativeLink() {
        return null;
    }

    public boolean useStation() {
        return true;
    }

}
