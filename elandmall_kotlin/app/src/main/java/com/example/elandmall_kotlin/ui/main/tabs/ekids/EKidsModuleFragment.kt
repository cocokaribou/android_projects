package com.example.elandmall_kotlin.ui.main.tabs.ekids

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.example.elandmall_kotlin.ui.EventBus
import com.example.elandmall_kotlin.ui.TabType
import com.example.elandmall_kotlin.ui.main.BaseModuleFragment
import com.example.elandmall_kotlin.ui.main.BaseViewModel
import com.example.elandmall_kotlin.ui.main.tabs.plandetail.PlanDetailModuleFragment

class EKidsModuleFragment : BaseModuleFragment() {
    override val viewModel: EKidsViewModel by viewModels()

    override fun observeData() {
        viewModel.uiList.observe(this) {
            setModules(it)
        }

//        EventBus.viewHolderEvent.observe(requireActivity()) {
//            it.getIfNotHandled()?.let { event ->
//                if(event.tabType == TabType.EKIDS) {
//                }
//            }
//        }
    }

    fun expand() {

    }

    companion object {
        fun create(tabName: String, apiUrl: String = "") =
            EKidsModuleFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_ITEM_TAB_NAME, tabName)
                    if (apiUrl.isNotEmpty()) {
                        putString(KEY_ITEM_URL, apiUrl)
                    }
                }
            }
    }
}