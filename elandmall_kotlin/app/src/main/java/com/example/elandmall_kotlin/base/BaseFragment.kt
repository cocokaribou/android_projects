package com.example.elandmall_kotlin.base

import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    companion object {
        const val KEY_ITEM_TAB_NAME = "keyItemTabName"
        const val KEY_ITEM_URL = "keyItemUrl"
    }
}