## ViewPager
- 데이터를 페이지 단위로 표시하고, 좌우 뒤집기(*flip*)을 통해 페이지를 전환할 수 있도록 만들어주는 <u>컨테이너</u>
- 자체적으로 화면을 그리지는 않고, 여러 종류의 뷰 위젯을 사용하여 각 뷰페이저의 페이지를 구성.

### adapter
- 안드로이드는 데이터리스트를 아이템 단위의 뷰 또는 뷰 그룹으로 표시할때(UI), adapter를 사용
- 사용자가 정의한 데이터 리스트를 입력으로 받아들여 화면에 표시할 뷰(view)를 생성
- 어댑터를 사용하는 대표적인 컴포넌트는 ListView, RecyclerView etc
<br>
- sdk에서 제공하는 모든 어댑터 클래스의 구현과정이 동일한 것은 아님


## ViewPager2
- 2019 androidx에 배포됨
- RecyclerView 기반
- RTL(Right To Left) 레이아웃, 수직 스크롤링 지원
- 페이지 변경 이벤트 처리하는 `OnPageChangeCallback`