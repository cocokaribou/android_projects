package com.cocokaribou.recycler_view_staggered_grid

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.cocokaribou.recycler_view_staggered_grid.databinding.ItemGoodsPreviewBinding
import java.util.*

class MyAdapter() :
    RecyclerView.Adapter<MyAdapter.BestItemHolder>() {
    lateinit var mContext: Context
    var photoList = mutableListOf<PhotoVO>()

    constructor(list: MutableList<PhotoVO>?, context: Context) : this() {
        mContext = context
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
        holder.bind(photoList[position], mContext)
    }


    class BestItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemBinding = ItemGoodsPreviewBinding.bind(itemView)
        fun bind(photo: PhotoVO, context: Context) {

            // image
            var imageUrl = photo.photoUrl
            Glide.with(itemBinding.ivGoodsImg.context)
                .load(imageUrl)
                .transition(DrawableTransitionOptions().crossFade())
                .into(itemBinding.ivGoodsImg)

            itemBinding.ivGoodsImg.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("photo", photo.photoUrl)
                (context as MainActivity).startActivity(intent)
            }
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