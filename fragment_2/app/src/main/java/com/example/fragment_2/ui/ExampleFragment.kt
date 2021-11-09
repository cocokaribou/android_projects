package com.example.fragment_2.ui

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragment_2.Logger
import com.example.fragment_2.R
import com.example.fragment_2.databinding.FragmentExampleBinding

class ExampleFragment : Fragment(R.layout.fragment_example) {

    private lateinit var binding: FragmentExampleBinding
    private var initString = ""

    // onInflate -> onCreateView

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
        Logger.e("${javaClass.simpleName} onInflate")
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.e("${javaClass.simpleName} onCreateView")

        initString = requireArguments().getString("some_string") ?: "no init data"
        // 이렇게 못 꺼내옴
//        initString = savedInstanceState!!.getString("some_string") ?: "no init data"

        Logger.e(initString)
        binding = FragmentExampleBinding.inflate(layoutInflater)
        binding.tvInit.text = initString

        return binding.root
    }
}