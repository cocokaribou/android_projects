package com.example.mvvm_simple_app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_simple_app.model.WeatherInfo

class MainViewModel: ViewModel() {
    val currentWeather: MutableLiveData<WeatherInfo> by lazy {
        MutableLiveData<WeatherInfo>()
    }

    fun clear() {
        Log.d("$this", "cleared")
        // but what does it do?????
        super.onCleared()
    }
}