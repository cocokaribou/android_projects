# QnA
> 궁금한 점을 하나씩 적고 하나씩 해소해나가기<br>
> 사소한 거라도 일단 적어두기

#### `android`와 `androidx`의 차이점은?
- `android` : original android support library, ships separately from android os
- `androidx` : major improvement to the android library, jetpack 을 구현한 것
- jetpack : set of libraries, tools and architectural guidance for building android apps
<br><br>

#### `android.support.v7.widget.RecyclerView`와 `androidx.recyclerview.widget.RecyclerView`의 차이점은?
- migrated into the androidx library
<br><br>

#### payload란?
- 특정 position의 holder를 업데이트할 때 payload값으로 구분할 수 있다.
- CS: the part of transmitted data that is the actual intended message. [reference](https://en.wikipedia.org/wiki/Payload_(computing))
<br><br>

#### 인자에 @NonNull을 붙이는 경우는?
- java 문법, kotlin 은 nullable 자료형이 존재
<br><br>

#### 왜 안드로이드의 리소스들은 Int값인지?
- R.java 파일에 int로 존재
- `gradle 7.0`, AndroidStudio@ArcticFox 에서 R.java 위치
```
app/build/intermediates/compile_and_runtime_not_namespaced_r_class_jar/debug/R.jar
```
<br><br>

#### RecyclerView의 Adapter에서 `onCreate()`와`onBind()`는 언제 호출되는지?
- `onCreate`는 ViewHolder(RecyclerView의 아이템)을 생성해서 return.<br>
- `onBind`는 data와 ViewHolder를 연결해서 화면에 실질적으로 그려줌.
<br><br>

#### ViewHolder란?
- ListView / RecyclerView는 inflate를 최소화하기 위해서 뷰를 재활용함. 각 뷰의 내용을 업데이트 할 때마다 `findViewById`를 호출하면 성능이 떨어지기 때문에 ItemView의 각 요소를 바로 액세스할 수 있도록 저장해두고 사용하기 위한 객체.
<br><br>

#### inflate란?
- xml로 쓰여있는 view의 정의를 실제 view 객체로 만드는 것
<br><br>

#### coroutine이란?
- [reference](https://kotlinlang.org/docs/coroutines-overview.html)
- for asynchronous or non-blocking programming
- **an instance of suspendable computation**
- 경량 스레드
<br><br>

#### 프로세스와 스레드의 관계는?
- Threads are tasks that share same resources. Processes are tasks which have independent resources.
- a process can have multiple threads
- [reference](https://stackoverflow.com/questions/44651226/difference-between-process-activity-threads-and-tasks-in-android#:~:text=Threads%20are%20tasks%20that%20share,are%20the%20instructions%20being%20executed)
<br>

- the Android system starts a new Linux process with a single thread of execution.
- by default all components run in the same process and thread("main" thread)
- if an application component starts and there already exists process for that application, then the component is started *within* that process and uses the same thread of execution
- however you can arrange for different components in your application to run in separate processes
- [reference](https://developer.android.com/guide/components/processes-and-threads)
<br><br>

#### surfaceView란?
<br><br>

#### view를 `findViewById`로 inflate해서 생성하는 것과 생성자로 생성하는 것의 차이는? 그냥 view 커스텀 클래스가 있으면 생성자로 만들 수 있는건가.
- 솔직히 질문이 잘 이해가 안 된다. 다음에 다시 정리
- LayoutInflater: Instantiates a layout xml file into its corresponding `View` objects.
- `LayoutInflater(Context context)` Create a new LayoutInflater instance associated with a particular Context.

|method|description|
|:---|:---|
|`from(Context context)`|Obtains the LayoutInflater from the given context.|
|`inflate(int resource, ViewGroup root, boolean attachToRoot)`|Inflate a new view hierarchy from the specified xml resource.|
<br><br>

#### 안드로이드는 프레임워크인가, os인가?
- Android is an open-source software stack for mobile devices that includes an operating system, middleware and key applications. So you are partially correct in considering it as a framework.
- [reference](https://softwareengineering.stackexchange.com/questions/51769/is-android-a-language-or-a-framework-platform)
<br><br>

#### 왜 `onStart()`없이 `onCreate()`만 있어도 잘 돌아갈까?
- `onCreate` 앱을 처음 실행할 때 초기화하면서 호출됨
- `onStart` 앱을 처음 실행할 때 & 앱이 다시 실행돼서(resume) 눈에 보일때마다(visible) 호출됨
- `AppCompatActivity`를 상속하는 Activity 클래스에서 모든 함수를 다 오버라이드 할 필요는 없다.
<br><br>

#### 모든 앱의 진입점은 MainActivity인가?
- manifest의 activity>intent-filter에서 정의
```xml
<action android:name="android.intent.action.MAIN"/>	
<category android:name="android.intent.category.LAUNCHER"/>
```
<br><br>

#### 왜 뷰 홀더에서 어댑터를 참조하지 못하는지?
- 뷰 홀더는 화면에 표시될 아이템 뷰를 저장하는 객체. 어댑터에 의해 관리되고 필요에 따라 어댑터에서 생성된다. 미리 생성된 뷰홀더 객체가 있는 경우에는 단순히 데이터가 뷰 홀더의 아이템 뷰에 바인딩됨.
[reference](https://recipes4dev.tistory.com/154)
- 이유가 딱히 있는걸까..?
<br><br>


#### `lateinit` vs `companion object`
- lateinit은 초기화가 나중에 되는 인스턴스 변수이다.<br>
- `lateinit` non-null type, properties can be initialized through dependency injection. 
- checking whether a lateinit var is initialized
```kotlin
if(foo::bar.isInitialized){
  println(foo.bar)
}
```
- companion object는 클래스 내부에 정의되는 object(싱글턴 객체 하나 있는 클래스)이다. 클래스가 메모리에 적재될 때 함께 생성된다
- `companion object` if you declare a variable or a function `companion obejct` inside your class, you can access its members using only the class name as a qualifier.

```kotlin
MyClass.Companion.myProp
MyClass.myProp

//companion object는 객체이다. 
//클래스명으로도 접근 가능한 건 축약 표현이지, java의 static과 혼동말것!
```
<br><br>

#### kotlin에는 static method가 없다?
- 없음
> Note that, even though the members of companion objects look like static members in other languages, at runtime those are still instance members of real objects, and can, for example, implement interfaces. - [reference](https://kotlinlang.org/docs/object-declarations.html#companion-objects)
<br><br>

#### Handler란?
- work thread에서 main thread로 메시지를 전달해주는 역할을 하는 클래스.
- 핸들러는 핸들러 객체를 만든 스레드와 해당 스레드의 message queue에 바인딩된다.
<br><br>

#### `adapterPostion` vs `layoutPosition`
<br><br>


#### 왜 네트워크 통신을 main thread에서 못하는지
- API level 11부터 deprecated됨
- Main thread는 UI thread라고도 불림, 뷰와 위젯에 이벤트를 적용시키는 스레드.
- 버튼을 스크린에서 터치 -> UI thread가 터치 이벤트를 뷰에 dispatch(다중 프로그래밍 시스템에서 다음에 처리될 작업을 선택하여 실행시키는것) -> 터치 상태를 설정하고 이벤트 큐에 요청 포스트 -> UI thread가 이 요청을 큐에서 해제(dequeue)한뒤 뷰에 그리도록 함 - [reference](https://www.androiddesignpatterns.com/2012/06/app-force-close-honeycomb-ics.html)
- Main thread에서는 긴 작업을 지양하고 Worker thread에서는 UI작업을 하지 않아야 한다
- 왜냐하면 화면을 그리는 시간이 늦어지니까 - [reference](https://holika.tistory.com/entry/%EB%82%B4-%EB%A7%98%EB%8C%80%EB%A1%9C-%EC%A0%95%EB%A6%AC%ED%95%9C-%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%99%9C-UI-Thread%EC%97%90%EC%84%9C%EB%8A%94-%EA%B8%B4-%EC%9E%91%EC%97%85%EC%9D%84-%ED%95%98%EB%A9%B4-%EC%95%88-%EB%90%98%EB%8A%94-%EA%B1%B8%EA%B9%8C)


#### Android SDK vs React Native

- Android SDK : Android Development, Necessary for Android, Android Studio
- React Native : Learn once write everywhere, Cross platform, Javascript
- Both : Frameworks(Full Stack), Cross-Platform Mobile Development


#### add fragment on activity VS add fragment on another fragment

- utilize one fragment container
```kotlin
//fragment
(activity as MainActivity).supportFragmentManager.commit {
	setReorderingAllowed(true)
    add<StoreListFragment>(R.id.container, getString(R.string.storeListFragment))
    addToBackStack(null) // add to stack
}
```