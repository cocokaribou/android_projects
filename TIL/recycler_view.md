## RecyclerView
`androidx.recyclerview.widget.RecyclerView` <br>
provides reusable viewHolder to display data <br>
[doc](https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView)<br>

- View Adapter
	- AdapterView 와 그 뷰에 표시할 데이터 사이의 다리가 되어줌
	- `onCreateViewHolder()` : 뷰홀더 생성
	- `onBindViewHolder()` : 뷰홀더 그려줌

### notify methods

```kotlin
void notifyDataSetChanged()
// 모든 자료를 다시 바인딩, 모든 View를 다시 그림
// bad practice!

void notifyItemChanged(int position, Object payload)
// position : 변경된 아이템의 위치
// payload : (optional) null일 경우 모든 업데이트로 식별
// 아이템이 삽입,삭제되는 이벤트에는 반응하지 않음


void notifyItemInserted(int position)
// position : data set에 새로 삽입된 아이템 위치
// 구조변경되는 이벤트


void notifyItemMoved(int fromPosition, int toPosition)
// fromPosition : 아이템의 이전 위치값
// toPositino : 아이템의 새로운 위치값
// 구조변경되는 이벤트 (ex: drag & drop)
```

### RecyclerView vs ListView
||RecyclerView|ListView|
|:---|:------|:------|
|ViewHolder|ViewHolder 패턴 이용|x|
|ItemLayout|`RecyclerView.LayoutManager` 가로,세로,지그재그 모두 지원|세로만 지원(vertical)|
|ItemAnimation|`RecyclerView.ItemAnimator`|x|
|Adapter|`RecyclerView.Adapter` 사용자 정의가 필요|배열/DB 결과에 대한 `ArrayAdapter`/`CursorAdapter`와 같은 다양한 소스에 대한 어댑터 존재|
|Decoration|`RecyclerView.ItemDecoration` 객체 사용해서 구분선 설정 |`android:divider` 속성 이용|
|ClickDetection|개별 터ㅣ 이벤트 관리하지만 기능 내장 x|`AdapterView.OnItemClickListener` 목록의 개별 항목에 대한 클릭이벤트 처리하는 인터페이스|


### RecyclerView.Adapter vs <a href="listAdapter">ListAdapter</a>
- ListAdapter is a extension of RecyclerView.Adapter
- ListAdapter offers `DiffUtil`, `submitList(list)`
- 
|Recycler Adapter| ListAdapter|
|:---|:---|
|best for displaying static list|best for displaying dynamic list|

### RecyclerView within ConstraintLayout does not scroll
1. `android:layout_height:0dp`
2. set `app:layout_constraintBottom_toBottomOf="parent"`