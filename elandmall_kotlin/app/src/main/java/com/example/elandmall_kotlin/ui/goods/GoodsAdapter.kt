package com.example.elandmall_kotlin.ui.goods

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.*
import com.example.elandmall_kotlin.model.GoodsModule
import com.example.elandmall_kotlin.model.GoodsModuleType
import com.example.elandmall_kotlin.ui.goods.viewholders.GoodsHeaderHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.GoodsInfoHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.GoodsTabHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.GoodsTopImageHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.tab1.GoodsDetailPopularHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.tab1.GoodsDetailSellerRecomHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.tab1.GoodsDetailTagHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.tab1.GoodsDetailWebHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.tab2.GoodsReviewPhotoHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.tab2.GoodsReviewPreviewHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.tab2.GoodsReviewTextHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.tab3.GoodsQnaFormHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.tab4.GoodsOrderInfoHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.tab4.GoodsOrderInfoStoreHolder

class GoodsAdapter : ListAdapter<GoodsModule, GoodsBaseViewHolder>(object : DiffUtil.ItemCallback<GoodsModule>() {
    override fun areItemsTheSame(oldItem: GoodsModule, newItem: GoodsModule): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: GoodsModule, newItem: GoodsModule): Boolean = oldItem.equals(newItem)

}) {
    override fun getItemViewType(position: Int): Int = currentList[position].type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsBaseViewHolder {
        return when (GoodsModuleType.values()[viewType]) {
            GoodsModuleType.GOODS_HEADER -> GoodsHeaderHolder(
                ViewGoodsHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            GoodsModuleType.GOODS_TOP_IMAGE -> GoodsTopImageHolder(
                ViewGoodsTopImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            GoodsModuleType.GOODS_INFO -> GoodsInfoHolder(
                ViewGoodsInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GoodsModuleType.GOODS_TAB -> GoodsTabHolder(
                ViewGoodsTabBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GoodsModuleType.GOODS_ORDER_INFO -> GoodsOrderInfoHolder(
                ViewGoodsOrderInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GoodsModuleType.GOODS_ORDER_INFO_STORE -> GoodsOrderInfoStoreHolder(
                ViewGoodsOrderInfoStoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GoodsModuleType.GOODS_DETAIL_WEB -> GoodsDetailWebHolder(
                ViewGoodsDetailWebBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GoodsModuleType.GOODS_DETAIL_TAG -> GoodsDetailTagHolder(
                ViewGoodsDetailTagBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GoodsModuleType.GOODS_DETAIL_POPULAR -> GoodsDetailPopularHolder(
                ViewGoodsDetailPopularBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GoodsModuleType.GOODS_DETAIL_SELLER_RECOM -> GoodsDetailSellerRecomHolder(
                ViewGoodsDetailSellerRecomBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GoodsModuleType.GOODS_REVIEW_PREVIEW -> GoodsReviewPreviewHolder(
                ViewGoodsReviewPreviewBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GoodsModuleType.GOODS_REVIEW_PHOTO -> GoodsReviewPhotoHolder(
                ViewGoodsReviewPhotoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GoodsModuleType.GOODS_REVIEW_TEXT -> GoodsReviewTextHolder(
                ViewGoodsReviewTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            GoodsModuleType.GOODS_QNA_FORM -> GoodsQnaFormHolder(
                ViewGoodsQnaFormBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> GoodsInfoHolder(
                ViewGoodsInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: GoodsBaseViewHolder, position: Int) {
        holder.onBind(currentList[position].data)
    }
}

abstract class GoodsBaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun onBind(item: Any?) {}
}