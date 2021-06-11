package com.example.openapi_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi_test.databinding.ItemBakeryBinding

class mAdapter(private val list: ArrayList<String>) : RecyclerView.Adapter<mAdapter.mViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mAdapter.mViewHolder {
        return mViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bakery, parent, false)
        )
    }

    override fun onBindViewHolder(holder: mAdapter.mViewHolder, position: Int) {
        val binding = ItemBakeryBinding.bind(holder.itemView)
        binding.txtStoreNm.text = "mic test"
        //do something with list
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class mViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}
