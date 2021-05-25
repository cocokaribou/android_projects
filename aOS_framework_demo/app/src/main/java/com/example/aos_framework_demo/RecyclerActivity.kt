package com.example.aos_framework_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aos_framework_demo.databinding.ActivityRecylcerBinding
import com.pionnet.overpass.customView.GridSpacingItemDecoration
import com.pionnet.overpass.customView.HorizontalSpacingItemDecoration
import com.pionnet.overpass.extension.collapse
import com.pionnet.overpass.extension.expand

class RecyclerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecylcerBinding
    private lateinit var mAdapter: CustomAdapter
    private lateinit var linearManager: LinearLayoutManager
    private lateinit var gridManager: GridLayoutManager
    private lateinit var horizontalDeco: HorizontalSpacingItemDecoration
    private lateinit var gridDeco: GridSpacingItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecylcerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = CustomAdapter()

        /**
         * HorizontalSpacingItemDecoration
         */
        val recyclerHorizontal = binding.recyclerHorizontal
        linearManager = LinearLayoutManager(this)
        linearManager.orientation = LinearLayoutManager.HORIZONTAL

        recyclerHorizontal.layoutManager = linearManager
        recyclerHorizontal.adapter = mAdapter

        binding.btnAddDeco.setOnClickListener {
            val editSpacing = binding.editSpacing
            val editEdge = binding.editEdge
            val switchEdge = binding.switchEdge

            /*
            입력값 숫자여부 check
             */
            var spacing = 0
            var edge = 0

            try {
                spacing = Integer.parseInt(editSpacing.text.toString())
                edge = Integer.parseInt(editEdge.text.toString())
            } catch (e: Exception) {
            }
            val checker = switchEdge.isChecked

            /*
            decoration 중복적용 방지
             */
            if (recyclerHorizontal.itemDecorationCount > 0) {
                recyclerHorizontal.removeItemDecoration(horizontalDeco)
            }

            horizontalDeco = HorizontalSpacingItemDecoration(spacing, edge, checker)
            recyclerHorizontal.addItemDecoration(horizontalDeco)
            mAdapter.notifyDataSetChanged()
        }


        /**
         * GridSpacingItemDecoration
         */
        gridManager = GridLayoutManager(this, 4)
        val recyclerGrid = binding.recyclerGrid
        recyclerGrid.layoutManager = gridManager
        recyclerGrid.adapter = mAdapter

        binding.btnAddDeco2.setOnClickListener {
            val editGridSpacing = binding.editGridSpacing
            val switchGridEdge = binding.switchGridEdge

            var gridSpacing = 0;

            try {
                gridSpacing = Integer.parseInt(editGridSpacing.text.toString())
            } catch (e: Exception) {
            }

            val checker = switchGridEdge.isChecked

            if (recyclerGrid.itemDecorationCount > 0) {
                recyclerGrid.removeItemDecoration(gridDeco)
            }

            gridDeco = GridSpacingItemDecoration(4, gridSpacing, checker)
            recyclerGrid.addItemDecoration(gridDeco)
            mAdapter.notifyDataSetChanged()
        }


        /**
         * AnimationExtension
         */
        val expand = binding.txtExpand
        val expanded = binding.txtExpanded

        var switch = false
        expand.setOnClickListener {
            when (switch) {
                true -> {
                    expand.text = "collapse"
                    expand(expanded)
                    switch = !switch
                }
                false -> {
                    expand.text = "expand"
                    collapse(expanded)
                    switch = !switch
                }
            }

        }
    }
}
