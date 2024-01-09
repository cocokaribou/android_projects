## In-app Logview for Android App

### Preview
<p align="center">
	<img src="preview.gif" width="300">
</p>

Custom Logview for debugging and qa.<br>
Works on top of Retrofit2 + OkHttp3 library interfaces.<br>

- collapsible, translucent logview UI on top of Activity UI.
- saving log text file to internal storage.
- searching log messages by keywords.
- (currently working) widget for various debugging tools.

|functions|description|
|:------|:---|
| `a(request: Request)` | api request log<br>(header, query, field) |
| `a(response: Response)`| api response log<br>(response json)|
| `d(msg: String)` | DEBUG log |
| `i(msg: String)` | INFO log |
| `w(msg: String)` | WARNING log |
| `e(msg: String)` | ERROR log |
