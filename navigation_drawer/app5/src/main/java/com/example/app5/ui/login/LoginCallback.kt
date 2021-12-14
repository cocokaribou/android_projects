package com.example.app5.ui.login

import android.os.Bundle
import android.util.Log
import com.facebook.*
import com.facebook.login.LoginResult
import org.json.JSONObject

class LoginCallback:FacebookCallback<LoginResult> {
    override fun onSuccess(result: LoginResult?) {
        requestProfile(result?.accessToken)
        Log.e("youngin", "onSuccess ${result?.accessToken}")
    }

    override fun onCancel() {
        Log.e("youngin", "onCancel")
    }

    override fun onError(error: FacebookException?) {
        Log.e("youngin", "onError ${error?.message}")
    }

    private fun requestProfile(token:AccessToken?){
        val request = GraphRequest.newMeRequest(token) { `object`, response ->
            Log.e("youngin", "$`object` || $response")
        }
        val bundle = Bundle()
        bundle.putString("fields", "id,name,gender,birthday")
        request.parameters = bundle //
        request.executeAsync()
    }
}