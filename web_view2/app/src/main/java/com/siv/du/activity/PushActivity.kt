package com.siv.du.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.siv.du.databinding.ActivityPushBinding

class PushActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPushBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPushBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO 로그인여부 체크
    }
}