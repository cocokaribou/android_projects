## Util 파일

```
com.pionnet.overpass
|__ customView
	|__ dialog
	|__ itemDecoration
|__ extension
|__ module
|__ util
```

### customView
- dialog
	- `CustomDialog.kt` 
		- siv,자주,DU에 적용
	- `TooltipDialog.kt` 
		- 자주에 적용
- itemDecoration
	- 리사이클러뷰 아이템 데코레이션
	
### extension
유용한 함수 모음
<br>package명 없이 import 
- `StringFormat.kt`
	- 문자열을 포맷해서 리턴하는 함수들
- `TextViewExtension.kt`
	- TextView에 정의된 함수들
- `ViewExtension.kt`
	- View에 정의된 함수들
	
### module
유용한 싱글턴 오브젝트, 클래스 모음
<br>context 주입받아서 init
- `DataManager.kt`
	- 앱 전역변수, 리터럴, 프리퍼런스, 로그인/로그아웃시 쿠키 처리 등
	- 입력해야될 부분 TODO로 표기
- `LogHelper.kt`
	- 디버그에서만 출력되는 로그
	- `initTag()`로 디폴트 태그 지정 가능
- `PaymentModule.kt`
	- 결제모듈
	- 입력해야될 부분 TODO로 표기
- `NetworkManager.kt`
	- 네트워크 연결 관리자

### util
비즈니스 로직 모음
- `SplashUtil.kt`
	- 앱 도입부에서 쓰이는 비즈니스 로직들
	- 랜딩주소 처리 (딥링크, 푸시), 루팅여부체크, 해시코드 생성 등
- `TestUtil.kt`
	- api 배포전 저장된 json 파일로 테스트할 때