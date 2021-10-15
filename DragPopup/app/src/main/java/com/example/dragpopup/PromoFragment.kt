package com.example.dragpopup

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.dragpopup.databinding.FragmentPromoBinding
import com.tuanhav95.drag.DragView

class PromoFragment(val url: String, val dragview: DragView) : Fragment() {
    lateinit var binding: FragmentPromoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPromoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webviewPromo.loadUrl(url)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                // activity 위에 떠 있는 fragment 전부 내리기
                val frags = requireActivity().supportFragmentManager.fragments
                frags.forEach { frag ->
                    requireActivity().supportFragmentManager.beginTransaction()
                        .remove(frag)
                        .commit()
                }
                dragview.close()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }


}