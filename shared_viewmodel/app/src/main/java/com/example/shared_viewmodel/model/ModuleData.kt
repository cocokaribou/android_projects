package com.example.shared_viewmodel.model

import com.example.shared_viewmodel.ui.list.ModulesType

data class ModuleData(
    var type : ModulesType,
    var bannerList: List<HomeMainBanner>? = null,
    var store: StoreData? = null,
    var storeList: List<StoreData>? = null
)