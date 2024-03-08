package com.example.elandmall_kotlin.ui.goods

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.*
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.view.TabListener
import kotlinx.coroutines.launch

class GoodsViewModel : ViewModel() {
    private val repository by lazy { GoodsRepository() }

    private var isExpand = false
    private val toggleListener: (Boolean) -> Unit = {
        isExpand = it
        expandWebViewHolder()
    }

    val currentIndex = MutableLiveData<Int>()
    private var tabListener: TabListener? = null
    fun setCallback(listener: TabListener) {
        tabListener = listener
    }

    init {
        requestGoods()
    }

    var goodsData: GoodsResponse.Data? = null
    var stickyData = MutableLiveData<Map<String, Any?>>()

    val moduleList = mutableListOf<GoodsModule>()
    var tabList = mutableListOf<GoodsModule>()
    val uiList = MutableLiveData<MutableList<GoodsModule>>()

    private fun requestGoods() {
        viewModelScope.launch {
            repository.requestGoodsStream().collect {
                it.fold(
                    onSuccess = {
                        it?.data.let { data ->
                            goodsData = data

                            setModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setModules(data: GoodsResponse.Data?) {
        moduleList.clear()

        moduleList.add(GoodsModule(GoodsModuleType.GOODS_HEADER))
        moduleList.add(GoodsModule(GoodsModuleType.GOODS_TOP_IMAGE, data?.topImageList))
        moduleList.add(GoodsModule(GoodsModuleType.GOODS_INFO, mapOf("share" to data?.share, "info" to data?.goodsInfo)))

        val reviewCount = data?.goodsInfo?.goodsReviewInfo?.reviewCount
        val qnaCount = data?.goodsInfo?.goodsQuestionInfo?.questionCount

        // inner recycler view holder
        moduleList.add(
            GoodsModule(
                GoodsModuleType.GOODS_TAB,
                mapOf(
                    "currentIndex" to currentIndex.value,
                    "reviewCount" to reviewCount,
                    "qnaCount" to qnaCount,
                    "updateTabInner" to ::updateTabInner
                )
            )
        )

        // outer sticky view
        stickyData.postValue(
            mapOf(
                "currentIndex" to currentIndex,
                "reviewCount" to reviewCount,
                "qnaCount" to qnaCount
            )
        )

        // default tab [0]
        tabList = moduleList.map { it.copy() }.toMutableList()
        tabList.add(
            GoodsModule(
                GoodsModuleType.GOODS_DETAIL_WEB,
                mapOf(
                    "data" to (goodsData?.goodsDetail ?: ""),
                    "isExpand" to isExpand,
                    "toggleListener" to toggleListener
                )
            )
        )
        tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_TAG, goodsData?.tagList))
        tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_SELLER_RECOM, goodsData?.sellerRecommendGoods))
        tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_SELLER_POPULAR, goodsData?.sellerPopularGoods))
        tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_RECOM, goodsData?.recommendGoods))
        tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_POPULAR, goodsData?.popularGoods))
        tabList.add(GoodsModule(GoodsModuleType.GOODS_BOTTOM_MARGIN))

        uiList.postValue(tabList)
    }

    fun updateTabInner(index: Int) {
        currentIndex.value = index

        tabList = moduleList.map { it.copy() }.toMutableList()

        val reviewCount = goodsData?.goodsInfo?.goodsReviewInfo?.reviewCount
        val qnaCount = goodsData?.goodsInfo?.goodsQuestionInfo?.questionCount

        tabList.map {
            if (it.type == GoodsModuleType.GOODS_TAB) {
                it.data = mapOf(
                    "currentIndex" to currentIndex.value,
                    "reviewCount" to reviewCount,
                    "qnaCount" to qnaCount,
                    "updateTabInner" to ::updateTabInner
                )
            }
        }

        when (index) {
            0 -> {
                // detail tab
                tabList.add(
                    GoodsModule(
                        GoodsModuleType.GOODS_DETAIL_WEB,
                        mapOf(
                            "data" to (goodsData?.goodsDetail ?: ""),
                            "isExpand" to isExpand,
                            "toggleListener" to toggleListener
                        )
                    )
                )
                tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_TAG, goodsData?.tagList))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_SELLER_RECOM, goodsData?.sellerRecommendGoods))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_SELLER_POPULAR, goodsData?.sellerPopularGoods))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_RECOM, goodsData?.recommendGoods))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_POPULAR, goodsData?.popularGoods))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_BOTTOM_MARGIN))
            }
            1 -> {
                // review tab
                val imgList: List<String> =
                    goodsData?.goodsInfo?.goodsReviewInfo?.reviewInfo?.reviewImageInfo?.reviewList?.map { it -> it?.imageUrl ?: "" }
                        ?: listOf()

                tabList.add(GoodsModule(GoodsModuleType.GOODS_REVIEW_PREVIEW, imgList))
                tabList.add(
                    GoodsModule(
                        GoodsModuleType.GOODS_REVIEW_PHOTO,
                        goodsData?.goodsInfo?.goodsReviewInfo?.reviewInfo?.reviewImageInfo
                    )
                )
                tabList.add(
                    GoodsModule(
                        GoodsModuleType.GOODS_REVIEW_TEXT,
                        goodsData?.goodsInfo?.goodsReviewInfo?.reviewInfo?.reviewTextInfo
                    )
                )
                tabList.add(GoodsModule(GoodsModuleType.GOODS_BOTTOM_MARGIN))
            }
            2 -> {
                // Q&A tab
                tabList.add(GoodsModule(GoodsModuleType.GOODS_QNA_FORM))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_QNA_LIST, goodsData?.goodsInfo?.goodsQuestionInfo?.questionList))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_BOTTOM_MARGIN))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_BOTTOM_MARGIN))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_BOTTOM_MARGIN))

            }
            3 -> {
                // order info tab
                tabList.add(GoodsModule(GoodsModuleType.GOODS_ORDER_INFO))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_ORDER_INFO_STORE, goodsData?.orderInfo))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_BOTTOM_MARGIN))
            }
        }

        uiList.postValue(tabList)
    }
    private fun expandWebViewHolder() {
        tabList.map {
            if (it.type == GoodsModuleType.GOODS_DETAIL_WEB) {
                it.data = mapOf(
                    "data" to (goodsData?.goodsDetail ?: ""),
                    "isExpand" to isExpand,
                    "toggleListener" to toggleListener
                )
            }
        }
        uiList.postValue(tabList)
    }
}