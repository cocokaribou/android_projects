package com.example.shared_viewmodel.ui

import androidx.lifecycle.*
import com.example.shared_viewmodel.model.*
import com.example.shared_viewmodel.ui.list.ModulesType
import kotlinx.coroutines.Dispatchers

/**
 */
class StoreSharedViewModel: ViewModel() {
    // api response(flow) to livedata
    private val repository = MainHomeRepository()
    private val paramMainHome = MutableLiveData<String>()
    val mainHomeResult: LiveData<MainHomeResult> = paramMainHome.switchMap {
        liveData {
            val stream = repository.searchMainHomeStream().asLiveData(Dispatchers.Main)
            emitSource(stream)
        }
    }

    fun searchMainHomeRepo() {
        paramMainHome.postValue("")
    }

    // in-app input data
    private val _mainList = MutableLiveData<List<ModuleData>>()
    val mainList : LiveData<List<ModuleData>> = _mainList

    // set main recycler view ui
    // CHECKER UI 로직은 fragment 단에 있어야 하는거 아냐..? 뷰모델은 데이터 조작만..
    fun setMainList(input: String, bannerList: List<HomeMainBanner>) {
        val inputList = input.split(" ")

        val moduleList = mutableListOf<ModuleData>()
        val storeList = mutableListOf<StoreData>()
        moduleList.add(ModuleData(ModulesType.Banner, bannerList = bannerList))
        moduleList.add(ModuleData(ModulesType.Grid, storeList = storeList))
        moduleList.add(ModuleData(ModulesType.Horizontal, storeList = storeList))
        inputList.forEachIndexed { i, item ->
            val storeData = StoreData(i, item)
            storeList.add(storeData)
            moduleList.add(ModuleData(ModulesType.Vertical, store = storeData))
        }
        moduleList.add(ModuleData(ModulesType.Horizontal, storeList = storeList))
        moduleList.add(ModuleData(ModulesType.Horizontal, storeList = storeList))
        moduleList.add(ModuleData(ModulesType.Horizontal, storeList = storeList))
        moduleList.add(ModuleData(ModulesType.Horizontal, storeList = storeList))
        moduleList.add(ModuleData(ModulesType.Horizontal, storeList = storeList))
        moduleList.add(ModuleData(ModulesType.Horizontal, storeList = storeList))
        moduleList.add(ModuleData(ModulesType.Horizontal, storeList = storeList))
        moduleList.add(ModuleData(ModulesType.Horizontal, storeList = storeList))
        moduleList.add(ModuleData(ModulesType.Horizontal, storeList = storeList))
        moduleList.add(ModuleData(ModulesType.Horizontal, storeList = storeList))

        _mainList.value = moduleList
    }

    private val _stoContent = MutableLiveData<String>()
    val stoContent : LiveData<String> = _stoContent

    fun setStoContent(stoContent: String) {
        _stoContent.value = stoContent
    }

    val currentStack = MutableLiveData(0)

    fun incStackCount() {
        currentStack.value?.let { page ->
            currentStack.value = page + 1
        }
    }
    fun decStackCount() {
        currentStack.value?.let { page ->
            if (page > 0) {
                currentStack.value = page - 1
            } else {
                currentStack.value = 0
            }
        }
    }

    fun initDetailStackCount() {
        currentStack.value?.let {
            currentStack.value = 0
        }
    }
}