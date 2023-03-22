package com.example.elandmall_kotlin.ui.goods.viewholders.tab2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.elandmall_kotlin.databinding.ViewGoodsReviewMoreItemBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsReviewPhotoBinding
import com.example.elandmall_kotlin.databinding.ViewGoodsReviewPhotoItemBinding
import com.example.elandmall_kotlin.model.Goods
import com.example.elandmall_kotlin.ui.goods.GoodsBaseViewHolder
import com.example.elandmall_kotlin.util.censoredString

class GoodsReviewPhotoHolder(val binding: ViewGoodsReviewPhotoBinding) : GoodsBaseViewHolder(binding.root) {
    private val mAdapter by lazy { PhotoReviewAdapter() }
    override fun onBind(item: Any?) {
        (item as? Goods.ReviewImageInfo)?.let { data ->
            initUI(data)
        }
    }

    private fun initUI(data: Goods.ReviewImageInfo) = with(binding) {
        header.setHeaderTitle("포토 리뷰 (${data.reviewCount})")

        list.adapter = mAdapter
        if ((data.reviewList?.size ?: 0) >= 4) {
            val list = data.reviewList?.subList(0, 3)?.toMutableList()
            list?.add(Goods.Review())

            mAdapter.submitList(list)
        } else
            mAdapter.submitList(data.reviewList)
    }

    class PhotoReviewAdapter : ListAdapter<Goods.Review, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<Goods.Review>() {
        override fun areItemsTheSame(oldItem: Goods.Review, newItem: Goods.Review) = oldItem == newItem
        override fun areContentsTheSame(oldItem: Goods.Review, newItem: Goods.Review) =
            oldItem.imageUrl == newItem.imageUrl

    }) {
        private val PHOTO = 0
        private val MORE = 1
        override fun getItemViewType(position: Int): Int {
            return if (currentList[position].imageUrl.isNullOrEmpty()) MORE else PHOTO
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return when (viewType) {
                PHOTO -> PhotoReviewHolder(
                    ViewGoodsReviewPhotoItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                else -> PhotoMoreHolder(
                    ViewGoodsReviewMoreItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as? PhotoReviewHolder)?.onBind(currentList[position])
            (holder as? PhotoMoreHolder)?.onBind()
        }
    }

    class PhotoReviewHolder(private val binding: ViewGoodsReviewPhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Goods.Review) = with(binding) {
            Glide.with(root.context)
                .load("http:" + data.imageUrl)
                .into(reviewImg)

            userId.text = data.userID?.censoredString(1)

            bodySize.text = "${data.height ?:0} ${data.weight}"

            goodsName.text = data.purchaseGoodsInfo

            reviewComment.text = data.reviewComment
        }
    }

    class PhotoMoreHolder(private val binding: ViewGoodsReviewMoreItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind() {}
    }
}