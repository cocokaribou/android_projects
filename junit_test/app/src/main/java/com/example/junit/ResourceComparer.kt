package com.example.junit

import android.content.Context

class ResourceComparer {

    // needs context object, which is specific to android
    fun isEqual(context: Context, resId: Int, string: String):Boolean{
        return context.getString(resId) == string
    }
}