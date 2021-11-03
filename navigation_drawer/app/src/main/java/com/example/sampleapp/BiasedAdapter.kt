package com.example.sampleapp

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BiasedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiasedHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }



    class BiasedHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    class RegularHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }


}