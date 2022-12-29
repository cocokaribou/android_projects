package com.example.elandmall_kotlin.ui.main.tabs.plan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.Category
import com.example.elandmall_kotlin.model.PlanResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.ui.main.viewholders.cateSelected
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PlanViewModel : BaseViewModel() {
    val repository by lazy { PlanRepository() }
    val moduleList = mutableListOf<ModuleData>()
    val tabModuleList = mutableListOf<ModuleData>()
    val uiList = MutableLiveData<MutableList<ModuleData>>()

    override fun requestRefresh() {

    }

    private fun requestPlan() {
        viewModelScope.launch {
            repository.requestPlanStream()
                .collect {
                    it.fold(
                        onSuccess = {
                            it?.data?.let { data ->
                                setPlanModules(data)
                            }
                        },
                        onFailure = {}
                    )
                }
        }
    }

    private fun setPlanModules(data: PlanResponse.Data) {
        moduleList.clear()

        if (!data.ctgList.isNullOrEmpty()) {
            val list: List<Category> = data.ctgList.map {
                Category(title = it?.dispCtgNm, payload1 = it?.dispCtgNo, image = it?.imgPath)
            }

            moduleList.add(
                ModuleData.CommonCategoryTabData(
                    categoryList = list,
                    changeCategory = {}),
            )
        }
    }
}