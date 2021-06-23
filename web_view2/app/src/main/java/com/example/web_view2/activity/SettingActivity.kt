package com.example.web_view2.activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.web_view2.databinding.ActivitySettingBinding
import com.pionnet.overpass.extension.getAppVersion

class SettingActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAppVersion()

        binding.buttonGoBack.setOnClickListener {
            finish()
        }
        binding.txtDeleteCache.setOnClickListener {
            try {
                val dir = this.cacheDir
                val result = dir.deleteRecursively()
                val toast = Toast.makeText(this, "삭제 성공여부 ${result}", Toast.LENGTH_SHORT)
                toast.show()
            } catch (e: Exception) {
                e.stackTrace
            }
        }

    }

    private fun initAppVersion(){
        binding.txtAppVersion.text = getAppVersion(this)
    }
}