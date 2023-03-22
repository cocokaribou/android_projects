package com.example.elandmall_kotlin.ui.goods.viewholders.tab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewGoodsQnaListBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsQnaListItemBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsQnaListMoreItemBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.censoredString

class GoodsQnaListHolder(private val binding: ViewGoodsQnaListBinding) : GoodsBaseViewHolder(binding.root) {
    private val mAdapter by lazy { QnaAdapter() }
    override fun onBind(item: Any?) {
        (item as? List<*>)?.let {
            val dataList = it.filterIsInstance<Goods.Question>()
            initUI(dataList)
        }
    }

    fun initUI(dataList: List<Goods.Question>) = with(binding) {
        list.adapter = mAdapter
        if (dataList.size >= 4) {
            val list = dataList.subList(0, 4).toMutableList()
            list.add(Goods.Question())
            mAdapter.submitList(list)
        } else
            mAdapter.submitList(dataList)
    }

    class QnaAdapter : ListAdapter<Goods.Question, RecyclerView.ViewHolder>(object : DiffUtil.ItemCallback<Goods.Question>() {
        override fun areItemsTheSame(oldItem: Goods.Question, newItem: Goods.Question): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Goods.Question, newItem: Goods.Question): Boolean = oldItem.content == newItem.content
    }) {
        private val QNA = 0
        private val MORE = 1
        override fun getItemViewType(position: Int): Int = if (currentList[position].userID.isNullOrEmpty()) MORE else QNA
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                QNA -> {
                    QnaHolder(
                        ViewGoodsQnaListItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }
                else -> {
                    MoreHolder(
                        ViewGoodsQnaListMoreItemBinding.inflate(
                            LayoutInflater.from(parent.context),
                            parent,
                            false
                        )
                    )
                }
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as? QnaHolder)?.onBind(currentList[position])
        }
    }

    class QnaHolder(val binding: ViewGoodsQnaListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Goods.Question) = with(binding) {
            if (data.userID.equals("판매자")) {
                userId.text = data.userID
                reply.visibility = View.VISIBLE
            } else {
                userId.text = data.userID?.censoredString(visibleCharCount = 2)
                reply.visibility = View.GONE
            }
            date.text = data.date
            content.text = data.content
        }
    }

    class MoreHolder(val binding: ViewGoodsQnaListMoreItemBinding) : RecyclerView.ViewHolder(binding.root) {}
}