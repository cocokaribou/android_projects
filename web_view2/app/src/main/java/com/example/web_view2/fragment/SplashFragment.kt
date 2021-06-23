package com.example.web_view2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.web_view2.databinding.FragmentSplashBinding

class SplashFragment : Fragment(){
    /**
     * splash fragment 설정해서 main activity에 올리기
     */
    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = DataBindingUtil.inflate(
//            layoutInflater,
//            R.layout.fragment_splash,
//            null,
//            false
//        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}