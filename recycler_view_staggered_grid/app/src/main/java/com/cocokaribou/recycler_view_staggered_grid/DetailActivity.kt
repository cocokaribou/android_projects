package com.cocokaribou.recycler_view_staggered_grid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cocokaribou.recycler_view_staggered_grid.databinding.ActivityDetailBinding
import com.skydoves.transformationlayout.*

class DetailActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val binding = ActivityDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        intent.getParcelableExtra<PhotoVO.Goods>("goods")?.let{ goods ->
//            var imageUrl = goods.imgUrl.substring(2, goods.imgUrl.length)
//            imageUrl = "http://$imageUrl"
//            Glide.with(binding.ivGoodsImgDetail.context)
//                .load(imageUrl)
//                .into(binding.ivGoodsImgDetail)
//            binding.tvBrandNameDetail.text = goods.brandNm
//            binding.tvGoodsNameDetail.text = goods.goodsNm
//            binding.tvGoodsPrice.text = "${goods.salePrice}원"
//        }
//    }
//
//    companion object {
//        fun startActivity(
//            context: Context,
//            transformationLayout: TransformationLayout,
//            goods: PhotoVO.Goods
//        ) {
//            val intent = Intent(context, DetailActivity::class.java)
//            intent.putExtra("goods", goods)
//            TransformationCompat.startActivity(transformationLayout, intent)
//        }
//    }
}