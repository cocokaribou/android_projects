## WebView
`android.webkit.WebView`<br>
웹페이지를 보여주는 뷰.

### Manifest
- 매니페스트에 인터넷 권한 추가
`<use-permission android:name="android.permission.INTERNET" />`
- `http` 프로토콜에 접속하기 위해 매니페스트의 `<application>` 에 `android:usesCleartextTraffic="true"`로 설정

- 혹은 매니페스트에 네트워크 보안 구성 파일 생성한 뒤 매니페스토의 `<application>`에 추가

res/xml/network_security_config.xml
```xml
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">example.com</domain>
    </domain-config>
</network-security-config>
```
AndroidManifest.xml

```
<application 
	android:networkSecurityConfig="@xml/network_security_config" 
	...
	>

</application>
```

### WebChromeClient
- 경고표시, 윈도우 닫기 등 웹 브라우저의 이벤트를 처리
- 웹 페이지 자체가 아닌 웹 페이지를 담은 틀, "브라우저 크롬"에 특화


### WebViewClient
- WebView 를 통해 html 링크를 클릭하거나 링크에 따른 내용, 에러, 폼 등록의 렌더링
<br>
![reference](https://m.blog.naver.com/PostView.nhn?blogId=credenda&logNo=80120157762&proxyReferer=https:%2F%2Fwww.google.com%2F)


### Hybrid App
- 0세대: 웹페이지의 CSS를 이용해서 앱처럼 보이게 하는 것
- 1세대: Native App, 앱 안에 WebView를 띄운다. Javascript와 Java/Kotlin의 메소드를 연결해서 핸드폰의 기능을 사용.
- 2세대: React Native, PWA앱
<br>
![reference](https://lcw126.tistory.com/218)


## 웹에서 데이터 받아오기

### Android Bridge
- `@JavascriptInterface` 어노테이션을 붙인 뒤 함수 생성
- 웹에서 협약된 이름의 자바스크립트 함수를 실행하면 호출됨
```javascript
window.location = “sample://action?argument1=2&argument2=2”
```


### scheme
- 웹단 `<a>`태그의 `href` 요소를 처리
- `WebViewClient`의 `shouldOverrideLoading()` 함수에서 처리한다

```java
public boolean shouldOverrideUrlLoading(WebView view, String url) {
	listener.shouldOverrideUrlLoading(url);
	
	//전화 걸기
	if (url.startsWith(”tel:”)) {
		Intent intent = new Intent(Intent.ACITION_DIAL, Uri.parse(url));
		mContext.startActivity(intent);
		reload();
		return true;
	}
}

```


## `android.webkit`
- Provide tools for browsing the web.


## Chromium
- a free and open-source codebase for a web browser
- Microsoft Edge, Opera and many other browsers are based on the Chromium code