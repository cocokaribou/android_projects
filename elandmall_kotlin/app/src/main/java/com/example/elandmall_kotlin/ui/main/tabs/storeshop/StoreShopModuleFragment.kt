package com.example.elandmall_kotlin.ui.main.tabs.storeshop

import android.media.AudioRecord.MetricsConstants.SOURCE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.children
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.ViewCategoryItemBinding
import com.example.elandmall_kotlin.databinding.ViewHomeCategoryItemBinding
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.StoreShopEvent
import com.example.elandmall_kotlin.ui.StoreShopEventType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.dpToPx
import com.example.elandmall_kotlin.util.getScreenWidthToPx
import com.example.elandmall_kotlin.util.pxToDp

class StoreShopModuleFragment : BaseModuleFragment() {

    override val viewModel: StoreShopViewModel by viewModels()

    val cateAdapter by lazy { CategoryAdapter() }

    override fun observeData() {
        viewModel.storeShopList.observe(this) {
            viewModel.setStoreShopModules(it)
        }

        viewModel.uiList.observe(this) {
            setModules(it)
        }
        viewModel.cateList.observe(this) {
            cateAdapter.submitList(it)
        }

        EventBus.storeShopEvent.observe(requireActivity()) {
            it.getIfNotHandled()?.let {
                when (it.type) {
                    StoreShopEventType.SELECT_TAB -> {
                        with(binding.sticky.adapter as CategoryAdapter) {
                            select(it.pos)
                        }
                    }
                    StoreShopEventType.CHANGE_VIEW_HOLDER -> {}
                }
            }
        }
    }

    // sticky tab pager
    override fun setStickyTab(isOn: Boolean) = with(binding.sticky) {
        visibility =
            if (isOn) View.VISIBLE
            else View.GONE

        adapter = cateAdapter
    }

    // 1. scroll list
    // 2. select tab
    override fun selectTab(pos: Int) {
        binding.sticky.scrollToPosition(pos)
        EventBus.fire(StoreShopEvent(StoreShopEventType.SELECT_TAB, pos))
    }

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            StoreShopModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}