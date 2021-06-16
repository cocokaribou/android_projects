package com.example.openapi_test

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openapi_test.Data.DataVO
import com.example.openapi_test.databinding.ItemBakeryBinding

class mAdapter(private val list: ArrayList<DataVO.VoObject.Bakery>) :
    RecyclerView.Adapter<mAdapter.mViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mAdapter.mViewHolder {
        return mViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bakery, parent, false)
        )
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.bind(list)
        //do something with list
    }

    override fun getItemCount(): Int {
        return 10
    }

    class mViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemBakeryBinding.bind(view)

        fun bind(list: ArrayList<DataVO.VoObject.Bakery>) {
            binding.txtStoreNm.text = "되긴 되냐고"
            binding.txtAddress.text = "왜 바인딩이 안되냐고"
            Log.e("adapter", "listSize: ${list.size}")

            for (i in 0..10) {
                binding.txtStoreNm.text = list[i].storeNm
                binding.txtAddress.text = list[i].storeAdr
            }
        }
    }
}
