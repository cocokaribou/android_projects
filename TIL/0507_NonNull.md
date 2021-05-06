### Why would findViewById returns null

https://stackoverflow.com/questions/3264610/findviewbyid-returns-null

- Make sure you've called `setContentView()` before `findViewById()`
- Make sure that the `id` you want is in the view or layout you've given to `setContentView()`

- try using `onFinishInflate()` method