package com.example.aos_framework_demo.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.ElandActivity
import com.example.aos_framework_demo.R
import com.example.aos_framework_demo.WebviewActivity
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.databinding.ItemStoreBinding
import com.example.aos_framework_demo.databinding.ItemStoreEndBinding
import com.pionnet.overpass.extension.*
import kotlin.collections.ArrayList

class RecommendAdapter(
    private val recList: ArrayList<TestVO.Data.Recommend>,
    mainContext: Context,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_LIST = 0
    private val TYPE_END = 1

    val context = mainContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_END ->
                EndHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_store_end, parent, false)
                )
            TYPE_LIST ->
                StoreHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false),
                    parent
                )
            else ->
                EndHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
                )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_END -> {
                holder as EndHolder
                holder.bind()
            }
            TYPE_LIST -> {
                holder as StoreHolder
                holder.bind(recList, position)
            }
        }
    }

    //header, footer, item 구별
    override fun getItemViewType(position: Int): Int {
        if (position >= recList.size) {
            return TYPE_END
        }
        return TYPE_LIST
    }

    override fun getItemCount(): Int { //recycler에서 한 칸 더 내줌
        return recList.size + 1
    }


    class StoreHolder(view: View, parent: ViewGroup) : RecyclerView.ViewHolder(view) {
        private val binding = ItemStoreBinding.bind(view)
        private val root = binding.root
        private val imgView: ImageView = binding.imgRecommend
        private val txtView: TextView = binding.txtRecommend
        private val parent: ViewGroup = parent

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(list: ArrayList<TestVO.Data.Recommend>, position: Int) {

            /*
            바인드
             */
            with(list[position]) {
                txtView.text = storeName

                val rootUrl = "http:"
                if (imgUrl.isNotEmpty()) {
                    imgView.loadImageCircle(rootUrl + imgUrl, 500, 500)
                }
            }

            /*
            클릭리스너
             */
            root.setOnClickListener {
                val clickPos = adapterPosition

                val rootUrl = "https://m.elandmall.com"
                val linkUrl = list[clickPos].linkUrl
                if (clickPos != RecyclerView.NO_POSITION) {
                    val intent: Intent = Intent(parent.context, WebviewActivity::class.java)
                    intent.putExtra("url", rootUrl + linkUrl)
                    parent.context.startActivity(intent)
                }
            }
        }
    }

    class EndHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemStoreEndBinding.bind(view)
        private val root = binding.root

        fun bind() {
            root.setOnClickListener {
                val msg = "더보기입니다"
                val toast = Toast.makeText(itemView.context, msg, Toast.LENGTH_LONG)
                toast.show()
            }
        }

    }

}