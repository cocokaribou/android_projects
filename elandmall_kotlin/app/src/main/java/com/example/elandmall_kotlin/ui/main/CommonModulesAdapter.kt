package com.example.elandmall_kotlin.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.elandmall_kotlin.databinding.*
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.viewholders.*

class CommonModulesAdapter(private val lifecycleOwner: LifecycleOwner) : ListAdapter<ModuleData, BaseViewHolder>(diff) {
    companion object {
        val diff = object : DiffUtil.ItemCallback<ModuleData>() {
            override fun areItemsTheSame(oldItem: ModuleData, newItem: ModuleData) = oldItem == newItem
            override fun areContentsTheSame(oldItem: ModuleData, newItem: ModuleData) = oldItem == newItem
        }
    }

    var value: List<ModuleData> = listOf()
        set(value) {
            field = value
            submitList(field)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            ModuleData.HomeMainBannerData.ordinal() -> HomeFlipperViewHolder(
                ViewHomeFlipperBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.HomeCategoryIconData.ordinal() -> HomeCategoryViewHolder(
                ViewHomeCategoryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.HomeMultiBannerData.ordinal() -> HomeMultiBannerViewHolder(
                ViewHomeMultiBannerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.HomeTimeData.ordinal() -> HomeTimeSaleViewHolder(
                ViewHomeTimeSaleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), lifecycleOwner
            )

            ModuleData.HomeBrandData.ordinal() -> HomeBrandViewHolder(
                ViewHomeBrandBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.HomeTitleData.ordinal() -> HomeTitleViewHolder(
                ViewHomeTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> HomeLuckyDealViewHolder(
                ViewHomeLuckyDealBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
//            ModuleData.HomeLuckyDealData.ordinal() -> HomeLuckyDealViewHolder(
//                ViewHomeLuckyDealBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//            )
//
//            ModuleData.HomeSeasonPlansData.ordinal() -> HomeSeasonPlanViewHolder(
//                ViewHomeSeasonPlanModuleBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//            )
//
//            ModuleData.HomeStoreShopData.ordinal() -> HomeStoreShopViewHolder(
//                ViewHomeStoreShopModuleBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//            )
//
//            else -> UnknownViewHolder(
//                ViewUnknownModuleBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent,
//                    false
//                )
//            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

// TODO 이게 정확히 뭐하는 앨까..
private inline fun <reified T : Any> T.ordinal(): Int {
    if (T::class.isSealed) {
        return T::class.java.classes.indexOfFirst { sub -> sub == javaClass }
    }

    val klass = if (T::class.nestedClasses.isEmpty()) {
        javaClass.declaringClass
    } else {
        javaClass
    }

    return klass.superclass?.classes?.indexOfFirst { it == klass } ?: -1
}