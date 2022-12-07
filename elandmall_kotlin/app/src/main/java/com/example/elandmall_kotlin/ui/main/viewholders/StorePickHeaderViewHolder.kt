package com.example.elandmall_kotlin.ui.main.viewholders

import androidx.fragment.app.FragmentActivity
import com.example.elandmall_kotlin.databinding.ViewStorePickHeaderBinding
import com.example.elandmall_kotlin.ui.*
import com.example.elandmall_kotlin.ui.main.tabs.BottomSheetFragment
import com.example.elandmall_kotlin.ui.main.tabs.DialogType

class StorePickHeaderViewHolder(private val binding: ViewStorePickHeaderBinding) : BaseViewHolder(binding.root) {
    override fun onBind(item: Any, pos: Int) {
        (item as? ModuleData.StorePickHeaderData)?.let {
            initUI(it)
        }
    }

    private fun initUI(data: ModuleData.StorePickHeaderData) = with(binding){
        storeNm.text = data.storeSelected
        storeMore.setOnClickListener {
            BottomSheetFragment(
                DialogType.STORE_PICK,
                data.storePickData
            ).show((binding.root.context as FragmentActivity).supportFragmentManager, "")
        }
    }
}