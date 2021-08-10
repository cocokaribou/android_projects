## Context
- application 환경에 대한 전역 정보에 접근하기 위한 인터페이스
- Activity 실행, Intent 브로드캐스팅, 그리고 Intent 수신 등과 같은 응용프로그램 수준의 작업을 수행하기 위한 api를 호출할 수 있다.

## Application Context VS Activity Context
- Application Context : Application 생명주기
- Acticity Context : Activity 생명주기<br>
`this`는 activity context를 가리킴

## Context type
 |**Application**|**Activity**|**Service**|**ContentProvider**|**BroadcastReceiver**
:-----:|:-----:|:-----:|:-----:|:-----:|:-----:
Show a Dialog|x|o|x|x|x
Start an Activity|x|o|x|x|x
Layout Inflation|x|o|x|x|x
Start a Service|o|o|o|o|o
Bind to a Service|o|o|o|o|x
Send a Broadcast|o|o|o|o|o
Register BroadcastReceiver|o|o|o|o|x
Load Resource Values|o|o|o|o|o
