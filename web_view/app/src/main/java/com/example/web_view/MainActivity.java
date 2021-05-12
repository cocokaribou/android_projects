package com.example.web_view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    //private String url = "https://youtube.com";
    private String url = "file:///android_asset/www/index.html"; //<script> alert("test"); </script>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webView = (WebView) findViewById(R.id.webview);


        //myWebView = new WebView(this);
        //myWebView = (WebView) findViewById(R.id.webview); //이렇게 적으면 계속 중단됨

        //webView 객체를 요소로 가져오거나 아니면 뷰그룹의 context로 가져오거나

        webView.getSettings().setJavaScriptEnabled(true); //이거 안 붙이면 사이트 다 망가지나? -> 똑같음
        webView.loadUrl(url);


        webView.setWebViewClient(new WebViewClientClass());
        webView.setWebChromeClient(new WebChromeClient());

        //webview로 구성된 화면에서 js로 alert 메시지를 띄울때, WebViewClient로만 구성하면 메시지가 뜨지않는다

    }
    //웹페이지 뒤로가기 지원하는 함수
    //없으면 뒤로가기 버튼 눌렀을때 앱이 꺼진다
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        //뒤로가기 버튼이 눌렸음 && 뒤로갈 페이지가 존재할때
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //커스텀 웹뷰클라이언트
    private class WebViewClientClass extends WebViewClient {
        //@Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }

        //webView로 구성된 화면에서 js로 alert띄울때, webViewClient로만 구성하면 alert창이 안 뜸
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }
}