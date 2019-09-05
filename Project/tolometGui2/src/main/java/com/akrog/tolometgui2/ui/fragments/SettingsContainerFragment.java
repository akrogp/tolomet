package com.akrog.tolometgui2.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akrog.tolometgui2.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SettingsContainerFragment extends ToolbarFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings_container, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.settings_container, new SettingsFragment());
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
