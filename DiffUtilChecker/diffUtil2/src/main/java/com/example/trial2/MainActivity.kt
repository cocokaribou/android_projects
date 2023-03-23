package com.example.trial2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.trial2.databinding.ActivityMainBinding

val list0 = listOf(
    ModuleData.Module1("module1", "check!", listOf("1", "2", "3"))
)
val list1 = listOf(
    ModuleData.Module1("module1", "check!?", listOf("2", "3"))
)
val list2 = listOf(
    ModuleData.Module1("module1", "check!", listOf("1", "2", "3")),
    ModuleData.Module2("module2", "check!", listOf("1", "2", "3"))
)
val list3 = listOf(
    ModuleData.Module1("module1", "check!", listOf("1")),
    ModuleData.Module2("module2", "check!", listOf("1", "2", "3"))
)
val list4 = listOf(
    ModuleData.Module2("module2", "check!", listOf("1")),
    ModuleData.Module1("module1", "check!", listOf("1", "2", "3"))
)
val list5 = listOf(
    ModuleData.Module2("module2", "check!", listOf("3", "1")),
    ModuleData.Module1("module1", "check!", listOf("2", "3"))
)
var counter = 0

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mAdapter by lazy { MainAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        initUI()
    }

    private fun initUI() = with(binding) {
        list.adapter = mAdapter

        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
            when (counter % 6) {
                0 -> mAdapter.submitList(list0)
                1 -> mAdapter.submitList(list1)
                2 -> mAdapter.submitList(list2)
                3 -> mAdapter.submitList(list3)
                4 -> mAdapter.submitList(list4)
                5 -> mAdapter.submitList(list5)
            }
            counter++

        }
    }
}