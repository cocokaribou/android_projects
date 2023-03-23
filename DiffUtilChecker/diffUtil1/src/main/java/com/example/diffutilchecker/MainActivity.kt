package com.example.diffutilchecker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.diffutilchecker.viewholders.Holder1
import com.example.diffutilchecker.viewholders.Holder2
import java.util.*

class MainActivity : AppCompatActivity(R.layout.actvity_main) {
    private var list0 = listOf(
        Data.Data1(itemList= listOf("1","2")),
        Data.Data2(itemList = listOf("1","2"))
    )
    private var list1 = listOf(
        Data.Data2(itemList = listOf("2","1")),
        Data.Data1(itemList = listOf("1"))
    )
    private var list2 = listOf(
        Data.Data2(itemList = listOf("2"))
    )

    var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = findViewById<RecyclerView>(R.id.list)

        val mAdapter = MainAdapter()
        recyclerView.adapter = mAdapter

        val swipe = findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipe.setOnRefreshListener {
            when (counter % 3) {
                0 -> mAdapter.submitList(list0)
                1 -> mAdapter.submitList(list1)
                2 -> mAdapter.submitList(list2)
            }
            Log.v("youngin", "---- swipe ---- list${counter % 3} ----")
            counter++
            swipe.isRefreshing = false
        }
    }
}
