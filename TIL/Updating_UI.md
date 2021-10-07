## `NetworkOnMainThreadException`
- Main Thread에서 네트워크 호출하면 발생하는 예외

## `CalledFromWrongThreadException`
- Worker Thread에서 ui 업데이트하면 발생하는 예외

### Main Thread vs Worker Thread
- 앱 = 프로세스 하나 위에 멀티 스레드
- **UI 업데이트는 Main Thread에서 처리한다**
- **네트워크는 Worker Thread 에서 처리한다**

### Network
- <del>AsyncTask 스레드 클래스</del> **deprecated!**
	- <del>doInBackground()</del>
	- <del>onPostExecute()</del>
	
- Handler
	- <del>Handler()</del> **deprecated!**
	- Handler(Looper)

```kotlin
	Handler(Looper.getMainLooper()).postDelayed({
		/* logic here */
	}, 3000)
```

- runOnUiThread()
```kotlin
	btnTest.setOnClickListener {
		runOnUiThread {
			tvTest.text = "update ui"
		}
	}
```