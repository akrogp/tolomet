package com.akrog.tolometgui.ui.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akrog.tolometgui.R;

public class HelpFragment extends ToolbarFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
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
