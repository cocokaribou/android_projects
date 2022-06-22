package com.example.shared_viewmodel.ui.list.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import com.example.shared_viewmodel.databinding.VhGridListBinding
import com.example.shared_viewmodel.model.ModuleData
import com.example.shared_viewmodel.model.StoreData
import com.example.shared_viewmodel.ui.list.BaseHolder
import com.example.shared_viewmodel.ui.list.Click
import com.example.shared_viewmodel.ui.list.GridAdapter

class GridListHolder(private val itemBinding: VhGridListBinding) : BaseHolder(itemBinding.root) {
    private lateinit var adapter: GridAdapter
    lateinit var list: List<StoreData>
    private var totalPage = 1

    private var CURR_PAGE = 1
    private val PAGING_COUNT = 4

    override fun bind(item: ModuleData, listener: Click): Unit = with(itemBinding) {
        adapter = GridAdapter(listener)
        list = item.storeList!!

        totalPage = if (list.size % PAGING_COUNT == 0) {
            list.size / PAGING_COUNT
        } else {
            list.size / PAGING_COUNT + 1
        }

        paging()

        ivLeftArrow.setOnClickListener { decPageCount() }
        ivRightArrow.setOnClickListener { incPageCount() }

        rvGrid.adapter = adapter
        rvGrid.layoutManager = GridLayoutManager(itemBinding.root.context, 2)
    }

    private fun incPageCount() {
        if (CURR_PAGE < totalPage) {
            ++CURR_PAGE
        } else {
            CURR_PAGE = 1
        }
        paging()
    }

    private fun decPageCount() {
        if (CURR_PAGE > 1) {
            --CURR_PAGE
        } else {
            CURR_PAGE = totalPage
        }
        paging()
    }

    private fun paging() {
        val start = (CURR_PAGE - 1) * PAGING_COUNT
        val end = if (list.size < CURR_PAGE * PAGING_COUNT) list.size - 1 else start + PAGING_COUNT - 1
        val newList = list.slice(start .. end).toMutableList()

        // column count fixed
        val totalRow = PAGING_COUNT / 2
        val currRow = if (newList.size % 2 != 0) newList.size / 2 + 1 else newList.size / 2
        val emptyHolderCount = (totalRow - currRow) * 2

        // ui for empty row
        if (emptyHolderCount > 0) {
            for (i in 0 until emptyHolderCount) {
                val emptyData = StoreData(0, "")
                newList.add(emptyData)
            }
        }

        adapter.differ.submitList(newList.toList())
    }
}