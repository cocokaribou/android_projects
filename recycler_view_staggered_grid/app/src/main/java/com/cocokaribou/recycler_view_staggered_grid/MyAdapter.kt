package com.cocokaribou.recycler_view_staggered_grid

import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cocokaribou.recycler_view_staggered_grid.databinding.ItemGoodsPreviewBinding
import java.util.*

class MyAdapter() :
    RecyclerView.Adapter<MyAdapter.BestItemHolder>() {
    var photoList = mutableListOf<PhotoVO>()

    constructor(list: MutableList<PhotoVO>?) : this() {
        if (!list.isNullOrEmpty()) {
            photoList = list
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestItemHolder {
        return BestItemHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_goods_preview, parent, false)
        )
    }

    //
    override fun onBindViewHolder(holder: BestItemHolder, position: Int) {
        holder.bind(photoList[position])
    }


    class BestItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemBinding = ItemGoodsPreviewBinding.bind(itemView)
        fun bind(photo: PhotoVO) {

            // image
            var imageUrl = photo.photoUrl
            Glide.with(itemBinding.ivGoodsImg.context)
                .load(imageUrl)
                .into(itemBinding.ivGoodsImg)
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}