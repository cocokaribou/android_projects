package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.ui.main.tabs.home.HomeRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class StoreShopViewModel : BaseViewModel() {
    private val repository: StoreShopRepository by lazy { StoreShopRepository() }

    val requestedData = MutableLiveData<StoreShopResponse?>()

    init {
        viewModelScope.launch {
            repository.requestStoreShopStream()
                .catch {
                    requestedData.postValue(null)
                }
                .collect {
                    it.fold(
                        onSuccess = {
                            requestedData.postValue(it)
                        },
                        onFailure = {
                            requestedData.postValue(null)
                        }
                    )
                }
        }
    }

    override fun requestRefresh() {
        viewModelScope.launch {
            repository.requestStoreShopStream()
                .catch {
                }
                .collect {
                    it.fold(
                        onSuccess = {
                            requestedData.postValue(it)
                        },
                        onFailure = {
                        }
                    )
                }
        }
    }

    val storeList = MutableLiveData<MutableList<ModuleData>>()
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
        storeList.postValue(moduleList)
    }
}