package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.util.Logger
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class StoreShopViewModel : BaseViewModel() {
    private val repository: StoreShopRepository by lazy { StoreShopRepository() }

    override val refreshComplete = MutableLiveData<String>()

    val storeShopList = MutableLiveData<StoreShopResponse?>()
    init {
        // init
        viewModelScope.launch {
            repository.requestStoreShopStream()
                .catch {
                    storeShopList.postValue(null)
                }
                .collect {
                    it.fold(
                        onSuccess = {
                            storeShopList.postValue(it)
                        },
                        onFailure = {
                            storeShopList.postValue(null)
                        }
                    )
                }
        }
    }

    override fun requestRefresh() {
        // refresh
        viewModelScope.launch {
            repository.requestStoreShopStream()
                .catch {
                    storeShopList.postValue(null)
                    refreshComplete.postValue("storeshop")
                }
                .collect {
                    it.fold(
                        onSuccess = {
                            storeShopList.postValue(it)
                            refreshComplete.postValue("storeshop")
                        },
                        onFailure = {
                            storeShopList.postValue(null)
                            refreshComplete.postValue("storeshop")
                        }
                    )
                }
        }
    }

    val uiList = MutableLiveData<MutableList<ModuleData>>()
    fun setStoreShopModules(data: StoreShopResponse?) {
        val moduleList = mutableListOf<ModuleData>()
        data?.data?.let { storeShopData ->
            if (!storeShopData.storeMainbannerList.isNullOrEmpty()) {
                moduleList.add(
                    ModuleData.HomeMainBannerData(
                        storeShopData.storeMainbannerList
                    )
                )
            }
        }
        uiList.postValue(moduleList)
    }
}