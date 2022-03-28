## Loading web url in android app

|||
|:--|:--|
|external browser|heavy context switch|
|in-app webview|doesn't support all features of the web platform, doesn't share state with the browser|

## Chrome custom tab
- open web urls within the context of your application using the chrome browser installed on the device.
- gives app more control over their web experience, and make transitions between native and web content more seamless without having to resort to a webview

## Chrome custom tab vs Webview
- can receive [CustomTabsCallback](https://developer.android.com/reference/androidx/browser/customtabs/CustomTabsCallback) from activity
- customizeable toolbar
- faster loading time compared to external browser, in-app webview
- shared cookie, permission
- [reference](https://aroundck.tistory.com/6031)