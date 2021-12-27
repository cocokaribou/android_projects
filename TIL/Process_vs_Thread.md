## Process
is similar to linux process.<br>
app will usually only be working within one single process.<br>


- `foreground process`
	- is required for what the user is currently doing.
	- runs an `Activity` at the top of the screen.
	- has a `BroadcastReceiver` that is currently running.
	- has a `Service` that is currently executing the code in one of its callbacks.
	
- `visible process`
	- does work that the user is currently aware of(UI)
	- runs an `Activity` that is visible to the user on-screen but not in the foreground(`onPause()`).
	- has a `Service` that is running as a foreground service.
	
- `service process`
	- holds a `Service` that has been started with the `startService()` method.
	- though being not visible to the user, system will always keep such processes running unless there's not enough memory
	
- `cached process`
	- <u>the one that is not currently needed.</u>
	- `threads` and `tasks` in android.

### `exitProcess()`
- Application 클래스는 process랑 같이 생성되고 사라짐
- 즉, exitProcess()를 호출하거나 백그라운드에서 실행중인 프로세스를 terminate 하기 전까지 Application 클래스의 초기화 코드는 호출되지 않음

- app_process 이름은 `zygote`이라고 한다

### process vs thread
- a process will at least have one thread(Main thread in Android)


## Thread
when the thread is blocked, no events can be dispatched, including drawing events<br>

- `main thread`
	- aka `UI thread`
	- dispatches events to the user interface widgets
	- methods that respond to system callbacks(ex: `onKeyDown()`) always run in the UI thread.
	- single thread model : Don't block the UI thread, Don't access Android UI toolkit outside the UI thread.

- `worker thread`
	- aka `background` or `worker` thread
	- cannot update UI from the worker thread
	- several ways to access the UI thread from the other threads: Handler
		- `Activity.runOnUiThread(Runnable)`
		- `View.post(Runnable)`
		- `View.postDelayed(Runnable, long)`
 
[reference](https://developer.android.com/guide/components/processes-and-threads)

### handler
- allows you to send and process Message and Runnable objects associated with a thread's MessageQueue.
