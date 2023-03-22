package com.example.elandmall_kotlin.ui.goods.viewholders.tab2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.databinding.ViewGoodsReviewMoreItemBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsReviewTextBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsReviewTextItemBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.Logger
import com.example.elandmall_kotlin.util.censoredString

class GoodsReviewTextHolder(val binding: ViewGoodsReviewTextBinding) : GoodsBaseViewHolder(binding.root) {
    private val mAdapter by lazy { TextReviewAdapter() }

    override fun onBind(item: Any?) {
        (item as? Goods.ReviewTextInfo)?.let { data ->
            initUI(data)
        }
    }

    private fun initUI(data: Goods.ReviewTextInfo) = with(binding) {
        header.setHeaderTitle("텍스트 리뷰 (${data.reviewCount})")

        list.adapter = mAdapter
        if ((data.reviewList?.size ?: 0) >= 4) {
            val list = data.reviewList?.subList(0, 3)?.toMutableList()
            list?.add(Goods.Review())

            mAdapter.submitList(list)
        } else
            mAdapter.submitList(data.reviewList)
    }

    class TextReviewAdapter : ListAdapter<Goods.Review, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<Goods.Review>() {
        override fun areItemsTheSame(oldItem: Goods.Review, newItem: Goods.Review) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Goods.Review, newItem: Goods.Review) =
            oldItem.imageUrl == newItem.imageUrl

    }) {
        private val TEXT = 0
        private val MORE = 1
        override fun getItemViewType(position: Int): Int {
            return if (currentList[position].userID.isNullOrEmpty()) MORE else TEXT
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                TEXT -> TextReviewHolder(
                    ViewGoodsReviewTextItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                else -> TextMoreHolder(
                    ViewGoodsReviewMoreItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as? TextReviewHolder)?.onBind(currentList[position])
            (holder as? TextMoreHolder)?.onBind()
        }
    }

    class TextReviewHolder(private val binding: ViewGoodsReviewTextItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Goods.Review) = with(binding) {
            userId.text = data.userID?.censoredString(1)

            bodySize.text = "${data.height ?: 0} ${data.weight}"

            goodsName.text = data.purchaseGoodsInfo

            reviewComment.text = data.reviewComment
        }
    }

    class TextMoreHolder(private val binding: ViewGoodsReviewMoreItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind() {}
    }

}