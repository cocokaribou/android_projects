# QnA
> 궁금한 점을 하나씩 적고 하나씩 해소해나가기<br>
> 사소한 거라도 일단 적어두기

#### `android`와 `androidx`의 차이점은?
<br><br>
#### `android.support.v7.widget.RecyclerView`와 `androidx.recyclerview.widget.RecyclerView`의 차이점은?
<br><br>

#### payload란?
- 특정 position의 holder를 업데이트할 때 payload값으로 구분할 수 있다.
<br><br>

#### 인자에 @NonNull을 붙이는 경우는?
<br><br>
#### 왜 안드로이드의 리소스들은 Int값인지?
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
<br><br>

#### 프로세스와 스레드의 관계는?
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


#### 안드로이드는 프레임워크인가, os인가?
- Android is an open-source software stack for mobile devices that includes an operating system, middleware and key applications. So you are partially correct in considering it as a framework. - [reference](https://softwareengineering.stackexchange.com/questions/51769/is-android-a-language-or-a-framework-platform)
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
