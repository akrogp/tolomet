package com.akrog.tolometgui2.ui.fragments;

public class InfoFragment extends BrowserFragment {
    @Override
    protected String getUrl() {
        return model.getInforUrl();
    }
}
