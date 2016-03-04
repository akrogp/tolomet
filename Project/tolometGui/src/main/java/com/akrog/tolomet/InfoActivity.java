package com.akrog.tolomet;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by gorka on 4/03/16.
 */
public class InfoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createView(savedInstanceState, R.layout.activity_info,
                R.id.favorite_item, R.id.charts_item, R.id.map_item, R.id.about_item, R.id.report_item);
        createProgress();
        createWeb();
    }

    private void createProgress() {
        progress = new ProgressDialog(this);
        progress.setMessage(getString(R.string.Loading) + "...");
        //progress.setTitle("");
        progress.setIndeterminate(true);
        progress.setCancelable(true);
        progress.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                web.stopLoading();
                last = null;
            }
        });
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
                progress.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progress.dismiss();
                super.onPageFinished(view, url);
            }

        });
        web.getSettings().setUserAgentString("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.75 Safari/537.36");
        web.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( model.getCurrentStation() != null )
            onSelected(model.getCurrentStation());
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public void onChangedSettings() {
    }

    @Override
    public void onSelected(Station station) {
        if( station == last )
            return;
        web.loadUrl(model.getInforUrl());
        last = station;
    }

    @Override
    public String getScreenShotSubject() {
        return null;
    }

    @Override
    public String getScreenShotText() {
        return null;
    }

    private WebView web;
    private ProgressDialog progress;
    private Station last;
}
