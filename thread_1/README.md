### thread 실습

#### `Thread.run()` vs `Thread.start()`

When a program calls the `start()` method, a new thread is created and then the `run()` method is executed. But if we directly call the `run()` method then no new thread will be created and `run()` method will be executed as a normal method call on the current calling thread itself and no multi-threading will take place.

#### How to update UI on Android
- `Handler`, `Looper`
- `runOnUiThread()`
- `AsyncTask` <s>deprecated?</s>

#### coroutine
코루틴과 스레드의 차이점:
https://aaronryu.github.io/2019/05/27/coroutine-and-thread/
https://3dmpengines.tistory.com/2035
gisdeveloper.co.kr/?p=10279
https://heegyukim.medium.com/%EC%BD%94%ED%8B%80%EB%A6%B0%EC%9D%98-%EC%BD%94%EB%A3%A8%ED%8B%B4-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-%EC%9C%84%ED%95%9C-%EC%8B%A4%ED%97%98-8e4bdd1942df

