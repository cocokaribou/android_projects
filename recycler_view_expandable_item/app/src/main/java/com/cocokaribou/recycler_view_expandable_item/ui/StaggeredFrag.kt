package com.cocokaribou.recycler_view_expandable_item.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cocokaribou.recycler_view_expandable_item.R
import com.cocokaribou.recycler_view_expandable_item.api.MyApi
import com.cocokaribou.recycler_view_expandable_item.databinding.FragmentStaggeredBinding
import com.cocokaribou.recycler_view_expandable_item.databinding.ItemPhotoPreviewBinding
import com.cocokaribou.recycler_view_expandable_item.model.Photo
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StaggeredFrag : Fragment() {
    lateinit var binding: FragmentStaggeredBinding
    var photoList = mutableListOf<Photo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStaggeredBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        getList()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getList() {
        val myApi = MyApi.getApiService()
        val callBack: Call<ResponseBody> = myApi.getRandomPhotos()
        callBack.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("onFailure!", "통신실패")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("onResponse", "통신성공")
                val handler = Handler(Looper.getMainLooper())
                val jsonString = response.body()?.string()
                val jsonArray = JSONArray(jsonString)
                for (i in jsonArray.length() - 1 downTo 0) {
                    val photo = Gson().fromJson(jsonArray[i].toString(), Photo::class.java)
                    photoList.add(photo as Photo)
                }

                initAdapter(photoList)
            }
        })
    }

    private fun initAdapter(list: MutableList<Photo>) {

        // pinterest ui
        val staggeredLayout = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        staggeredLayout.gapStrategy= StaggeredGridLayoutManager.GAP_HANDLING_NONE

        binding.rvGoods.layoutManager = staggeredLayout
        binding.rvGoods.adapter = MyAdapter(list, requireContext())
        binding.rvGoods.addOnScrollListener(ScrollListener(binding.rvGoods))

    }
    class ScrollListener(val rvGoods: RecyclerView) :RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            Log.e("onScrollStateChanged", "called")
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            Log.e("onScrolled", "called")
        }
    }

    class MyAdapter() :
        RecyclerView.Adapter<MyAdapter.BestItemHolder>() {
        lateinit var mContext: Context
        var photoList = mutableListOf<Photo>()

        constructor(list: MutableList<Photo>?, context: Context) : this() {
            mContext = context
            if (!list.isNullOrEmpty()) {
                photoList = list
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestItemHolder {
            return BestItemHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_photo_preview, parent, false)
            )
        }


        override fun onBindViewHolder(holder: BestItemHolder, position: Int) {
            holder.bind(photoList[position], mContext, position)
        }


        class BestItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val itemBinding = ItemPhotoPreviewBinding.bind(itemView)
            fun bind(photo: Photo, context: Context, position: Int) {
                itemBinding.tvIndex.text = position.toString()

                // image
                var imageUrl = photo.photoUrl
                Glide.with(itemBinding.ivGoodsImg.context)
                    .load(imageUrl)
                    .transition(DrawableTransitionOptions().crossFade())
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
}

