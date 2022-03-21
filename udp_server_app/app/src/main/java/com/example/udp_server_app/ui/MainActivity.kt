package com.example.udp_server_app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.udp_server_app.CommonConst
import com.example.udp_server_app.R
import com.example.udp_server_app.ui.client.ClientActivity
import com.example.udp_server_app.ui.profile.ProfileFragment
import com.example.udp_server_app.ui.server.ServerActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_set_profile).let { btnSetProfile ->
            btnSetProfile.setOnClickListener {
                addFrag(ProfileFragment(), CommonConst.PROFILE_FRAG)
            }
        }

        findViewById<Button>(R.id.btn_enter_room).let { btnEnterRoom ->
            btnEnterRoom.setOnClickListener {
                startActivity(Intent(this, ClientActivity::class.java))
            }
        }

        findViewById<Button>(R.id.btn_make_room).let { btnMakeRoom ->
            btnMakeRoom.setOnClickListener {
                startActivity(Intent(this, ServerActivity::class.java))
            }
        }
    }

    fun addFrag(frag: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .add(R.id.frag_container, frag, tag)
            .commitNow()
    }

    fun removeFrag(frag: Fragment) {
        supportFragmentManager.beginTransaction()
            .remove(frag)
            .commitNow()
    }
}