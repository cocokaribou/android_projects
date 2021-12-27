## RecyclerView
`androidx.recyclerview.widget.RecyclerView` <br>
provides reusable viewHolder to display data <br>
[doc](https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView)<br>

- View Adapter
	- AdapterView 와 그 뷰에 표시할 데이터 사이의 다리가 되어줌
	- `onCreateViewHolder()` : 뷰홀더 생성
	- `onBindViewHolder()` : 뷰홀더 그려줌

### RecyclerView vs ListView
||RecyclerView|ListView|
|:---|:------|:------|
|ViewHolder|ViewHolder 패턴 이용|x|
|ItemLayout|`RecyclerView.LayoutManager` 가로,세로,지그재그 모두 지원|세로만 지원(vertical)|
|ItemAnimation|`RecyclerView.ItemAnimator`|x|
|Adapter|`RecyclerView.Adapter` 사용자 정의가 필요|배열/DB 결과에 대한 `ArrayAdapter`/`CursorAdapter`와 같은 다양한 소스에 대한 어댑터 존재|
|Decoration|`RecyclerView.ItemDecoration` 객체 사용해서 구분선 설정 |`android:divider` 속성 이용|
|ClickDetection|개별 터ㅣ 이벤트 관리하지만 기능 내장 x|`AdapterView.OnItemClickListener` 목록의 개별 항목에 대한 클릭이벤트 처리하는 인터페이스|


### RecyclerView.Adapter vs ListAdapter
- ListAdapter is a extension of RecyclerView.Adapter
- ListAdapter offers `DiffUtil`, `submitList(list)`
- 
|Recycler Adapter| ListAdapter|
|:---|:---|
|best for displaying static list|best for displaying dynamic list|