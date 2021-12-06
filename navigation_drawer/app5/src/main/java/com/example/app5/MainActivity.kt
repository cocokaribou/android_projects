package com.example.app5

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import studios.codelight.smartloginlibrary.SmartLogin
import studios.codelight.smartloginlibrary.SmartLoginCallbacks
import studios.codelight.smartloginlibrary.SmartLoginConfig
import studios.codelight.smartloginlibrary.users.SmartUser
import studios.codelight.smartloginlibrary.util.SmartLoginException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // smart login framework
        val config = SmartLoginConfig(this@MainActivity, object : SmartLoginCallbacks {
            override fun onLoginSuccess(user: SmartUser?) {
                TODO("Not yet implemented")
            }

            override fun onLoginFailure(e: SmartLoginException?) {
                TODO("Not yet implemented")
            }

            override fun doCustomLogin(): SmartUser {
                TODO("Not yet implemented")
            }

            override fun doCustomSignup(): SmartUser {
                TODO("Not yet implemented")
            }

        })
        config.facebookAppId = "875588695918732" //jaju
    }

    val startForFacebookLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
        when(activityResult.resultCode){
            Activity.RESULT_OK -> {

            }
            Activity.RESULT_CANCELED -> {

            }
        }
    }

    val startForGoogleLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
        when(activityResult.resultCode){
            Activity.RESULT_OK -> {

            }
            Activity.RESULT_CANCELED -> {

            }
        }
    }

    val startForCustomLogin = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ activityResult ->
        when(activityResult.resultCode){
            Activity.RESULT_OK -> {

            }
            Activity.RESULT_CANCELED -> {

            }
        }
    }
}