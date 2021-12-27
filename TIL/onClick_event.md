## onClickListener를 등록하는 방법

### 1. Layout에 이벤트 추가
- Layout 구성하면서(xml file) `Button`에 이벤트 등록
```xml
android:onClick = "btnClick" <!-- 코드에서 사용할 함수명 -->
```

### 2. 버튼 인스턴스 생성하면서 OnClickListener 구현
```java
Button testBtn = (Button)findViewById(R.id.btn);
testBtn.setOnClickListener(new View.OnClickListener(){
    @Override
    public void onClick(View v){
    	/* logic here */
    }
});
```

### 3. OnClickListener interface 구현
- `View.OnClickListener`를 implements
```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ...
    
    Button testBtn = (Button)findViewById(R.id.btn);
    btn.setOnClickListener(this);

    @Override
    public void onClick(View v){
        if(v.getId == R.id.btn){
        /* logic here */
        }
    }
}
```

### 4. OnClickListener 인스턴스 구현
```java
Button.onClickListener myListener = new View.OnClickListener(){
	@Override
	public void onClick(View v){
		if(v.getId == R.id.btn){
			/* logic here */
		}
	}
}
findViewById(R.id.btn).setOnClickListener(myListener);

```

### 5?. lambda
```kotlin
Button.onClickListener {
	/* logic here */
}
```

## onClickListener vs onTouchListener
|onClickListener|onTouchListener|
|:---|:---|
|ex: Button, ImageButton|ex: get coordinates of screen where you touch exactly|