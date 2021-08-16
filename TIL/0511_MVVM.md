## MVVM 패턴
비즈니스 및 프레젠테이션 로직을 UI와 분리

### View
- 화면에 보이는 레이아웃 구조
- UI와 관련된 로직을 수행할 수 있다

### ViewModel
- View에 연결된 데이터와 명령을 구현
- *변경알림 이벤트*를 통해 상태 변경을 View에 알림(notify)

### Model
- 데이터를 저장하고 있는 비시각적 클래스(entity)
- 일반적으로 데이터를 액세스하거나 캐싱이 필요한 서비스 또는 리포지토리와 함께 사용됨

## structure
![image](/TIL/resources/mvvm-architecture.png)

#### see also: [ListAdapter](""), [LiveData]("") etc.
