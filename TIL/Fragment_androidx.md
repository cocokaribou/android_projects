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
- RxJava2 이용한 이벤트 버스
- 액티비티 단에서 데이터 저장
- [reference](https://www.charlezz.com/?p=1062)