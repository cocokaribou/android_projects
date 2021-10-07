## 개요
- 외부업체의 sdk 없이 사내 서버에서 푸시 전송하는 솔루션개발에 앞서 테스트를 위한 데모앱 제작
- FCM에서 token을 받아서 DB에 저장
- 수신측(안드로이드 앱)에서 알림채널 커스텀

## 앱구조
- MainActivity
	- `NotificationManagerCompat` 시스템 알림 활성화 여부 체크
	- `FirebaseMessaging` 인스턴스 생성
	- token 값 api에 전송
- MyFirebaseMessagingService
	- 메시지 수신 후 채널 생성, MainActivity로 이동
- MyAPI
	- token 등록 api 구현
	

## 헤맸던 점
- fcm 인스턴스 생성 함수 deprecated?
	- `FirebaseInstanceId` 대신 `FirebaseMessaging.getInstance()` 사용
	
- apk 추출한 뒤 빌드 에러 남
	- 디렉터리에서 apk 파일 삭제, 혹은 `rm -r .gradle`
	
- api 통신이 안 됨
	- http로 시작하는 api라 android:usesCleartextTraffic="true" 설정
	- callback을 제대로 호출하기
	- 로그찍고 무작정 빌드하지 말고 Junit test 사용해보기
	
- 콘솔로 메시지를 발송하면 `FirebaseMessagingService::onMessageReceived()`가 호출 안됨
	- 콘솔로 메시지 전송할 경우, `message.notification` 값이 있는 notification message로 분류됨
	- notification message는 *앱이 포그라운드에 있을 때만* `onMessageReceived()`를 호출

- 알림메시지 스타일 커스텀하기
	- data message로 데이터 payload에 따라서 알림채널 생성
	- 알림채널에 스타일 적용


## 참고 사이트
- [안드로이드 앱 FCM 푸시 알림 예제](https://blog.naver.com/PostView.naver?blogId=ndb796&logNo=221553341369&redirect=Dlog&widgetTypeCall=true&directAccess=false)
- [Firebase 메시지 전송](https://team-platform.tistory.com/23)