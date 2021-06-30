package com.example.youngin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.youngin.R
import com.example.youngin.activity.MainActivity
import com.example.youngin.data.SplashResponse
import com.example.youngin.databinding.FragmentSplashBinding
import com.pionnet.overpass.extension.loadImagePreLoad
import java.util.*

class SplashFragment : Fragment() {
    /**
     * splash fragment 설정해서 main activity에 올리기
     */
    private lateinit var binding: FragmentSplashBinding
    private lateinit var splashResponse: SplashResponse

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(
            LayoutInflater.from(context)
        )
        splashResponse = SplashResponse.getSplashResponse()!!
        return binding.root
    }

    //onActivityCreated deprecated!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val splashImgArr = splashResponse.getSplashImage()

        if (!splashImgArr.isNullOrEmpty()) {
            val random = Random()
            var index = random.nextInt(splashImgArr.size)

            if (index < splashImgArr.size) {
                binding.imgSplash.loadImagePreLoad(splashImgArr[index].imageUrl)
            }
        } else {
        }
        checkUpdate(splashResponse.verInfo?.appTp)
    }

    private fun checkUpdate(appTp: String?){
        if(!appTp.isNullOrEmpty()){
            MainActivity.instance.removeSplashFragment()
        }
    }
}

