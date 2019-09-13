package com.akrog.tolometgui.ui.fragments;

public class ProviderFragment extends BrowserFragment {
    @Override
    protected String getUrl() {
        return model.getUserUrl();
    }
}
