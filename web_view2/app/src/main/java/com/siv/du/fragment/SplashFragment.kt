package com.siv.du.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.siv.du.R
import com.siv.du.activity.MainActivity
import com.siv.du.data.SplashResponse
import com.siv.du.databinding.FragmentSplashBinding
import com.siv.du.dialog.BasicDialog
import com.pionnet.overpass.extension.loadImagePreLoad
import java.util.*

/**
 * 스플래시 화면 프래그먼트
 * api 요청결과
 * MainActivity의 frame layout에 띄움
 */
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding //view
    private lateinit var splashResponse: SplashResponse //model

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
        checkUpdate(
            splashResponse.verInfo?.appTp,
            splashResponse.verInfo?.msg
        )
    }

    private fun checkUpdate(appTp: String?, msg: String?) {
        val mainActivity = context as MainActivity
        val alertDialog = AlertDialog.Builder(mainActivity)
        if (!appTp.isNullOrEmpty()) {
            when (appTp) {
                //앱 업데이트 권장
                "2" -> {
                    mainActivity.showDialog(
                        BasicDialog.create(BasicDialog.Param.TYPE_UPDATE)
                    )
                }
                //앱 업데이트 강제
                "3" -> {
                    mainActivity.showDialog(
                        BasicDialog.create(BasicDialog.Param.TYPE_FORCE_UPDATE)
                    )
                }
                //점검
                "9" -> {
                    var message = getString(R.string.sys_check)

                    if (msg != null) {
                        message = msg //?
                    }
                    alertDialog.setMessage(message)
                }
                else -> mainActivity.removeSplashFragment()
            }
            mainActivity.removeSplashFragment()
        }
    }
}

