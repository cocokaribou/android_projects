package com.cocokaribou.recycler_view_expandable_item

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cocokaribou.recycler_view_expandable_item.databinding.ActivityMainBinding
import com.cocokaribou.recycler_view_expandable_item.ui.*
import com.cocokaribou.recycler_view_expandable_item.ui.dragview.DragViewFrag
import com.skydoves.transformationlayout.onTransformationStartContainer

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    fun initView() {
        binding.viewpager.adapter = CustomFragmentStateAdapter(this)
    }


    class CustomFragmentStateAdapter(val fragActivity: FragmentActivity) :
        FragmentStateAdapter(fragActivity) {

        override fun getItemCount() = 5

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> {
                    IntroFrag()
                }
                1 -> {
                    ScrollFrag()
                }
                2->{
                    DragViewFrag()
                }
                3 -> {
                    TransformationFrag()
                }
                else -> {
                    StaggeredFrag()
                }
            }
        }

    }
}
