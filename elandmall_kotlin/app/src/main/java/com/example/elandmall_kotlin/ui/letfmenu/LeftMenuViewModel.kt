package com.example.elandmall_kotlin.ui.letfmenu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.LeftMenuModule
import com.example.elandmall_kotlin.model.LeftMenuResponse
import com.example.elandmall_kotlin.model.ModuleType
import com.example.elandmall_kotlin.ui.main.BaseViewModel
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

        list.add(LeftMenuModule(ModuleType.DIVIDER))
        list.add(LeftMenuModule(ModuleType.RECENTLY, data.navCatLatelyGoodsList))
        list.add(LeftMenuModule(ModuleType.DIVIDER))

        list.add(LeftMenuModule(ModuleType.CATEGORY, data.navCatCategoryList?.get(0)?.navCatCategoryMenuList))
        list.add(LeftMenuModule(ModuleType.DIVIDER))

        list.add(LeftMenuModule(ModuleType.BRAND, data.navCatBrandList))
        list.add(LeftMenuModule(ModuleType.DIVIDER))

        list.add(LeftMenuModule(ModuleType.SHOP, data.navCatShopList))
        list.add(LeftMenuModule(ModuleType.DIVIDER))

        list.add(LeftMenuModule(ModuleType.SERVICE_MENU, data.navCatServicemenuList))

        list.add(LeftMenuModule(ModuleType.FOOTER))


        uiList.postValue(list)
    }

}