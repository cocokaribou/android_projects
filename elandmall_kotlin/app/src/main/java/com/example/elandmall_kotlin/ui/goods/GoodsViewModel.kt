package com.example.elandmall_kotlin.ui.goods

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.*
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.launch

class GoodsViewModel : ViewModel() {
    private val repository by lazy { GoodsRepository() }

    var currentTab = 0
    val tabListener : (Int) -> Unit = {
        currentTab = it
        updateTabs()
    }
    init {
        requestGoods()
    }

    val uiList = MutableLiveData<MutableList<GoodsModule>>()

    private fun requestGoods() {
        viewModelScope.launch {
            repository.requestGoodsStream().collect {
                it.fold(
                    onSuccess = {
                        it?.data.let { data ->
                            setStickyUI(data)
                            setModules(data)
                        }
                    },
                    onFailure = {}
                )
            }
        }
    }

    private fun setStickyUI(data: GoodsResponse.Data?) {
    }

    private fun setModules(data: GoodsResponse.Data?) {
        val list = mutableListOf<GoodsModule>()

        list.add(GoodsModule(GoodsModuleType.HEADER))
        list.add(GoodsModule(GoodsModuleType.GOODS_TOP_IMAGE, data?.topImageList))
        list.add(GoodsModule(GoodsModuleType.GOODS_INFO, mapOf("share" to data?.share, "info" to data?.goodsInfo)))

        val reviewCount =  data?.goodsInfo?.goodsReviewInfo?.reviewCount
        val qnaCount = data?.goodsInfo?.goodsQuestionInfo?.questionCount
        list.add(GoodsModule(GoodsModuleType.GOODS_TAB, mapOf("listener" to tabListener, "review_count" to reviewCount, "qna_count" to qnaCount)))

        uiList.postValue(list)
    }

    private fun updateTabs() {

    }
}