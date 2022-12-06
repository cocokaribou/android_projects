package com.example.elandmall_kotlin.ui.main.viewholders

import androidx.fragment.app.FragmentActivity
import com.example.elandmall_kotlin.databinding.ViewStoreShopPickHeaderBinding
import com.example.elandmall_kotlin.ui.*
import com.example.elandmall_kotlin.ui.main.tabs.BottomSheetFragment
import com.example.elandmall_kotlin.ui.main.tabs.DialogType
import com.example.elandmall_kotlin.util.Logger

class StoreShopPickHeaderViewHolder(private val binding: ViewStoreShopPickHeaderBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StoreShopPickHeaderData)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.StoreShopPickHeaderData) = with(binding){
        val index = data.storeShopPickData.indexOfFirst { it.categoryNo.equals(data.selected) }

        storeNm.text = data.storeShopPickData[index].relContNm
        storeMore.setOnClickListener {
            BottomSheetFragment(
                DialogType.STORE_PICK,
                data.storeShopPickData
            ).show((binding.root.context as FragmentActivity).supportFragmentManager, "")
        }
    }
}