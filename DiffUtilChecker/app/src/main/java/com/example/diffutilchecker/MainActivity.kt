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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

abstract class Test(
    var id: UUID,
    var type: String
)

class Test1(
    var itemList: List<String>
) : Test(id = UUID.randomUUID(), "holder1")

class Test2(
    var itemList: List<String>
) : Test(id = UUID.randomUUID(), "holder2")

class MainActivity : AppCompatActivity(R.layout.actvity_main) {
    private var list1 = listOf(
        Test1(listOf("1", "2")),
        Test2(listOf("1", "2"))
    )
    private var list2 = listOf(
        Test2(listOf("1")),
        Test1(listOf("1"))
    )
    private var list3 = listOf(
        Test2(listOf("1", "2"))
    )

    var counter = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recyclerView = findViewById<RecyclerView>(R.id.list)

        val mAdapter = Adapter()
        recyclerView.adapter = mAdapter

        mAdapter.submitList(list1)

        val swipe = findViewById<SwipeRefreshLayout>(R.id.swipe)
        swipe.setOnRefreshListener {
            when (counter % 3) {
                0 -> {
                    Log.v("youngin", "swipe! ---------------------큰홀더 작은홀더")
                    mAdapter.submitList(list2)
                }
                1 -> {
                    Log.v("youngin", "swipe! ---------------------큰홀더")
                    mAdapter.submitList(list3)
                }
                2 -> {
                    Log.v("youngin", "swipe! ---------------------작은홀더 큰홀더")
                    mAdapter.submitList(list1)
                }
            }
            counter++
            swipe.isRefreshing = false

        }
    }

    class Adapter : ListAdapter<Test, ViewHolder>(object : DiffUtil.ItemCallback<Test>() {
        override fun areContentsTheSame(oldItem: Test, newItem: Test): Boolean = oldItem.equals(newItem)
        override fun areItemsTheSame(oldItem: Test, newItem: Test): Boolean = oldItem.id == newItem.id
    }) {
        override fun getItemViewType(position: Int) = position

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            Log.v("youngin", "⚠️onCreate[$viewType]")
            return if (currentList[viewType].type == "holder1") {
                Holder1(LayoutInflater.from(parent.context).inflate(R.layout.view_recycler_holder, parent, false))
            } else {
                Holder2(LayoutInflater.from(parent.context).inflate(R.layout.view_recycler_holder2, parent, false))
            }
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            (currentList[position] as? Test1)?.let {
                Log.v("youngin", "작은홀더 그립니당 ${currentList[position].javaClass.simpleName} | ${holder.javaClass.simpleName}")
                (holder as? Holder1)?.onBind(it)
            }
            (currentList[position] as? Test2)?.let {
                Log.v("youngin", "큰홀더 그립니당 ${currentList[position].javaClass.simpleName} | ${holder.javaClass.simpleName}")
                (holder as? Holder2)?.onBind(it)
            }
        }
    }
}

class Holder1(private val view: View) : ViewHolder(view) {
    fun onBind(data: Test1) {
        Log.v("youngin", "holder1 타니")
        val list = itemView.findViewById<RecyclerView>(R.id.list1)

        val mAdapter = InnerAdapter1()
        list.adapter = mAdapter
        mAdapter.submitList(data.itemList)
    }

    inner class InnerAdapter1 : ListAdapter<String, InnerHolder1>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            InnerHolder1(
                LayoutInflater.from(parent.context).inflate(R.layout.item_recylcer, parent, false)
            )

        override fun onBindViewHolder(holder: InnerHolder1, position: Int) {
            holder.onBind(currentList[position])
        }
    }

    inner class InnerHolder1(private val itemView: View) : ViewHolder(itemView) {
        fun onBind(title: String) {
            itemView.findViewById<TextView>(R.id.title).text = title
        }
    }
}

class Holder2(private val view: View) : ViewHolder(view) {
    fun onBind(data: Test2) {
        Log.v("youngin", "holder2 타니")
        val list = itemView.findViewById<RecyclerView>(R.id.list2)
        val mAdapter = InnerAdapter2()

        list.adapter = mAdapter
        mAdapter.submitList(data.itemList)
    }

    inner class InnerAdapter2 : ListAdapter<String, InnerHolder2>(object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem

    }) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            InnerHolder2(
                LayoutInflater.from(parent.context).inflate(R.layout.item_recylcer2, parent, false)
            )

        override fun onBindViewHolder(holder: InnerHolder2, position: Int) {
            holder.onBind(currentList[position])
        }
    }

    inner class InnerHolder2(private val itemView: View) : ViewHolder(itemView) {
        fun onBind(title: String) {
            itemView.findViewById<TextView>(R.id.title).text = title
        }
    }
}