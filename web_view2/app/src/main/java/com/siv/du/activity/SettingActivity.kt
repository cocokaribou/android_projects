package com.siv.du.activity

/*
설정화면 네이티브 아닌 웹으로 제작

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.siv.du.R
import com.siv.du.base.BaseActivity
import com.siv.du.data.SplashResponse
import com.siv.du.databinding.ActivitySettingBinding
import com.pionnet.overpass.extension.getAppVersion

*/
/**
 * 웹뷰에서 설정화면 스킴(setting://) 받아서 띄우는 네이티브 화면
 *//*

class SettingActivity : BaseActivity() {
    private lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initGoBack()
        initDeleteCache()
        initAppVersion()
//        initCsTel()
        initDeveloperMode()
        Log.e("$tag checker!", "intent.data.scheme: ${intent.data?.scheme}")

    }

    private fun initGoBack() {
        binding.buttonGoBack.setOnClickListener {
            finish()
        }
    }

    private fun initDeleteCache() {
        binding.txtDeleteCache.setOnClickListener {
            val dir = this.cacheDir
            val result = dir.deleteRecursively()
            Toast.makeText(this, "삭제 성공여부 ${result}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initAppVersion() {
        binding.txtAppVersion.text = getAppVersion(this)
    }

//    private fun initCsTel() {
//        val csTelNo = SplashResponse.getSplashResponse()?.settingInfo?.csTelno ?: null
//        if (!csTelNo.isNullOrEmpty()) {
//            binding.txtCallNumber.text = "전화연결: $csTelNo"
//            binding.txtCallNumber.setOnClickListener {
//                callIntent("tel:$csTelNo")
//            }
//        }
//    }

    private fun initDeveloperMode() {
        binding.txtTitle.setOnLongClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setMessage("서버 변경")
            val editText = EditText(this)
            editText.setSingleLine()
            builder.setView(editText)

            val serverTypeArr = resources.getStringArray(R.array.server_type)

            val adapter =
                ArrayAdapter<String>(this, R.layout.dialog_server_type, R.id.txt_serverName)
            adapter.addAll(*serverTypeArr)

            builder.setPositiveButton("확인") { dialog, _ ->
                if (editText.text.toString() == "puga") {
                    val innerBuilder = AlertDialog.Builder(this)
                    innerBuilder.setTitle("변경할 서버를 선택하세요")
                    innerBuilder.setAdapter(adapter) { dialog, itemIndex ->
                        Log.e("$tag checker!", "adapter index: $itemIndex")

                    }
                    innerBuilder.show()
                }

            }
            builder.show()

            true
            //TODO 서버변경 다이얼로그
        }
    }

}*/
