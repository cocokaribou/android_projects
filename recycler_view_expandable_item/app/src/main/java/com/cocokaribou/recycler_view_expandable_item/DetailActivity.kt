package com.cocokaribou.recycler_view_expandable_item

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cocokaribou.recycler_view_expandable_item.databinding.ActivityDetailBinding
import com.skydoves.transformationlayout.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationEndContainer(intent.getParcelableExtra("com.skydoves.transformationlayout"))
        super.onCreate(savedInstanceState)

        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<BestVO.Goods>("goods")?.let{ goods ->
            var imageUrl = goods.imgUrl.substring(2, goods.imgUrl.length)
            imageUrl = "http://$imageUrl"
            Glide.with(binding.ivGoodsImgDetail.context)
                .load(imageUrl)
                .into(binding.ivGoodsImgDetail)
            binding.tvBrandNameDetail.text = goods.brandNm
            binding.tvGoodsNameDetail.text = goods.goodsNm
            binding.tvGoodsPrice.text = "${goods.salePrice}원"
        }
    }

    companion object {
        fun startActivity(
            context: Context,
            transformationLayout: TransformationLayout,
            goods: BestVO.Goods
        ) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("goods", goods)
            TransformationCompat.startActivity(transformationLayout, intent)
        }
    }
}