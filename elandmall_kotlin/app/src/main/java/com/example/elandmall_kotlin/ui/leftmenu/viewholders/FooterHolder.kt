package com.example.elandmall_kotlin.ui.leftmenu.viewholders

import android.widget.Toast
import com.example.elandmall_kotlin.databinding.ViewLnbFooterBinding
import com.example.elandmall_kotlin.ui.leftmenu.LeftMenuBaseViewHolder

class FooterHolder(val binding: ViewLnbFooterBinding) : LeftMenuBaseViewHolder(binding.root) {
    override fun onBind(item: Any?) {
        initUI()
    }

    private fun initUI() = with(binding){
        login.setOnClickListener {
            Toast.makeText(root.context, "login", Toast.LENGTH_SHORT).show()
        }

        cs.setOnClickListener {
            Toast.makeText(root.context, "cs", Toast.LENGTH_SHORT).show()
        }

        setting.setOnClickListener {
            Toast.makeText(root.context, "setting", Toast.LENGTH_SHORT).show()
        }
    }
}