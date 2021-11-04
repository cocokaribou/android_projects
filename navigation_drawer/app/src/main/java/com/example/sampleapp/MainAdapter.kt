package com.example.sampleapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleapp.databinding.ItemBiasedBinding
import com.example.sampleapp.databinding.ItemNormalBinding
import java.util.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.NormalHolder>() {
    var dataList = mutableListOf<String>()
    private var mItemViewType = 0

    companion object {
        val VIEWTYPE_BIGHOLDER = 0
        val VIEWTYPE_GRID2HOLDER = VIEWTYPE_BIGHOLDER + 1
        val VIEWTYPE_GRID4HOLDER = VIEWTYPE_GRID2HOLDER + 1
    }

    fun setItemViewType(viewType: Int) {
        mItemViewType = viewType
        notifyDataSetChanged()
//        notifyItemMoved() //인자로 뭘 넣어줘야,,
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NormalHolder {
        Logger.e("oncreate")
        // BIGHOLDER일 때는 viewType(index)마다 다른 홀더를 붙여준다
//        if (mItemViewType == VIEWTYPE_BIGHOLDER) {
        // 3개마다 하나씩
//            if (viewType != 0 && viewType % 3 == 1) {
//                val itemBiasedBinding = ItemBiasedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                return BiasedHolder(itemBiasedBinding)
//            } else {
//                val itemTestBinding = ItemNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                return NormalHolder(itemTestBinding)
//            }
        //BIGHOLDER 아닐 때는 그냥 홀더 하나로 퉁친다
//        } else {
        val itemTestBinding = ItemNormalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NormalHolder(itemTestBinding)
//        }
    }

    override fun onBindViewHolder(holder: NormalHolder, position: Int) {
        holder.bind(dataList, position)
//            is BiasedHolder -> {
//                holder.bind(dataList, position)
//            }
//    }
    }



    override fun getItemViewType(position: Int): Int {
        return mItemViewType
    }

    fun submitList(mList: MutableList<String>) {
        dataList = mList
    }
    override fun getItemCount(): Int {
        return dataList.size //여기가 0을 뱉고 있었군
    }

    class NormalHolder(private var itemBinding: ItemNormalBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dataList: MutableList<String>, position: Int) {
            with(itemBinding) {
                Glide.with(root.context)
                    .load(dataList[position])
                    .into(img)
            }
        }
    }

    class BiasedHolder(private val itemBinding: ItemBiasedBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(dataList: MutableList<String>, position: Int) {
            with(itemBinding) {
                Glide.with(root.context)
                    .load(dataList[position])
                    .into(img)
            }

        }
    }

}