package com.cocokaribou.recycler_view_staggered_grid

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cocokaribou.recycler_view_staggered_grid.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e(javaClass.simpleName, "onCreate()")
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getStringExtra("photo")?.let{ url ->
            Glide.with(binding.ivPhotoImgDetail.context)
                .load(url)
                .into(binding.ivPhotoImgDetail)
        }
    }

}