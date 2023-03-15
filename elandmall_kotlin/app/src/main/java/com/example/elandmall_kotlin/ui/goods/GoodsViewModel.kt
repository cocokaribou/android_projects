package com.example.elandmall_kotlin.ui.goods

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.*
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.launch

class GoodsViewModel: ViewModel() {
    private val repository by lazy { GoodsRepository() }

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
        list.add(GoodsModule(GoodsModuleType.GOODS_INFO))

        uiList.postValue(list)
    }
}