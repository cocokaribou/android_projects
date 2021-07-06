package com.example.youngin.common

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("BeautyPref", Context.MODE_PRIVATE)
    }

    fun getBoolean(context: Context, valueName: String, defValue: Boolean): Boolean {
        val preference = getPreference(context)
        return preference.getBoolean(valueName, defValue)
    }

    fun setBoolean(context: Context, valueName: String, value: Boolean): Boolean{
        val preference = getPreference(context)
        val editor : SharedPreferences.Editor = preference.edit()
        editor.putBoolean(valueName, value)
        editor.apply()
        return true
    }

    fun setInt(context:Context, valueName:String, value:Int){

    }

}