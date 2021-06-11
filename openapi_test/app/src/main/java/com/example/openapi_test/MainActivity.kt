package com.example.openapi_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val KEY = "524d4b724e6a6f79313037777a6e6377"
    private val domain = "http://openapi.seoul.go.kr:8088/(인증키)/xml/LOCALDATA_072218_GS/1/5/"
    private val pattern = "\\([^)]*\\)" //괄호안 정규식

    lateinit var binding: ActivityMainBinding
    lateinit var mainRecycler : RecyclerView
    lateinit var mainAdapter : mAdapter
    lateinit var linearManager : LinearLayoutManager

    private val list = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecycler()
        requestApi()
    }

    fun setRecycler(){
        mainAdapter = mAdapter(list)
        linearManager = LinearLayoutManager(this@MainActivity)
        binding.recyclerViewMainList.also{
            it.adapter = mainAdapter
            it.layoutManager = linearManager
        }
    }

    fun requestApi(){
        
    }

}