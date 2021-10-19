package com.example.dragpopup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.example.dragpopup.data.Promotion
import com.example.dragpopup.databinding.LayoutPopupBinding

class PopupLayout(private val mDataList: MutableList<Promotion>) : Fragment() {
    lateinit var binding: LayoutPopupBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LayoutPopupBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFlipper()
    }

    fun initFlipper() {
        mDataList.forEach { promotion ->
            val imgUrl = promotion.imgUrl

            val imgTest = ImageView(context)
            imgTest.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            binding.flipperPopupImageRow.addView(imgTest)
            Glide.with(requireContext()).load(imgUrl).into(imgTest)
        }

        val inMotion = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_in_right)
        inMotion.duration = 500
        val outMotion = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_out_left)
        outMotion.duration = 500
        with(binding.flipperPopupImageRow) {
            flipInterval = 4000
            inAnimation = inMotion
            outAnimation = outMotion
            startFlipping()
        }
    }
//    fun getChildIndex():Int{
//        return binding.flipperPopupImageRow.displayedChild
//    }
}