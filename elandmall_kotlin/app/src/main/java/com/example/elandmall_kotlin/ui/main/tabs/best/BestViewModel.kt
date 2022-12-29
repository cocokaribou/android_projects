package com.example.elandmall_kotlin.ui.main.tabs.best

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.BestResponse
import com.example.elandmall_kotlin.model.Category
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.ui.main.viewholders.cateSelected
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class BestViewModel : BaseViewModel() {
    private val repository by lazy { BestRepository() }

    private val moduleList = mutableListOf<ModuleData>()
    private var tabModuleList = mutableListOf<ModuleData>()
    val uiList = MutableLiveData<MutableList<ModuleData>>()

    var pageIdx = 1

    init {
        requestBest()
    }

    override fun requestRefresh() {
        requestBest()
    }

    private fun requestBest() {
        viewModelScope.launch {
            repository.requestBestStream()
                .collect {
                    it.fold(
                        onSuccess = {
                            it?.data?.let { data ->
                                setBestModules(data)
                                if (data.dispCtgList?.get(0) != null) {
                                    requestBestTab(data.dispCtgList[0].dispCtgNo!!, pageIdx)
                                }
                            }
                        },
                        onFailure = {}
                    )
                }
        }
    }

    private fun requestBestTab(dispCtgNo: String, idx: Int) {
        viewModelScope.launch {
            repository.requestBestTabStream(dispCtgNo, idx)
                .collect {
                    it.fold(
                        onSuccess = {
                            it?.data?.let { data ->
                                setBestTabModules(data)
                            }
                        },
                        onFailure = {}
                    )
                }
        }
    }

    private fun setBestModules(data: BestResponse.Data) {
        moduleList.clear()

        if (!data.dispCtgList.isNullOrEmpty()) {
            val list: List<Category> =
                data.dispCtgList.map {
                    val title = it.dispCtgShowNm ?: it.ldispCtgShowNm
                    val payload = it.dispCtgNo ?: it.ldispCtgNo
                    Category(title = title, payload1 = payload, image = it.imgPath)
                }
            moduleList.add(
                ModuleData.CommonCategoryTabData(
                    categoryList = list,
                    changeCategory = { payload ->
                        changeCategoryTab(payload[0])
                    })
            )
        }

        uiList.postValue(moduleList)
    }

    private fun setBestTabModules(data: BestResponse.Data) {
        tabModuleList = moduleList.map { it.clone() }.toMutableList()

        if (data.goodsInfo != null) {
            data.goodsInfo.goodsList?.let { goodsList ->
                goodsList.mapIndexed { i, goods ->
                    goods.rank = i + 1
                }

                goodsList.chunked(2).forEach { goods ->
                    tabModuleList.add(
                        ModuleData.CommonGoodsGridData(goods)
                    )
                }
            }
        }

        uiList.postValue(tabModuleList)
    }

    private fun changeCategoryTab(dispCtgNo: String) {
        requestBestTab(dispCtgNo, pageIdx)
    }
}