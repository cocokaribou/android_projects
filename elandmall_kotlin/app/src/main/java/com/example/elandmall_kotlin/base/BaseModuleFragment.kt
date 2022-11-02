package com.example.elandmall_kotlin.base

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.elandmall_kotlin.R
import com.example.elandmall_kotlin.databinding.FragmentTabCommonBinding

open class BaseModuleFragment: Fragment() {

    companion object {
        const val KEY_ITEM_TAB_NAME = "keyItemTabName"
        const val KEY_ITEM_URL = "keyItemUrl"
    }

    protected val binding by viewBinding(FragmentTabCommonBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.text.text = "아 뭐냐궁"
    }
}