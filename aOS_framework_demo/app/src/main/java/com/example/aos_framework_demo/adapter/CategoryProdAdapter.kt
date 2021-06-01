package com.example.aos_framework_demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.R
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.databinding.ItemCategoryProductBinding
import com.pionnet.overpass.extension.loadImageWithOriginal
import com.pionnet.overpass.extension.loadImageWithScale
import com.pionnet.overpass.extension.setPriceStroke
import com.pionnet.overpass.extension.setUnderLine

class CategoryProdAdapter(private val prodList: ArrayList<TestVO.Data.Category.Goods>) :
    RecyclerView.Adapter<CategoryProdAdapter.CategoryHolder>() {
    class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCategoryProductBinding.bind(view)
        val imgProductImg: ImageView = binding.imgProductImg
        val imgStorePick = binding.imgStorePick
        val txtBrandNm: TextView = binding.txtBrandNm
        val txtProductNm: TextView = binding.txtProductNm
        val txtPrice: TextView = binding.txtPrice
        val txtSaleRate: TextView = binding.txtSaleRate
        val txtMarketPrice: TextView = binding.txtMarketPrice

        val reviewRow = binding.layoutReviewRow
        val imgStar1: ImageView = binding.imgStar1
        val imgStar2: ImageView = binding.imgStar2
        val imgStar3: ImageView = binding.imgStar3
        val imgStar4: ImageView = binding.imgStar4
        val imgStar5: ImageView = binding.imgStar5
        val txtReviewCount: TextView = binding.txtReviewCount

        val txtFreeShipping: TextView = binding.txtFreeShipping


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category_product, parent, false)
        return CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        with(prodList[position]){
            var imgUrl = "http:"+ imageUrl
            holder.imgProductImg.loadImageWithScale(imgUrl, 450, 450)

            holder.txtBrandNm.text = brandNm
            holder.txtProductNm.text = goodsNm

            if(saleRate == 0){
                holder.txtSaleRate.visibility = View.GONE
            }
            holder.txtSaleRate.text = saleRate.toString()+"%"
            holder.txtPrice.text = custSalePrice.toString()+"원"
            //https://860709.tistory.com/59
            //TODO '원'만 글자크기 줄이기
            holder.txtMarketPrice.text = marketPrice.toString()+"원"
            holder.txtMarketPrice.setPriceStroke(10, true)

            if(goodsCommentCount == 0){
                holder.reviewRow.visibility = View.GONE
            }
            holder.txtReviewCount.text = "("+goodsCommentCount.toString()+")"

            if(!iconView.isEmpty()){
                holder.txtFreeShipping.visibility = View.GONE
            }
            if(fieldRecevPossYn == "N"){
                holder.imgStorePick.visibility = View.GONE
            }

        }
        with(holder) {
        }

    }

    override fun getItemCount(): Int {
        return prodList.size
    }
}