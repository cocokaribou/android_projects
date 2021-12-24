## Fragment
- master-detail 구조를 갖기 쉽다
- fragment -> navigation drawer, bottom sheet dialog, navigation component etc

## Fragment vs Activity
- 프래그먼트는 단독으로 존재할 수 없으며, 반드시 액티비티 내에서 호스팅 되어야 한다
- 액티비티 스택에 액티비티를 쌓아두는 것보다 프래그먼트 백스택에서 프래그먼트를 관리하는 것이 메모리 관리 차원에서 효율적이다
- 화면전환도 액티비티보다 순조롭게 가능
- [reference](https://charlezz.medium.com/activity-vs-fragment-%EB%AC%B4%EC%97%87%EC%9D%84-%EC%84%A0%ED%83%9D%ED%95%B4%EC%95%BC-%ED%95%A0%EA%B9%8C-56ce7fa2bfc4)

## data 주고받기
- Activity
	1. intent
	2. application 단
	- 액티비티는 다른 프로세스에서 실행하는 것을 염두하고 설계되었기 때문에 <u>메모리 영역을 공유하지 않는다</u>
- Fragment
	- 프래그먼트간 데이터 공유는 <u>액티비티 내에서</u> 자유롭게 이뤄진다

## 추가
- 구글은 single-activity 앱 구조를 권장하고 있다!
- 읽어보기 -> [Migrate to the Navigation component](https://developer.android.com/guide/navigation/navigation-migrate)