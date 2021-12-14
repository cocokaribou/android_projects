package com.example.app5

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


class CustomTabActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("onCreate", "when will it be called")
    }

}