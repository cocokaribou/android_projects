package com.example.elandmall_kotlin.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.elandmall_kotlin.databinding.*
import com.example.elandmall_kotlin.model.LeftMenuModule
import com.example.elandmall_kotlin.model.ModuleType
import com.example.elandmall_kotlin.ui.category.viewholders.*
import com.example.elandmall_kotlin.ui.main.ordinal

class LeftMenuAdapter : ListAdapter<LeftMenuModule, LeftMenuBaseViewHolder>(object : DiffUtil.ItemCallback<LeftMenuModule>() {
    override fun areItemsTheSame(oldItem: LeftMenuModule, newItem: LeftMenuModule): Boolean = oldItem == newItem
    override fun areContentsTheSame(oldItem: LeftMenuModule, newItem: LeftMenuModule): Boolean = oldItem.equals(newItem)
}) {
    override fun getItemViewType(position: Int): Int = currentList[position].type.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeftMenuBaseViewHolder {
        return when (currentList[viewType].type) {
            ModuleType.DIVIDER -> {
                Divider(
                    ViewLnbDividerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ModuleType.RECENTLY -> {
                RecentlyHolder(
                    ViewLnbRecentlyBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ModuleType.CATEGORY -> {
                CategoryHolder(
                    ViewLnbCategoryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ModuleType.BRAND -> {
                BrandHolder(
                    ViewLnbBrandBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ModuleType.SHOP -> {
                ShopHolder(
                    ViewLnbShopBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ModuleType.SERVICE_MENU -> {
                ServiceHolder(
                    ViewLnbServiceBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> FooterHolder(
                ViewLnbFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: LeftMenuBaseViewHolder, position: Int) {
        holder.onBind()
    }
}

abstract class LeftMenuBaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun onBind() {}
}