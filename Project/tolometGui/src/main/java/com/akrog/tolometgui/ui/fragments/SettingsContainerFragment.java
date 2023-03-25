package com.akrog.tolometgui.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.akrog.tolometgui.R;

public class SettingsContainerFragment extends ToolbarFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_container, container, false);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.settings_container, new AppSettingsFragment());
        fragmentTransaction.commit();
    }

    @Override
    protected int getMenuResource() {
        return R.menu.empty;
    }

    @Override
    protected int[] getLiveMenuItems() {
        return new int[0];
    }

    @Override
    public boolean needsScreenshotStation() {
        return false;
    }

    @Override
    public String getScreenshotSubject() {
        return getString(R.string.ShareSettingsSubject);
    }

    @Override
    public String getScreenshotText() {
        return getString(R.string.ShareSettingsText);
    }

    @Override
    public void onSettingsChanged() {

    }

    @Override
    public boolean useStation() {
        return false;
    }
}
