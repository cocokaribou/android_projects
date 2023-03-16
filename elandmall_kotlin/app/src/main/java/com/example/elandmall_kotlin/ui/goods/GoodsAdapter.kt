package com.example.elandmall_kotlin.ui.goods

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewGoodsHeaderBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsInfoBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsTabBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsTopImageBinding
import com.example.elandmall_kotlin.model.GoodsModule
import com.example.elandmall_kotlin.model.GoodsModuleType
import com.example.elandmall_kotlin.ui.goods.viewholders.GoodsHeaderHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.GoodsInfoHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.GoodsTabHolder
import com.example.elandmall_kotlin.ui.goods.viewholders.GoodsTopImageHolder

class GoodsAdapter : ListAdapter<GoodsModule, GoodsBaseViewHolder>(object : DiffUtil.ItemCallback<GoodsModule>() {
    override fun areItemsTheSame(oldItem: GoodsModule, newItem: GoodsModule): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: GoodsModule, newItem: GoodsModule): Boolean = oldItem.equals(newItem)

}) {
    override fun getItemViewType(position: Int): Int = currentList[position].type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsBaseViewHolder {
        return when (GoodsModuleType.values()[viewType]) {
            GoodsModuleType.HEADER -> GoodsHeaderHolder(
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