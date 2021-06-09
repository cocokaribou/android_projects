package com.example.aos_framework_demo.adapter

import android.content.ClipData
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aos_framework_demo.R
import com.example.aos_framework_demo.data.TestVO
import com.example.aos_framework_demo.databinding.ItemIconBinding
import com.pionnet.overpass.extension.*

class CategoryIconAdapter(
    private val ctgList: ArrayList<TestVO.Data.Category>,
    private val onItemClickListener: (Any, Int, Int) -> Unit
) :
    RecyclerView.Adapter<CategoryIconAdapter.CategoryHolder>() {

    private var position = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        return CategoryHolder(ItemIconBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClickListener)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(ctgList[position], position)
    }

    override fun getItemCount(): Int {
        return ctgList.size
    }

    fun selected(position: Int){
        this.position = position
        ctgList.forEach {
            it.checker = false
        }
        ctgList[position].checker = true
        
        notifyDataSetChanged()
    }

//    fun getHolderWidth(): Int{
//        return CategoryHolder().getHolderWidth()
//    }

    class CategoryHolder(
            private val itemIconBinding: ItemIconBinding,
            private val mListener: (Any, Int, Int) -> Unit
    ) : RecyclerView.ViewHolder(itemIconBinding.root) {
        fun bind(
            item: TestVO.Data.Category,
            position: Int,
        ) {
            itemIconBinding.txtCtgNm.text = item.ctgNm

            itemIconBinding.root.setOnClickListener {
                mListener(item, position, itemIconBinding.root.width)
            }

            if (item.checker) {
                itemIconBinding.txtCtgNm.setTextColor(Color.parseColor("#c90d1b"))
                itemIconBinding.txtCtgNm.setTypeface(null, Typeface.BOLD)
                itemIconBinding.imgCtgIcon.loadImage("http:" + item.activeImgUrl)
            } else {
                itemIconBinding.txtCtgNm.setTextColor(Color.parseColor("#2f2f2f"))
                itemIconBinding.txtCtgNm.setTypeface(null, Typeface.NORMAL)
                itemIconBinding.imgCtgIcon.loadImage("http:" + item.dactiveImgUrl)
            }
        }

    }

}
