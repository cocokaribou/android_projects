package com.example.shared_viewmodel.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.shared_viewmodel.LogHelper
import com.example.shared_viewmodel.databinding.*
import com.example.shared_viewmodel.model.MainData
import com.example.shared_viewmodel.model.StoreData
import com.example.shared_viewmodel.ui.list.ModulesAdapter.GridListHolder.Companion.CURR_PAGE
import com.example.shared_viewmodel.ui.list.ModulesAdapter.GridListHolder.Companion.PAGING_COUNT
import com.example.shared_viewmodel.MainHolder

/**
 * Modules Adapter
 * - adapter.setItemList
 */
typealias Click = ((String) -> Unit)?

class ModulesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ModulesType.values()[viewType]) {
            ModulesType.Grid -> {
                val itemBinding = VhGridListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GridListHolder(itemBinding)
            }
            ModulesType.Vertical -> {
                val itemBinding = ItemVerticalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                VerticalListHolder(itemBinding)
            }
            ModulesType.Horizontal -> {
                val itemBinding = VhHorizontalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HorizontalListHolder(itemBinding)
            }
        }
    }

    private var onItemClickListener: Click = null

    fun setOnItemClickListener(listener: Click) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = differ.currentList[position]
        if (holder is MainHolder) {
            holder.bind(item, onItemClickListener)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size
    override fun getItemViewType(position: Int): Int {
        return differ.currentList[position].type.ordinal
    }

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<MainData>() {
        override fun areItemsTheSame(oldItem: MainData, newItem: MainData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MainData, newItem: MainData): Boolean {
            return oldItem.dataList == newItem.dataList
        }
    })

    class VerticalListHolder(private val itemBinding: ItemVerticalListBinding) : MainHolder(itemBinding.root) {
        override fun bind(item: MainData, listener: Click) {
            item.data?.let { item ->
                itemBinding.itemContent = item.stoName
                itemBinding.root.setOnClickListener {
                    listener?.let {
                        it(item.stoName!!)
                    }
                }
                itemBinding.rb.rating = 100f
            }
        }
    }

    class HorizontalListHolder(private val itemBinding: VhHorizontalListBinding) : MainHolder(itemBinding.root) {
        override fun bind(item: MainData, listener: Click) {
            item.dataList?.let { list ->
                with(itemBinding.rvHorizontal) {
                    adapter = HorizontalAdapter(list, listener)
                    layoutManager = LinearLayoutManager(itemBinding.root.context, LinearLayoutManager.HORIZONTAL, false)
                }
            }
        }
    }

    fun setPagingCount(page: Int) {
        CURR_PAGE = 1
        PAGING_COUNT = if (page == 0) 1 else page
    }

    class GridListHolder(private val itemBinding: VhGridListBinding) : MainHolder(itemBinding.root) {
        private lateinit var adapter: GridAdapter
        lateinit var list: List<StoreData>
        private var totalPage = 1

        companion object {
            var CURR_PAGE = 1
            var PAGING_COUNT = 4
        }

        override fun bind(item: MainData, listener: Click): Unit = with(itemBinding) {
            adapter = GridAdapter(listener)
            list = item.dataList!!

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
}