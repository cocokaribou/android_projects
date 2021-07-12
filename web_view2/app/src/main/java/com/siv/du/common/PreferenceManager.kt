package com.siv.du.common

import android.content.Context
import android.content.SharedPreferences

/**
 * shared preference 세팅하는 Preference Manager
 * Boolean, Int, String get/set하는 보일러플레이트
 */
object PreferenceManager {
    fun getPreference(context: Context): SharedPreferences {
        return context.getSharedPreferences("BeautyPref", Context.MODE_PRIVATE)
    }

    fun getBoolean(context: Context, valueName: String, defValue: Boolean): Boolean {
        val preference = getPreference(context)
        return preference.getBoolean(valueName, defValue)
    }

    fun setBoolean(context: Context, valueName: String, value: Boolean): Boolean {
        val preference = getPreference(context)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putBoolean(valueName, value)
        editor.apply()
        return true
    }

    fun setInt(context: Context, valueName: String, value: Int) {
        val preference = getPreference(context)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putInt(valueName, value)
        editor.apply()
    }

    fun getInt(context: Context, valueName: String, defValue: Int): Int {
        val preference = getPreference(context)
        return preference.getInt(valueName, defValue)
    }

    fun setString(context: Context, valueName: String, value: String) {
        val preference = getPreference(context)
        val editor: SharedPreferences.Editor = preference.edit()
        editor.putString(valueName, value)
        editor.apply()
    }

    fun getString(context: Context, valueName: String, defValue: String): String {
        val preference = getPreference(context)
        return preference.getString(valueName, defValue)!!
    }

}