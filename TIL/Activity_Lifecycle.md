## Activity Lifecycle
액티비티는 `onCreate()`에서 `onDestroy()`까지 모든 생명주기 메서드를 거친다.

### `android.app.Activity`
- 생명주기와 관련있는 함수들
	- onCreate(Bundle)
	- onStart()
	- onResume()
	- onPause()
	- onStop()
	- onDestroy()
- 그 밖의 유용한 함수들
	- onSaveInstanceState(Bundle)
	- startActivity(intent)
	- setContentView(View)
	
### onCreate()
- 앱이 초기화될 때 한 번 호출됨
- `setContentView(View)` : Activity의 UI를 inflate 한다.
- `findViewById(Int)` : 위젯을 조작하기 위해 객체화한다 (혹은 ViewBinding)
- 로케일, 물리 디바이스 구성과 같은 옵션을 저장하고 있음
- 이러한 설정이 바뀌었을 때 기존의 activity를 종료시키고 다시 실행
- `onSaveInstanceState()` : onCreate() 이전에 호출돼서 상태를 저장. **그러나 activity 생명주기에는 미포함**


### onStart()
- 앱이 재실행될 때(눈에 *보이게* 됐을 때) 호출됨
- `onStop()` -> `onRestart()` 순서로 호출됨

### onStop()
- 앱이 사용자 눈에 안 보이게 됐을 때 호출됨
- `onStop()` -> `onStart()` 앱이 안보였다가 다시 보이게 됨
- `onStop()` -> `onDestroy()` 앱이 안 보였다가 종료됨

### onResume()
- 액티비티가 포커스를 잃었다가 다시 받을 때 호출됨
- `onStart()` -> `onResume()`
- `onRestoreInstanceState()` -> `onResume()`
	
### onPause()
- 액티비티가 포커스를 잃었을 때 호출됨
- 유지하고 싶은 데이터를 여기서 저장. 


## Q. Application lifecycle vs Activity lifecycle
- `android.app.Application`
- 앱 가동시 Application 클래스가 가장 먼저 초기화됨
- 어플리케이션 상태에 반응하는 함수들
	- onCreate()
	- onLowMemory()
	- onTrimMemory()
	- onConfigurationChanged()
	- etc

- You have to override `Activity::onPause()`, `Activity::onResume()`and save/restore your data there. Application will never restore on its own if there is an activity killed. [reference](https://stackoverflow.com/questions/8092956/android-application-lifecycle-and-activities) 
