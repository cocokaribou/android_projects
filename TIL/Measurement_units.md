## dp
- stands for 'density-independent pixel'
- 1 dp is <i>a virtual pixel</i>
- android translates this value to the appropriate number of real pixels according to the density of the device

### dp vs sp for TextView
- sp stands for 'scale-independent pixel'
- The sp unit is the same size as dp, but it <i>resizes</i> based on the user's preferred text size.
- [reference](https://developer.android.com/training/multiscreen/screendensities)

### setTextSize in dp programmatically
```kotlin
tvTest.apply {
    setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16f)
}
```