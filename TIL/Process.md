### `exitProcess()`
- Application 클래스는 process랑 같이 생성되고 사라짐
- 즉, exitProcess()를 호출하거나 백그라운드에서 실행중인 프로세스를 terminate 하기 전까지 Application 클래스의 초기화 코드는 호출되지 않음

- app_process 이름은 `zygote`이라고 한다