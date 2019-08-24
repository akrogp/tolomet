package com.akrog.tolometgui2.ui.fragments;

public class ProviderFragment extends BrowserFragment {
    @Override
    protected String getUrl() {
        return model.getUserUrl();
    }
}
