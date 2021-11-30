package com.example.fragment_2

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragment_2.databinding.FragmentRootBinding
import com.example.fragment_2.ui.WebFragment
import com.pionnet.overpass.module.LogHelper

class RootFragment : Fragment(R.layout.fragment_root) {

    private lateinit var binding: FragmentRootBinding
    private var initString = ""

    // onInflate -> onCreateView

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initString = requireArguments().getString("some_string") ?: "no init data"
        // 이렇게 못 꺼내옴
//        initString = savedInstanceState!!.getString("some_string") ?: "no init data"

        binding = FragmentRootBinding.inflate(layoutInflater)
        binding.tvInit.text = initString

        binding.clickable.setOnClickListener {
            binding.clickable.let{ view ->
                val location = intArrayOf(0,0)
                it.getLocationInWindow(location)
            }
        }

        binding.button.setOnClickListener {
            LogHelper.e("clicked!")
            childFragmentManager.beginTransaction().add(WebFragment(), "webfragment").commit()
        }

        return binding.root
    }
}