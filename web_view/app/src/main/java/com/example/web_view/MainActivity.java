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
    private String url = "https://www.naver.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        webView = (WebView) findViewById(R.id.webview);


        //myWebView = new WebView(this);
        //myWebView = (WebView) findViewById(R.id.webview); //이렇게 적으면 계속 중단됨

        //webView 객체를 요소로 가져오거나 아니면 뷰그룹의 context로 가져오거나

        //setContentView(myWebView);
        //setContentView(R.layout.activity_main); //위의 코드와 똑같이 크롬으로 리다이렉트

        //아냐 역시 setContentView는 메인을 불러오는 게 맞다 상식적으로도

        webView.getSettings().setJavaScriptEnabled(true); //이거 안 붙이면 사이트 다 망가지나? -> 똑같음
        webView.loadUrl(url);


        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
    }

    //웹페이지 뒤로가기 지원하는 함수
    //없으면 뒤로가기 버튼 눌렀을때 앱이 꺼진다
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()){
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
            return true;
        }
    }
}