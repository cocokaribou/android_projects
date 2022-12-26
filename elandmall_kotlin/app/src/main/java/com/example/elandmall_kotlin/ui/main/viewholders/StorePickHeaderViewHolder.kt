package com.example.elandmall_kotlin.ui.main.viewholders

import androidx.fragment.app.FragmentActivity
import com.example.elandmall_kotlin.databinding.ViewStorePickHeaderBinding
import com.example.elandmall_kotlin.model.StoreShopResponse
import com.example.elandmall_kotlin.ui.*
import com.example.elandmall_kotlin.ui.main.tabs.BottomSheetFragment

class StorePickHeaderViewHolder(private val binding: ViewStorePickHeaderBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StorePickHeaderData)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.StorePickHeaderData) = with(binding) {
        storeNm.text = data.list[data.initIndex].relContNm
        storeMore.setOnClickListener {
            BottomSheetFragment(
                sortCallback = data.selectStore,
                initIndex = data.initIndex,
                list = data.list
            ).show((binding.root.context as FragmentActivity).supportFragmentManager, "")
        }
    }
}