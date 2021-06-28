### thread 실습

#### `Thread.run()` vs `Thread.start()`

When a program calls the `start()` method, a new thread is created and then the `run()` method is executed. But if we directly call the `run()` method then no new thread will be created and `run()` method will be executed as a normal method call on the current calling thread itself and no multi-threading will take place.
