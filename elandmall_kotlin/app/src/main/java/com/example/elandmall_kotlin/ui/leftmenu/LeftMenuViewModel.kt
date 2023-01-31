package com.example.elandmall_kotlin.ui.leftmenu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.BaseViewModel
import com.example.elandmall_kotlin.model.LeftMenuModule
import com.example.elandmall_kotlin.model.LeftMenuResponse
import com.example.elandmall_kotlin.model.LeftMenuModuleType
import kotlinx.coroutines.launch

class LeftMenuViewModel : BaseViewModel() {
    private val repository by lazy { LeftMenuRepository() }

    init {
        requestCategory()
    }

    val loginData = MutableLiveData<LeftMenuResponse.LoginInfo?>()
    val topMenuData = MutableLiveData<List<LeftMenuResponse.NavCatTopMenu?>>()
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
    private fun setStickyUI(data: LeftMenuResponse.Data) {
        loginData.postValue(data.loginInfo)

        data.navCatTopMenuList?.let { topMenu ->
            topMenuData.postValue(topMenu)
        }
    }

    private fun setModules(data: LeftMenuResponse.Data) {
        val list = mutableListOf<LeftMenuModule>()

        list.add(LeftMenuModule(LeftMenuModuleType.DIVIDER))
        list.add(LeftMenuModule(LeftMenuModuleType.RECENTLY, data.navCatLatelyGoodsList))
        list.add(LeftMenuModule(LeftMenuModuleType.DIVIDER))

        list.add(LeftMenuModule(LeftMenuModuleType.CATEGORY, data.navCatCategoryList?.get(0)?.navCatCategoryMenuList))
        list.add(LeftMenuModule(LeftMenuModuleType.DIVIDER))

        list.add(LeftMenuModule(LeftMenuModuleType.BRAND, data.navCatBrandList))
        list.add(LeftMenuModule(LeftMenuModuleType.DIVIDER))

        list.add(LeftMenuModule(LeftMenuModuleType.SHOP, data.navCatShopList))
        list.add(LeftMenuModule(LeftMenuModuleType.DIVIDER))

        list.add(LeftMenuModule(LeftMenuModuleType.SERVICE_MENU, data.navCatServicemenuList))

        list.add(LeftMenuModule(LeftMenuModuleType.FOOTER))


        uiList.postValue(list)
    }

}