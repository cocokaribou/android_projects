## Util 파일

```
com.pionnet.overpass
|__ customView
	|__ dialog
		|__ CustomDialog.kt
	|__ itemDecoration
		|__ GridDividerItemDecoration.kt
		|__ GridSpacingItemDecoration.kt
		|__ HorizontalSpacingItemDecoration.kt
		|__ StickyHeaderItemDecoration.kt
		|__ VerticalSpacingItemDecoration.kt
|__ extension
	|__ TextViewExtension.kt
	|__ ViewExtension.kt
|__ module
	|__ LogHelper.kt
	|__ NetworkManager.kt
|__ util
	|__ DisplayUtil.kt
	|__ SplashUtil.kt
	|__ StringUtil.kt
	|__ TestUtil.kt
```

### customView
- dialog
	- `CustomDialog.kt` 
		- siv,자주,DU와 동일한 UI 적용
		- 빌더 패턴
- itemDecoration
	- RecyclerView.ItemDecoration 상속


|클래스|생성자 param|설명|
|:------:|:---|:---|
|GridDividerItemDecoration|`width:Int` 선 굵기<br>`height:Int` 선 굵기<br>`color:Int` 선 색상|그리드 아이템 사이 구분선|
|GridSpacingItemDecoration|`spanCount:Int` 그리드 행 수<br>`spacing:Int` 아이템 마진<br>`includeEdge:Boolean` 테두리 마진 여부|그리드 아이템 사이 마진 설정|
|HorizontalSpacingItemDecoration|`spacing:Int` 마진 <br>`edgeSpacing:Int` 처음과 끝 마진<br>`includeEdge:Boolean` 처음과 끝 마진 여부|LinearLayoutManager<br>horizontal 마진 설정|
|VerticalSpacingItemDecoration|`spacing:Int` 마진<br>`firstEndSpacing:Int` 처음과 끝 마진<br>`includeTopBottom:Boolean` 처음과 끝 마진 여부|LinearLayoutManager<br>vertical 마진 설정|
|StickyHeaderItemDecoration|`mListener: stickyHeaderInterface`|stickyHeaderInterface 의 메소드 구현<br>`isHeader()`함수 false일 때 헤더 보임|


### extension
- `TextViewExtension.kt`
	- TextView에 정의된 함수들
- `ViewExtension.kt`
	- View에 정의된 함수들
	
### module
유용한 싱글턴 오브젝트 모음
<br>context 주입받아서 init
- `LogHelper.kt`
	- 디버그에서만 출력되는 로그
	- `initTag()`로 디폴트 태그 지정 가능
- `NetworkManager.kt`
	- 네트워크 연결 관리자
	- `checkNetworkAvailable()` : 함수 인자로 `OnNetworkListener` 인터페이스 구현, 네트워크 연결여부 확인

### util
비즈니스 로직 모음
- `DisplayUtil.kt`
	- DisplayMetrics 객체 반환
- `SplashUtil.kt`
	- 앱 도입부에서 쓰이는 비즈니스 로직들
	- 화이트링크, 블락링크 처리
	- 루팅 여부 체크
	- 해시코드 생성
- `StringUtil.kt`
	- 문자열 포맷 (날짜, 가격)
	- Date 객체 처리
- `TestUtil.kt`
	- `getJsonFileToString()` : api 배포전 저장된 json 파일로 테스트할 때