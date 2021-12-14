package com.example.app5.data

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit

object UserDataManager {
    lateinit var preferences: SharedPreferences
    fun init(context: Context) {
        preferences = context.getSharedPreferences(context.packageName, Activity.MODE_PRIVATE)
    }

    val user = User()

    var autoLogin: Boolean
        get() = preferences.getBoolean("autoLogin", false)
        set(value) = preferences.edit {
            putBoolean("autoLogin", value)
        }

    var saveId: Boolean
        get() = preferences.getBoolean("saveId", false)
        set(value) = preferences.edit {
            putBoolean("saveId", value)
        }

    fun printUserData(){
        Log.e("UserDataManager", "autoLogin  :$autoLogin")
        Log.e("UserDataManager", "saveId     :$saveId")
    }
}