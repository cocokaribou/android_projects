package com.example.youngin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BaseActivity: AppCompatActivity(){
    var mLandingInfo:String? = null
    var mLandingType = 0

    private val permissionCall = 100
    private val mTelNo:String?= null  //? 핸드폰번호가..아 전화거는 intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}