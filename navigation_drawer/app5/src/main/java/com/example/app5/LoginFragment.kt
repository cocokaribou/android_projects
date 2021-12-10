package com.example.app5

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.app5.databinding.FragmentLoginBinding
import com.facebook.FacebookSdk
import studios.codelight.smartloginlibrary.LoginType
import studios.codelight.smartloginlibrary.SmartLoginCallbacks
import studios.codelight.smartloginlibrary.SmartLoginConfig
import studios.codelight.smartloginlibrary.SmartLoginFactory
import studios.codelight.smartloginlibrary.users.SmartUser
import studios.codelight.smartloginlibrary.util.SmartLoginException

class LoginFragment: Fragment(R.layout.fragment_login), SmartLoginCallbacks{
    lateinit var binding : FragmentLoginBinding
    lateinit var config : SmartLoginConfig

    lateinit var user : SmartUser

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        config = SmartLoginConfig(requireActivity(), this)
        config.facebookAppId = "875588695918732"

        initButton()
    }

    override fun onLoginSuccess(user: SmartUser?) {
    }

    override fun onLoginFailure(e: SmartLoginException?) {
    }

    override fun doCustomLogin(): SmartUser {
        Log.e("youngin","custom login : $user")
        return user
    }

    override fun doCustomSignup(): SmartUser {
        return user
    }

    fun initButton(){
        binding.btnLogin.setOnClickListener {
            binding.apply{
                if(etId.text.isEmpty() || etPw.text.isEmpty()){
                    AlertDialog.Builder(requireContext())
                        .setMessage(getString(R.string.alert_empty_input))
                        .setPositiveButton(getString(R.string.confirm), DialogInterface.OnClickListener{ dialog, which ->
                            dialog.dismiss()
                        })
                        .show()

                }else{
                    user = SmartUser()
                    user.userId = etId.text.toString()
                    etId.text.clear()
                    etPw.text.clear()
                    doCustomLogin()
                }
            }
        }


        binding.btnFacebook.setOnClickListener {
//            val smartLogin = SmartLoginFactory.build(LoginType.Facebook)
//            smartLogin.login(config)
//            FacebookSdk.sdkInitialize(requireContext())
            (requireActivity() as MainActivity).startForFacebookLogin.launch(Intent(Intent.ACTION_VIEW, Uri.parse("https://facebook.com")))
        }

        binding.btnGoogle.setOnClickListener {
        }
    }
}