package com.example.youngin.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.youngin.databinding.ActivitySettingBinding
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

        binding.txtTitle.setOnLongClickListener {
            val toast = Toast.makeText(this, "ta-da", Toast.LENGTH_LONG)
            toast.show()
            true
        }

    }

    private fun initAppVersion(){
        binding.txtAppVersion.text = getAppVersion(this)
    }
}