activity -> adapter 로 list를 넘겨주는데 adapter 쪽에서 lateinit 선언을 했더니 제대로 초기화가 안되고 있었음

```kotlin
private lateinit var bakeryList: List<DataVO.VoObject.Bakery> // x
private var bakeryList: emptyList<DataVO.VoObject.Bakery> // o

```