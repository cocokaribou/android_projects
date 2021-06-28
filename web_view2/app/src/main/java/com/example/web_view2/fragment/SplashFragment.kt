package com.example.web_view2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.web_view2.R
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
        return inflater.inflate(R.layout.fragment_splash, container, false)

//        binding = DataBindingUtil.inflate(
//            LayoutInflater.from(context),
//            R.layout.fragment_splash,
//            container,
//            false
//        )
//        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}

