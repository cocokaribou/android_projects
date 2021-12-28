## Fragment
`androidx.fragment.app`<br>
reusable portion of UI <br>
[doc](https://developer.android.com/guide/fragments)

### `add()` vs `replace()`
```kotlin
supportFragmentManager.beginTransaction()
    .replace<SomeFragment>(R.layout.fragment_container)
    .commit()
```
- `add()` : add a fragment to the associated FragmentManager, inflating the Fragment's view into the container view
- `replace()` : add a fragment to a container and then *remove all the currently added fragments*



### `addToBackStack()`
- transaction *will be remembered* after it is committed, and will reverse its operation when popped off the stack
- `setReorderingAllowed()` must be set to `true`
<br><br>
- beginTransition ~ commit 까지의 전환, **transaction** 자체를 저장하고 있다
- add/replace 뿐만 아니라 hide/show 등의 처리도 Back키로 복귀 가능하다
- `FragmentManager.getBackStackCount()`로 카운트 얻기
- `popBackStack()` 비동기
- [reference](https://dwenn.tistory.com/42)
- [reference](https://zzandoli.tistory.com/55)

### `setReorderingAllowed()`
- allow optimizing operations within and across transactions
- remove redundant operations, eliminating operations that cancel
- 트랜잭션과 관련된 프래그먼트의 상태변경을 최적화


### handling data from fragment to fragment
- ViewModel
	- 뷰모델 클래스 추가
	- FragmentA, FragmentB의 onActivityCreated()에 뷰모델 인스턴스 추가
	- LiveData를 observe로 얻는다
- Interface 이용한 리스너 구현
	- 인터페이스 생성
	- 전달하는 곳에 리스너 초기화하고 메서드 선언만 함
	- 전달 받는 곳에서 리스너 구현하고 implements 메서드 구현함
- bundle
```kotlin
// fragment a
val payload = "test"
val bundle = bundleOf("bundle_key" to payload)
supportFragmentManager.commit{
    setReorderingAllowed(true)
    add<RootFragment>(R.id.fragment_container_view, args=bundle)
}

// fragment b
val initString = requireArguments().getString("bundle_key")

``` 

- RxJava2 이용한 이벤트 버스
- 액티비티 단에서 데이터 저장
- [reference](https://www.charlezz.com/?p=1062)

### `commitAllowingStateLoss()`

- `java.lang.IllegalStateException`
- splash screen을 액티비티에서 `commit()`하고, 앱을 화면 끈 채로 실행하면(debug) 앱이 죽는 현상
- `FragmentTransaction.commit()`을 `Activity.onStop()`(not visible) 이후로 호출하면 나오는 에러 에러
<br><br>
![image](/TIL/resources/commit.png)
- activity, fragment가 resume됐는지 체크
- 유저가 앱을 가시화할때까지 지연시키기
- `commitAllowingStateLoss()` 
	- transaction에 날아가면 안되는 데이터가 없다
	- onStop(), onSaveInstanceState() 이후에 fragment가 보이지 않아도 괜찮다
	- ex) onStop() 이후에 commitAllowingStateLoss 호출, 백그라운드에 있는데 메모리가 없어서 앱프로세스가 kill 됨 -> transaction state loss
- [reference](https://medium.com/inloopx/demystifying-androids-commitallowingstateloss-cb9011a544cc)
	
### `commitNow()`
- `commit()` : 즉시 수행되지 않고, 다음 스레드가 준비될때 메인 스레드에 대한 작업으로 *예약*된다. 즉, 실제로 트랜잭션(fragment 추가, 제거, 교체)이 발생하지 않는다.
- `executePendingTransactions()` : commit한 현재 보류중인 모든 트랜잭션 실행
- `commitNow()` : 실제 사용자가 실행하고자 하는 것보다 더 많은 트랜잭션을 실수로 실행할 수 없게 함. <u>backstack은 사용할 수 없다.</u>
<br><br>
![image](/TIL/resources/commitnow.png)
- [reference](https://pluu.github.io/blog/android/2017/01/26/fragmentTransaction/)
- [reference](https://medium.com/hongbeomi-dev/%EB%B2%88%EC%97%AD-%EB%8B%A4%EC%96%91%ED%95%9C-%EC%A2%85%EB%A5%98%EC%9D%98-commit-8f646697559f)