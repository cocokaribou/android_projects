## Migrating Elandmall AOS application from java to kotlin
### summary
- Kotlin, MVVM structure
- fragment for main tab pager, activity for other UIs
- the goal of this project is to migrate existing Java code into Kotlin <u>for study purposes</u>

### schedule
1. app intro process (merging several apis before launching MainActivity)
2. eventbus for handling in-app navigation or global-scope events
3. main tab pager
4. implementing each tab (recycler views)

### apis

- logo (gnb, bottom bar) :
  - https://m.elandmall.com/api/main/mainTabJsonNew.action
- tab init & tab swipeToRefresh
- category :
  - https://m.elandmall.com/api/dispctg/searchGnbAllCategoryListElandJson.action?cate_expand_yn=Y
- brand : 
  - https://m.elandmall.com/api/dispctg/searchEsBrandIndexCountInfoJson.action
  - https://srch.elandmall.com/add/ranking/get?domain_no=14&max_count=10&brand_yn=Y&interval=30
  - https://m.elandmall.com/api/dispctg/searchGoodsListLatelyPreviewJson.action
  - https://srch.elandmall.com/add/quicksearch/get?kwd=&domain_no=&req=%5B%22planshop%22%5D&max_count=3&domain_no=&staff_yn=N&search_poss_yn=Y&sort_key=reg_dtime&sort_type=desc&resfix_yn=N
  - https://srch.elandmall.com/add/ranking/get?domain_no=14&max_count=10&brand_yn=N
  - https://m.elandmall.com/api/dispctg/searchEsBrandSelectIndexInfoJson.action?keywordindex=%E3%84%B1&lang=kor
  
- bottom bar order / recent: isLoginCheckAjax.action

- gnb plan: https://m.elandmall.com/api/shop/initPlanShopGoodsMainJson.action
- gnb eventCoupon: webview
- 