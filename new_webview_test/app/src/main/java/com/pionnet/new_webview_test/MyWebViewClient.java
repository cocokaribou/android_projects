package com.pionnet.new_webview_test;

import android.content.Context;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public abstract class MyWebViewClient extends WebViewClient {
    private Context mContext;

    public MyWebViewClient(Context context) {
        this.mContext = context;
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.e("youngin", url);
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    public abstract void showErrorPage();

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        showErrorPage();
    }

    public void setUntouchableProgress(int visible) {
    }
}