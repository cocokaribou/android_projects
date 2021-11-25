package com.example.fragment_2

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragment_2.databinding.FragmentExampleBinding
import com.example.fragment_2.ui.WebFragment
import com.pionnet.overpass.customView.dialog.ToolTipDialog
import dpToPx

class RootFragment : Fragment(R.layout.fragment_example) {

    private lateinit var binding: FragmentExampleBinding
    private var initString = ""

    // onInflate -> onCreateView

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        initString = requireArguments().getString("some_string") ?: "no init data"
        // 이렇게 못 꺼내옴
//        initString = savedInstanceState!!.getString("some_string") ?: "no init data"

        binding = FragmentExampleBinding.inflate(layoutInflater)
        binding.tvInit.text = initString

        binding.clickable.setOnClickListener {
            binding.clickable.let{ view ->
                val location = intArrayOf(0,0)
                it.getLocationInWindow(location)
                ToolTipDialog(view.context, requireActivity())
                    .pointTo(location[0]+view.width/2, location[1]+context!!.dpToPx(10F))
                    .content("pointing the box").also{
                        it.show()
                    }
            }
        }

        binding.button.setOnClickListener {
            childFragmentManager.beginTransaction().add(WebFragment(), "webfragment").commit()
        }

        return binding.root
    }
}