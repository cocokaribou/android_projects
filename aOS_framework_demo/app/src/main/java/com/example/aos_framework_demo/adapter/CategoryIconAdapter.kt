package com.example.aos_framework_demo.adapter

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

class CategoryIconAdapter(private val ctgList: ArrayList<TestVO.Data.Category>) :
    RecyclerView.Adapter<CategoryIconAdapter.CategoryHolder>() {
    private var clickPos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_icon, parent, false)
        return CategoryHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(ctgList, position, clickPos)
    }

    override fun getItemCount(): Int {
        return ctgList.size
    }

    class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemIconBinding.bind(view)
        private val root = binding.root
        private var imgView: ImageView = binding.imgCtgIcon
        private var textView: TextView = binding.txtCtgNm

        fun bind(
            ctgList: ArrayList<TestVO.Data.Category>,
            position: Int,
            clickPos: Int
        ) {
            with(ctgList[position]) {
                val txt = ctgNm
                var txtTypeface = Typeface.DEFAULT
                var txtColor = Color.parseColor("#2f2f2f")
                var imgUrl = "http:"+dactiveImgUrl

                when (position) {
                    clickPos -> {
                        txtTypeface = Typeface.DEFAULT_BOLD
                        txtColor = Color.parseColor("red")
                        imgUrl = "http:"+activeImgUrl
                    }
                    else -> {
                    }
                }

                textView.apply {
                    text = txt
                    typeface = txtTypeface
                    setTextColor(txtColor)
                }
                imgView.loadImage(imgUrl)
            }
        }
        fun click():Int {
            var clickPos = 0
            root.setOnClickListener {
                clickPos = adapterPosition
            }
            return clickPos
        }

    }
}
