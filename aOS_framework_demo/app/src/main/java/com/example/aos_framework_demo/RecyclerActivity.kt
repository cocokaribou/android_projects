package com.example.aos_framework_demo

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aos_framework_demo.adapter.CustomAdapter
import com.example.aos_framework_demo.databinding.ActivityRecylcerBinding
import com.pionnet.overpass.customView.GridDividerItemDecoration
import com.pionnet.overpass.customView.GridSpacingItemDecoration
import com.pionnet.overpass.customView.HorizontalSpacingItemDecoration

class RecyclerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecylcerBinding
    private lateinit var mAdapter: CustomAdapter
    private lateinit var linearManager: LinearLayoutManager
    private lateinit var gridManager: GridLayoutManager
    private lateinit var horizontalDeco: HorizontalSpacingItemDecoration
    private lateinit var gridDeco: GridSpacingItemDecoration
    private lateinit var gridDeco2: GridDividerItemDecoration

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

        gridDeco = GridSpacingItemDecoration(4, 0, false)
        binding.btnAddDeco2.setOnClickListener {
            if (gridDeco2 != null) {
                recyclerGrid.removeItemDecoration(gridDeco2)
            }
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
         * GridDividerItemDecoration
         */
        gridDeco2 = GridDividerItemDecoration(0, Color.parseColor("#b2b2b2"))
        binding.btnAddDeco3.setOnClickListener {
            if (gridDeco != null) {
                recyclerGrid.removeItemDecoration(gridDeco)
            }
            val editGridSpacing = binding.editGridSpacing2
            var gridSpacing = 0;

            try {
                gridSpacing = Integer.parseInt(editGridSpacing.text.toString())
            } catch (e: Exception) {
            }

            if (recyclerGrid.itemDecorationCount > 0) {
                recyclerGrid.removeItemDecoration(gridDeco2)
            }

            gridDeco2 = GridDividerItemDecoration(gridSpacing, Color.parseColor("#b2b2b2"))
            recyclerGrid.addItemDecoration(gridDeco2)
            mAdapter.notifyDataSetChanged()
        }

//        Paint class : 컬러와 스타일 정보

//
//        /**
//         * AnimationExtension
//         */
//        val expand = binding.txtExpand
//        val expanded = binding.txtExpanded
//
//        var switch = false
//        expand.setOnClickListener {
//            when (switch) {
//                true -> {
//                    expand.text = "collapse"
//                    expand(expanded)
//                    switch = !switch
//                }
//                false -> {
//                    expand.text = "expand"
//                    collapse(expanded)
//                    switch = !switch
//                }
//            }
//
//        }
    }
}
