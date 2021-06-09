package com.example.aos_framework_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.databinding.ActivityStickyBinding
import com.example.aos_framework_demo.databinding.ItemRecyclerBinding
import com.example.aos_framework_demo.databinding.ItemStickyBinding
import com.pionnet.overpass.customView.StickyHeaderItemDecoration

class StickyActivity: AppCompatActivity() {
    private lateinit var binding: ActivityStickyBinding
    private lateinit var headerBinding: ItemStickyBinding

    val testList: List<String> = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStickyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val verticalLayoutManager = LinearLayoutManager(
            this@StickyActivity,
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.stickyRecyclerView.adapter = TestAdapter(testList)
        binding.stickyRecyclerView.layoutManager = verticalLayoutManager
        binding.stickyRecyclerView.addItemDecoration(
            StickyHeaderItemDecoration(
                stickyHeaderInterface
            )
        )

        //sticky header 생성
        headerBinding = ItemStickyBinding.inflate(layoutInflater, binding.stickyHolder, false)
        binding.stickyHolder.addView(headerBinding.root)
        binding.stickyHolder.isVisible = false

    }

    private var stickyHeaderInterface: StickyHeaderItemDecoration.StickyHeaderInterface = object : StickyHeaderItemDecoration.StickyHeaderInterface {
        override fun getHeaderPositionForItem(itemPosition: Int): Int {
            return 0
        }

        override fun getHeaderLayout(headerPosition: Int): Int {
            return R.layout.item_sticky
        }

        override fun isHeader(itemPosition: Int): Boolean {
            return itemPosition < 2
        }

        override fun hideHeader() {
            binding.stickyHolder.isVisible = false
        }

        override fun showHeader() {
            binding.stickyHolder.isVisible = true
        }
    }

    class TestAdapter(private var testList: List<String>) :
        RecyclerView.Adapter<TestAdapter.ItemHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val itemBinding = ItemRecyclerBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )

            return ItemHolder(itemBinding)
        }

        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            val category = testList[position]
            holder.bind(category)
        }

        override fun getItemCount(): Int {
            return testList.size
        }

        class ItemHolder(private val itemBinding: ItemRecyclerBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {

            fun bind(item: String) {
                itemBinding.text.text = item
            }
        }
    }

}