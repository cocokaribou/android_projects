1. JSON 데이터 파싱해서 화면에 그리기
2. 다양한 `ViewHolder` 생성한 뒤 `getItemViewType(Int)`로 position별 viewType 지정
3. 전체 액티비티를 Adapter로 감싸서 UI 그려주기
4. listener 객체로 activity, adapter, holder 간에 통신하기
	- recycler view 영역에서 holder 클릭
	- recycler view 내 ui 변경 `RecyclerView.Adapter::notifyDataSetChanged()`
	- activity에서 listener 구현, activity에서 adapter 등록시 인자로 전달
	- activity 내 ui 변경, 스크롤 이벤트
```kotlin
	

    private val onItemClickListener: (Any, Int, Int) -> Unit = { item, position, holderWidth ->
        if (item is TestVO.Data.Category) {
            mainAdapter.setCategoryDataSetChanged() //notifyDataSetChanged()
            categoryAdapter.selected(position) //notifyDataSetChanged()

            val centerOfDisplay = getDisplaySize(this).widthPixels / 2
            linearManager.scrollToPositionWithOffset(position, centerOfDisplay - holderWidth/2)
            val index = titleList[position]
            gridManager.scrollToPositionWithOffset(index, 0)

        }
    }
    
    private override fun onCreate(savedInstanceState: Bundle?) {
    	// ...
    	mainAdapter.setListener(onItemClickListener)
    }
```

5. `ViewHolder` 객체에서 `View.GONE`, `View.VISIBLE` toggle할 땐 예외처리 해주기
```kotlin
    binding.txtMarketPrice.visibility = 
    	if (marketPrice == 0) View.GONE 
    	else View.VISIBLE
```