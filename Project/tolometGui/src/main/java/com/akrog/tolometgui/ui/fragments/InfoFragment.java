package com.akrog.tolometgui.ui.fragments;

public class InfoFragment extends BrowserFragment {
    @Override
    protected String getUrl() {
        return model.getInforUrl();
    }
}
