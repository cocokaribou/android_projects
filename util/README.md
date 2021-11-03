## PionFramework_aOS Util
```
com.pionnet.overpass
|__ customView
|__ extension
```

## customView
- RecyclerView.ItemDecoration 상속

|클래스|생성자 param|설명|
|:------:|:---|:---|
|GridDividerItemDecoration|`width:Int` 선 굵기<br>`height:Int` 선 굵기<br>`color:Int` 선 색상|그리드 아이템 사이 구분선|
|GridSpacingItemDecoration|`spanCount:Int` 그리드 행 수<br>`spacing:Int` 아이템 마진<br>`includeEdge:Boolean` 테두리 마진 여부|그리드 아이템 사이 마진 설정|
|HorizontalSpacingItemDecoration|`spacing:Int` 마진 <br>`edgeSpacing:Int` 처음과 끝 마진<br>`includeEdge:Boolean` 처음과 끝 마진 여부|LinearLayoutManager<br>horizontal 마진 설정|
|VerticalSpacingItemDecoration|`spacing:Int` 마진<br>`firstEndSpacing:Int` 처음과 끝 마진<br>`includeTopBottom:Boolean` 처음과 끝 마진 여부|LinearLayoutManager<br>vertical 마진 설정|
|StickyHeaderItemDecoration|`mListener: stickyHeaderInterface`|stickyHeaderInterface 의 메소드 구현<br>`isHeader()`함수 false일 때 헤더 보임|

## extension
- 자주 쓰는 View 유틸 모음

#### - TextExtension
|메소드|param|return|설명|
|:------|:---|:---|:---|
|Date.toSimpleString()||`String` 포맷된 날짜|현재 날짜 출력|
|getCurrentTime()||`String` 포맷된 현재시각|현재시각 출력|
|getAddDateString()|`format:String` 날짜 포맷형식<br>`addDate:Int` 계산할 일수|`String` 계산된 날짜|입력일수 만큼 현재 날짜에서 계산|
|priceFormat()|`value:String` 가격|`String` 포맷된 가격|천 단위 포맷해서 출력<br>ex) 1000 -> 1,000|
|productCnt()|`value:Long` 만 단위 상품개수|`String` 포맷된 상품개수|만 단위 포맷해서 출력<br>ex) 2500000 -> 250만+|
|TextView.setPriceStroke()|`size:Int` '원' 글자크기<br>`isExist: Boolean` '원' 글자 처리유무||텍스트뷰(원 단위 가격)에 취소선 처리|
|TextView.setBoldText()|||텍스트뷰 볼드 처리|
|TextView.setUnderLine()|||텍스트뷰 언더라인 처리|

#### - ViewExtension
|메소드|param|설명|
|:------|:---|:---|
|ImageView.loadImage()|`url:String` 이미지 url|이미지 출력|
|ImageView.loadImageWithScale()|`url:String`<br>`width:Int`<br>`height:Int`|이미지 스케일 지정해서 출력|
|ImageView.loadImageWithOriginal()|`url:String`|이미지 원본크기 출력|
|ImageView.loadImagePreLoad()|`url:String`|캐싱된 이미지 출력|
|ImageView.loadImageCircle()|`url:String`<br>`width:Int` 가변인자<br>`height:Int` 가변인자|이미지 원형으로 출력|
|preLoadSplash()|`context:Context`<br>`url:String`|스플래시 이미지 출력|
|View.setLayoutWeight()|`weight:Float`|linearLayout에서 weight 값 적용<br>전체 weight 1로 고정|
|View.margin()|`left:Float?` 가변인자<br>`top:Float?` 가변인자<br>`right:Float?` 가변인자<br>`bottom:Float?` 가변인자|뷰에 마진 적용<br>어느 변에 적용할지 인자에서 할당 `View.margin(right=inputFloat)`|
|View.setDynamicHeight()|`value:Float`|뷰 높이값 적용|
|View.setDynamicWidth()|`value:Float`|뷰 너비값 적용|
|ConstraintLayout.setHorizontalBias()|`tragetViewId: Int` 적용할 뷰의 리소스값<br>`bias:Float` bias값|horizontal에서 bias 값 적용<br>전체 1로 고정|

#### - AnimationExtension
|메소드|param|설명|
|:------|:---|:---|
|expand()|`v:View` 확장할 뷰|레이아웃 확장|
|collapse()|`v:View` 닫을 뷰|레이아웃 닫기|

#### - BusinessExtension
|메소드|param|return|설명|
|:------|:---|:---|:---|
|isUpdate()|`appVer: String` 앱 프로젝트 버전<br>`newVer: String` 서버에서 받는 앱 버전|`true` 업데이트 진행<br>`false` 업데이트 무시|업데이트 유무|
|setSplashImgUrl()|`imgList:List<String>`이미지 데이터<br>`isRandom:Boolean` 이미지 랜덤출력 여부|`String` 이미지 데이터|스플래시 데이터 출력|
|getAppVersion()|`context:Context` 버전정보 가진 컨텍스트|`String` 버전이름|프로젝트 앱 버전|

#### - DisplayExtension
|메소드|param|return|설명|
|:------|:---|:---|:---|
|getDisplaySize()|`activity:Activity`|`DisplayMetrics` 해상도 객체|해상도 구하기<br>(density, width, height, xdpi, ydpi)|

#### - JsonExtension
|메소드|param|return|설명|
|:------|:---|:---|:---|
|isUpdate()|`filePath: String` 하드코딩된.json 파일<br>`context: Context` 컨텍스트|`String` 문자열화 된 json|json 데이터를 문자열로 출력<br>|

