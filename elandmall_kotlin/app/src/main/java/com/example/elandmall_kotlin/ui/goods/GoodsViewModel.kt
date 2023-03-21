package com.example.elandmall_kotlin.ui.goods

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.*
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.launch

class GoodsViewModel : ViewModel() {
    private val repository by lazy { GoodsRepository() }

    var isExpand = false
    val currentTab = MutableLiveData<Int>()
    val tabListener: (Int) -> Unit = {
        updateTabInner(it)
    }
    val toggleListener: (Boolean) -> Unit = {
        isExpand = it
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
                    "listener" to tabListener,
                    "current_tab" to currentTab,
                    "review_count" to reviewCount,
                    "qna_count" to qnaCount,
                    "update_listener" to ::updateTabInner
                )
            )
        )

        // outer sticky view
        stickyData.postValue(mapOf("current_tab" to currentTab, "review_count" to reviewCount, "qna_count" to qnaCount))

        uiList.postValue(moduleList)
    }

    fun updateTabInner(index: Int) {
        Logger.v("매번 여기를 타셔야해요 $index")


        moduleList.apply {
            map {
                if (it.type == GoodsModuleType.GOODS_TAB) {
                    val reviewCount = goodsData?.goodsInfo?.goodsReviewInfo?.reviewCount
                    val qnaCount = goodsData?.goodsInfo?.goodsQuestionInfo?.questionCount

                    GoodsModule(
                        GoodsModuleType.GOODS_TAB,
                        mapOf(
                            "listener" to tabListener,
                            "current_tab" to index,
                            "review_count" to 0,
                            "qna_count" to 0,
                            "update_listener" to ::updateTabInner
                        )
                    )
                }
            }
        }
        tabList = moduleList.toMutableList()

        Logger.v("${(tabList.find{ it.type == GoodsModuleType.GOODS_TAB}?.data as Map<String, *>)["current_tab"]}")
        when (index) {
            0 -> {
                // detail tab
                tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_WEB, mapOf("data" to (goodsData?.goodsDetail ?: ""), "isExpand" to isExpand, "toggle" to toggleListener)))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_TAG, goodsData?.tagList))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_POPULAR, goodsData?.sellerPopularGoods))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_DETAIL_SELLER_RECOM, goodsData?.sellerRecommendGoods))
            }
            1 -> {
                // review tab
                val imgList: List<String> =
                    goodsData?.goodsInfo?.goodsReviewInfo?.reviewInfo?.reviewImageInfo?.reviewList?.map { it -> it?.imageUrl ?: "" }
                        ?: listOf()

                tabList.add(GoodsModule(GoodsModuleType.GOODS_REVIEW_PREVIEW, imgList))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_REVIEW_PHOTO, goodsData?.goodsInfo?.goodsReviewInfo?.reviewInfo?.reviewImageInfo))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_REVIEW_TEXT, goodsData?.goodsInfo?.goodsReviewInfo?.reviewInfo?.reviewTextInfo))
            }
            2 -> {
                // Q&A tab
                tabList.add(GoodsModule(GoodsModuleType.GOODS_QNA_FORM))
            }
            3 -> {
                // order info tab
                tabList.add(GoodsModule(GoodsModuleType.GOODS_ORDER_INFO))
                tabList.add(GoodsModule(GoodsModuleType.GOODS_ORDER_INFO_STORE, goodsData?.orderInfo))
            }
        }

        uiList.postValue(tabList)
    }
}