## Context
- application 환경에 대한 전역 정보에 접근하기 위한 인터페이스
- Activity 실행, Intent 브로드캐스팅, 그리고 Intent 수신 등과 같은 응용프로그램 수준의 작업을 수행하기 위한 api를 호출할 수 있다.

## Application Context VS Activity Context
- Application Context : Application 생명주기
- Acticity Context : Activity 생명주기<br>
`this`는 activity context를 가리킴

|||
|:------|:---|
| `this` | activity context |
|`getContext()`| activity context |
| `getApplication()` | application context |
| `getApplicationContext()` | application context |

## Context type
![image](/TIL/resources/context.png)

[reference](https://stackoverflow.com/questions/3572463/what-is-context-on-android)

## Application Context는 잘 쓰이지 않는다?
- 액티비티가 수행하는 모든 걸 지원하는 완전한 컨텍스트가 아님
- 이 컨텍스트로 수행하려는 작업은 대부분 GUI 관련된 에러로 실패한다
- 라이프사이클이 애플리케이션 전역이므로, 메모리 누수가 발생할 수 있다
- [reference](https://onlyfor-me-blog.tistory.com/235)