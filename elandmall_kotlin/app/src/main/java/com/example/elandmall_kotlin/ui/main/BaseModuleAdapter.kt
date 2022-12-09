package com.example.elandmall_kotlin.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.*
import com.example.elandmall_kotlin.ui.BaseViewHolder
import com.example.elandmall_kotlin.ui.ModuleData
import com.example.elandmall_kotlin.ui.main.viewholders.*

class BaseModuleAdapter(private val lifecycleOwner: LifecycleOwner) : ListAdapter<ModuleData, BaseViewHolder>(diff) {
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
            ModuleData.MainBannerData.ordinal() -> MainBannerViewHolder(
                ViewMainBannerBinding.inflate(
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

            ModuleData.CommonTitleData.ordinal() -> CommonTitleViewHolder(
                ViewCommonTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ModuleData.HomeLuckyDealData.ordinal() -> HomeLuckyDealViewHolder(
                ViewHomeLuckyDealBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.HomeSeasonPlanData.ordinal() -> HomeSeasonPlanViewHolder(
                ViewHomeSeasonPlanBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.HomeStoreShopData.ordinal() -> HomeStoreShopViewHolder(
                ViewHomeStoreShopBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.HomeMdData.ordinal() -> HomeMdViewHolder(
                ViewHomeMdBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopDeliveryData.ordinal() -> StoreShopDeliveryViewHolder(
                ViewStoreShopDeliveryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopRecommendData.ordinal() -> StoreShopRecommendViewHolder(
                ViewStoreShopRecommendBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopRegularData.ordinal() -> StoreShopRegularViewHolder(
                ViewStoreShopRegularBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StorePickHeaderData.ordinal() -> StorePickHeaderViewHolder(
                ViewStorePickHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopEmptyGoodsData.ordinal() -> StoreShopEmptyGoodsViewHolder(
                ViewStorePickEmptyGoodsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.CommonSortData.ordinal() -> CommonSortViewHolder(
                ViewCommonSortBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StorePickMoreData.ordinal() -> StorePickMoreViewHolder(
                ViewStorePickMoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.CommonGoodsGridData.ordinal() -> CommonGoodsGridViewHolder(
                ViewCommonGoodsGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.CommonGoodsLinearData.ordinal() -> CommonGoodsLinearViewHolder(
                ViewCommonGoodsLinearBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.CommonGoodsLargeData.ordinal() -> CommonGoodsLargeViewHolder(
                ViewCommonGoodsLargeBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopCateTabData.ordinal() -> StoreShopCateTabViewHolder(
                ViewStoreShopCateTabBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            ModuleData.StoreShopCateTitleData.ordinal() -> StoreShopCateTitleViewHolder(
                ViewStoreShopCateTitleBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> UnknownViewHolder(
                ViewUnknownHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = currentList[position]
        holder.onBind(item, position)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        payloads.map {
            when (it) {
                "payload" -> {
                    // do something
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        // scroll animation effect
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        // scroll animation effect
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAppeared()
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDisappeard()
    }

    override fun getItemViewType(position: Int): Int {
        return currentList[position].ordinal()
    }

    override fun onCurrentListChanged(previousList: MutableList<ModuleData>, currentList: MutableList<ModuleData>) {
        super.onCurrentListChanged(previousList, currentList)
    }
}

inline fun <reified T : Any> T.ordinal(): Int {
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