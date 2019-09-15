package com.akrog.tolometgui.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.akrog.tolometgui.R;

import androidx.annotation.Nullable;

public abstract class BrowserFragment extends ToolbarFragment {
    private static final int[] LIVE_ITEMS = {R.id.refresh_item, R.id.browser_item};
    protected WebView web;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browser, container, false);
        setWebView(view.findViewById(R.id.web));
        return view;
    }

    @Override
    protected int getMenuResource() {
        return R.menu.browser;
    }

    @Override
    protected int[] getLiveMenuItems() {
        return LIVE_ITEMS;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        model.liveCurrentStation().observe(this, station -> reload());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if( id == R.id.refresh_item )
            reload();
        else if( id == R.id.browser_item )
            openBrowser();

        return super.onOptionsItemSelected(item);
    }

    private void setWebView(WebView web) {
        this.web = web;
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                beginProgress();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                endProgress();
                super.onPageFinished(view, url);
            }

        });
        WebSettings settings = web.getSettings();
        settings.setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(true);
        //settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
    }

    @Override
    public boolean needsScreenshotStation() {
        return true;
    }

    @Override
    public String getScreenshotSubject() {
        return model.getCurrentStation().getName();
    }

    @Override
    public String getScreenshotText() {
        return String.format("%s %s %s %s%s",
            getString(R.string.ShareWebPre),
            model.getCurrentStation().getProviderType().name(),
            getString(R.string.ShareWebMid),
            model.getCurrentStation().getName(),
            getString(R.string.ShareWebPost));
    }

    @Override
    public void onSettingsChanged() {
    }

    @Override
    public void onCancel() {
        super.onCancel();
        if( web != null )
            web.stopLoading();
    }

    public void reload() {
        if( web == null || !model.checkStation() )
            return;
        web.loadUrl(getUrl());
    }

    public void openBrowser() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getUrl())));
    }

    protected abstract String getUrl();
}
