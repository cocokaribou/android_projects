package com.example.web_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    public WebView webViewPopup;

    private String url = "file:///android_asset/www/index.html";
    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true); //이거 안 붙이면 사이트 다 망가지나? -> 똑같음
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl(url);
        webView.loadUrl("javascript:closeWin()");

        WebViewClientClass webViewClient = new WebViewClientClass();
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(new WebChromeClientClass(webViewClient));

        //webview로 구성된 화면에서 js로 alert 메시지를 띄울때, WebViewClient로만 구성하면 메시지가 뜨지않는다
    }

    //웹페이지 뒤로가기 지원하는 함수
    //없으면 뒤로가기 버튼 눌렀을때 앱이 꺼진다
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //뒤로가기 버튼이 눌렸음 && 뒤로갈 페이지가 존재할때
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //커스텀 웹뷰클라이언트
    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            isOpen = true;
            return true;
        }

        //webView로 구성된 화면에서 js로 alert띄울때, webViewClient로만 구성하면 alert창이 안 뜸
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    //커스텀 웹크롬클라이언트
    private class WebChromeClientClass extends WebChromeClient {
        private WebViewClientClass webViewClient;

        public WebChromeClientClass(WebViewClientClass webViewClient) {
            this.webViewClient = webViewClient;
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            webViewPopup = new WebView(MainActivity.this);
            Log.e("hi️", "hi~");

            webViewPopup.getSettings().setJavaScriptEnabled(true);
            webViewPopup.setWebChromeClient(new WebChromeClient() {

                @Override
                public void onCloseWindow(WebView window) {
                    super.onCloseWindow(window);
                    Log.e("hi", "hi~2");
                    window.setVisibility(View.GONE);
                }
            });

            webView.addView(webViewPopup);

            WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
            transport.setWebView(webViewPopup);
            resultMsg.sendToTarget();

            return true;
        }

    }
}