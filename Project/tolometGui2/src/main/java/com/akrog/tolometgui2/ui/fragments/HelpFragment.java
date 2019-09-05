package com.akrog.tolometgui2.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akrog.tolometgui2.R;

public class HelpFragment extends ToolbarFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    protected int getMenuResource() {
        return R.menu.help;
    }

    @Override
    protected int[] getLiveMenuItems() {
        return new int[0];
    }

    @Override
    public String getScreenshotSubject() {
        return getString(R.string.HelpSubject);
    }

    @Override
    public String getScreenshotText() {
        return getString(R.string.HelpText);
    }

    @Override
    public boolean useStation() {
        return false;
    }
}
