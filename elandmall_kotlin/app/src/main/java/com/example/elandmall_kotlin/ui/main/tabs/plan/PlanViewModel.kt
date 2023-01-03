package com.example.elandmall_kotlin.ui.main.tabs.plan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.Category
import com.example.elandmall_kotlin.model.PlanResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import kotlinx.coroutines.launch

class PlanViewModel : BaseViewModel() {
    val repository by lazy { PlanRepository() }
    private val moduleList = mutableListOf<ModuleData>()
    private var tabModuleList = mutableListOf<ModuleData>()
    val uiList = MutableLiveData<MutableList<ModuleData>>()

    init {
        requestPlan()
    }

    override fun requestRefresh() {
        requestPlan()
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

    private fun requestPlanTab(dispCtgNo: String, index: Int) {
        viewModelScope.launch {
            repository.requestPlanTabStream(dispCtgNo, index)
                .collect {
                    it.fold(
                        onSuccess = {
                            it?.data?.let { data ->
                                setPlanTabModules(data)
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
            val list = data.ctgList.mapIndexed { i, item ->
                Category(title = item.dispCtgNm, payload1 = item.dispCtgNo, payload2 = (i + 1).toString(), image = item.imgPath)
            }.toMutableList()

            list.add(0, Category(title = "전체보기", 0))

            moduleList.add(
                ModuleData.CommonCategoryTabData(
                    categoryList = list,
                    changeCategory = {
                        val dispCtgNo = it[0]
                        val index = it[1].toInt()

                        requestPlanTab(dispCtgNo, index)
                    }),
            )
        }

        tabModuleList = moduleList.map { it.clone() }.toMutableList()
        if (!data.planList.isNullOrEmpty()) {
            data.planList.forEach { plan ->
                tabModuleList.add(
                    ModuleData.CommonPlanData(plan)
                )
            }
        }
        uiList.postValue(tabModuleList)
    }

    private fun setPlanTabModules(data: PlanResponse.Data) {
        tabModuleList = moduleList.map { it.clone() }.toMutableList()

        if (!data.planList.isNullOrEmpty()) {
            data.planList.forEach { plan ->
                tabModuleList.add(
                    ModuleData.CommonPlanData(plan)
                )
            }
        }

        uiList.postValue(tabModuleList)
    }
}