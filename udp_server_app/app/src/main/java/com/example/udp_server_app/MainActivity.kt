package com.example.udp_server_app

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.udp_server_app.R

class MainActivity : AppCompatActivity() {

    lateinit var btnSetProfile : Button
    lateinit var btnMakeRoom : Button
    lateinit var btnEnterRoom : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSetProfile = findViewById(R.id.btn_set_profile)
        btnMakeRoom = findViewById(R.id.btn_make_room)
        btnEnterRoom = findViewById(R.id.btn_enter_room)

        initButtons()
    }

    private fun initButtons() {
        btnSetProfile.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .add(ProfileFragment, "profile")
        }
        btnEnterRoom.setOnClickListener {
            startActivity(Intent(this, ServerActivity::class.java))
        }
    }
}