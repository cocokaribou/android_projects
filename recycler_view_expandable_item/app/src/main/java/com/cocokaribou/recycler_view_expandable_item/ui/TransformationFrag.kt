package com.cocokaribou.recycler_view_expandable_item.ui

import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cocokaribou.recycler_view_expandable_item.*
import com.cocokaribou.recycler_view_expandable_item.api.MyApi
import com.cocokaribou.recycler_view_expandable_item.databinding.FragmentTransformationBinding
import com.cocokaribou.recycler_view_expandable_item.databinding.ItemGoodsPreviewBinding
import com.cocokaribou.recycler_view_expandable_item.model.Goods
import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransformationFrag : Fragment() {
    lateinit var binding: FragmentTransformationBinding
    lateinit var itemDeco: RecyclerView.ItemDecoration
    var mAdapter = MyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransformationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initAdapter()
        getList()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun getList() {
        val myApi = MyApi.getElandApiService()
        // 잡화 슈즈
        val callBack: Call<ResponseBody> = myApi.getBestProducts("1607006212")
        callBack.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("onFailure!", "통신실패")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.e("onResponse", "통신성공")
                val jsonString = response.body()?.string()
                val jsonObj = JSONObject(jsonString)
                val goodsArrayString =
                    jsonObj.getJSONObject("data").getJSONObject("goods_info").toString()
                val bestVo = Gson().fromJson(goodsArrayString, Goods::class.java)
                mAdapter.submitList(bestVo.goodsList)
            }

        })
    }

    private fun initAdapter() {
        binding.rvGoods.adapter = mAdapter
        binding.rvGoods.layoutManager = GridLayoutManager(requireContext(), 2)
        Log.e("layout param", "${binding.rvGoods.layoutParams}")
        if (binding.rvGoods.itemDecorationCount > 0) {
            binding.rvGoods.removeItemDecoration(itemDeco)
        } else {
            itemDeco = GridSpacingItemDecoration(2, 0, false)
            binding.rvGoods.addItemDecoration(itemDeco)
        }

        binding.btnToView.setOnClickListener {
            binding.btnTransformationLayout.startTransform()
        }
        binding.cardviewPopup.setOnClickListener {
            binding.btnTransformationLayout.finishTransform()
        }

        // veil recycler
//        binding.veilRecyclerView.setAdapter(mAdapter)
//        binding.veilRecyclerView.setLayoutManager(GridLayoutManager(this, 2))
//        binding.veilRecyclerView.addVeiledItems(6)
//        binding.veilRecyclerView.veil()

    }

    class GridSpacingItemDecoration(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }
    }

    class MyAdapter :
        ListAdapter<Goods.Goods, MyAdapter.BestItemHolder>(diffUtil) {
        lateinit var goodsArray: MutableList<Goods.Goods>
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestItemHolder {
            return BestItemHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_goods_preview, parent, false)
            )
        }

        override fun onBindViewHolder(holder: BestItemHolder, position: Int) {
            holder.bind(goodsArray[position])
        }


        class BestItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val itemBinding = ItemGoodsPreviewBinding.bind(itemView)
            private var previousTime = SystemClock.elapsedRealtime()
            fun bind(goods: Goods.Goods) {
                itemBinding.run {
                    tvBrandName.text = goods.brandNm
                    tvGoodsName.text = goods.goodsNm
                    if (goods.marketPrice == 0) {
                        tvOriginPrice.visibility = View.GONE
                    } else {
                        tvOriginPrice.visibility = View.VISIBLE
                        tvOriginPrice.text = "${goods.marketPrice}원"
                        tvOriginPrice.paintFlags = tvSalesRate.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    tvSalesPrice.text = "${goods.salePrice}원"
                    if (goods.saleRate == 0) {
                        tvSalesRate.visibility = View.GONE
                    } else {
                        tvSalesRate.visibility = View.VISIBLE
                        tvSalesRate.text = "${goods.saleRate}%"
                    }

                    // image
                    var imageUrl = goods.imgUrl.substring(2, goods.imgUrl.length)
                    imageUrl = "http://$imageUrl"
                    Glide.with(ivGoodsImg.context)
                        .load(imageUrl)
                        .into(ivGoodsImg)

                    root.setOnClickListener {
                        val now = SystemClock.elapsedRealtime()
                        if (now - previousTime >= itemTransformationLayout.duration) {
                            DetailActivity.startActivity(root.context, itemTransformationLayout, goods)
                            previousTime = now
                        }
                    }
                }
            }
        }

        override fun submitList(list: MutableList<Goods.Goods>?) {
            super.submitList(list)
            if (!list.isNullOrEmpty()) {
                goodsArray = list
            }

        }

        companion object {
            val diffUtil = object : DiffUtil.ItemCallback<Goods.Goods>() {
                override fun areItemsTheSame(
                    oldItem: Goods.Goods,
                    newItem: Goods.Goods
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItems: Goods.Goods,
                    newItems: Goods.Goods
                ): Boolean {
                    return oldItems.goodsNo == newItems.goodsNo
                }
            }
        }
    }
}