package com.akrog.tolomet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by gorka on 21/03/16.
 */
public abstract class BrowserActivity extends BaseActivity {
    @Override
    public void createView(Bundle savedInstanceState, int layoutResId, int... buttonIds) {
        super.createView(savedInstanceState, layoutResId, buttonIds);
        createWeb();
    }

    private void createWeb() {
        web = (WebView)findViewById(R.id.web);
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
    protected void onResume() {
        super.onResume();
        reload();
    }

    @Override
    public void onRefresh() {
        if( model.getCurrentStation() != null ) {
            last = null;
            reload(model.getCurrentStation());
        }
    }

    @Override
    public void onCancel() {
        super.onCancel();
        web.stopLoading();
        last = null;
    }

    @Override
    public void onBrowser() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getUrl())));
    }

    @Override
    public void onChangedSettings() {
    }

    @Override
    public void onSelected(Station station) {
        reload(station);
    }

    private void reload() {
        if( model.getCurrentStation() != null )
            reload(model.getCurrentStation());
    }

    private void reload( Station station ) {
        if( station == last )
            return;
        web.loadUrl(getUrl());
        last = station;
    }

    protected abstract String getUrl();

    @Override
    public String getScreenShotSubject() {
        return model.getCurrentStation().getName();
    }

    @Override
    public String getScreenShotText() {
        return String.format("%s %s %s %s%s",
                getString(R.string.ShareWebPre),
                model.getCurrentStation().getProviderType().name(),
                getString(R.string.ShareWebMid),
                model.getCurrentStation().getName(),
                getString(R.string.ShareWebPost)
        );
    }

    private WebView web;
    private Station last;
}