package com.example.elandmall_kotlin.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.LeftMenuModule
import com.example.elandmall_kotlin.model.CategoryResponse
import com.example.elandmall_kotlin.model.ModuleType
import kotlinx.coroutines.launch

class LeftMenuViewModel : ViewModel() {
    private val repository by lazy { LeftMenuRepository() }

    init {
        requestCategory()
    }

    val loginData = MutableLiveData<CategoryResponse.Data.LoginInfo>()
    val topMenuData = MutableLiveData<List<CategoryResponse.Data.NavCatTopMenu?>>()
    val uiList = MutableLiveData<MutableList<LeftMenuModule>>()

    private fun requestCategory() {
        viewModelScope.launch {
            repository.requestCategoryStream()
                .collect {
                    it.fold(
                        onSuccess = {
                            it?.data?.let { data ->
                                setStickyUI(data)
                                setModules(data)
                            }

                        },
                        onFailure = {}
                    )
                }
        }
    }
    private fun setStickyUI(data: CategoryResponse.Data) {
        data.loginInfo?.let { login ->
            loginData.postValue(login)
        }
        data.navCatTopMenuList?.let { topMenu ->
            topMenuData.postValue(topMenu)
        }
    }

    private fun setModules(data: CategoryResponse.Data) {
        val list = mutableListOf<LeftMenuModule>()

        list.add(LeftMenuModule(ModuleType.DIVIDER))
        list.add(LeftMenuModule(ModuleType.RECENTLY, ""))

        list.add(LeftMenuModule(ModuleType.DIVIDER))
        list.add(LeftMenuModule(ModuleType.CATEGORY))

        list.add(LeftMenuModule(ModuleType.DIVIDER))
        list.add(LeftMenuModule(ModuleType.BRAND))

        list.add(LeftMenuModule(ModuleType.DIVIDER))
        list.add(LeftMenuModule(ModuleType.SHOP))

        list.add(LeftMenuModule(ModuleType.DIVIDER))
        list.add(LeftMenuModule(ModuleType.SERVICE_MENU))

        list.add(LeftMenuModule(ModuleType.FOOTER))


        uiList.postValue(list)
    }

}