package com.example.aos_framework_demo

import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aos_framework_demo.adapter.CustomAdapter
import com.example.aos_framework_demo.databinding.ActivityRecylcerBinding
import com.pionnet.overpass.customView.GridDividerItemDecoration
import com.pionnet.overpass.customView.GridSpacingItemDecoration
import com.pionnet.overpass.customView.HorizontalSpacingItemDecoration
import com.pionnet.overpass.customView.VerticalSpacingItemDecoration

class RecyclerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecylcerBinding
    private lateinit var mAdapter: CustomAdapter
    private lateinit var horizontalManager: LinearLayoutManager
    private lateinit var verticalManager: LinearLayoutManager
    private lateinit var gridManager: GridLayoutManager
    private lateinit var horizontalDeco: HorizontalSpacingItemDecoration
    private lateinit var verticalDeco: VerticalSpacingItemDecoration
    private lateinit var gridDeco: GridSpacingItemDecoration
    private lateinit var gridDeco2: GridDividerItemDecoration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecylcerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAdapter = CustomAdapter()

        /**
         * Horizontal & Vertical SpacingItemDecoration
         */

        /*
        Horizontal
         */
        val recyclerHorizontal = binding.recyclerHorizontal
        horizontalManager = LinearLayoutManager(this@RecyclerActivity)
        horizontalManager.orientation = LinearLayoutManager.HORIZONTAL

        recyclerHorizontal.layoutManager = horizontalManager
        recyclerHorizontal.adapter = mAdapter

        /*
        Vertical
         */
        val recyclerVertical = binding.recyclerVertical
        verticalManager = LinearLayoutManager(this@RecyclerActivity)
        verticalManager.orientation = LinearLayoutManager.VERTICAL

        recyclerVertical.layoutManager = verticalManager
        recyclerVertical.adapter = mAdapter

        binding.btnAddDeco.setOnClickListener {
            val editSpacing = binding.editSpacing
            val editEdge = binding.editEdge
            val switchEdge = binding.switchEdge

            /*
            입력값 숫자여부 check
            .toIntOrNull()
             */
            val spacing = editSpacing.text.toString().toIntOrNull() ?: 0
            val edge = editEdge.text.toString().toIntOrNull() ?: 0
            val checker = switchEdge.isChecked

            /*
            decoration 중복적용 방지
             */
            if (recyclerHorizontal.itemDecorationCount > 0 && recyclerVertical.itemDecorationCount > 0) {
                recyclerHorizontal.removeItemDecoration(horizontalDeco)
                recyclerVertical.removeItemDecoration(verticalDeco)
            }

            horizontalDeco = HorizontalSpacingItemDecoration(spacing, edge, checker)
            verticalDeco = VerticalSpacingItemDecoration(spacing, edge, checker)
            recyclerHorizontal.addItemDecoration(horizontalDeco)
            recyclerVertical.addItemDecoration(verticalDeco)
            mAdapter.notifyDataSetChanged()
        }


        /**
         * GridSpacingItemDecoration
         */
        gridManager = GridLayoutManager(this, SPAN_COUNT)
        val recyclerGrid = binding.recyclerGrid
        recyclerGrid.layoutManager = gridManager
        recyclerGrid.adapter = mAdapter

        val editGridSpacing = binding.editGridSpacing
        val switchGridEdge = binding.switchGridEdge

        gridDeco = GridSpacingItemDecoration(SPAN_COUNT, 0, false)
        editGridSpacing.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                recyclerGrid.removeItemDecoration(gridDeco2)
                val gridSpacing = editGridSpacing.text.toString().toIntOrNull() ?: 0
                val checker = switchGridEdge.isChecked

                if (recyclerGrid.itemDecorationCount > 0) {
                    recyclerGrid.removeItemDecoration(gridDeco)
                }

                gridDeco = GridSpacingItemDecoration(SPAN_COUNT, gridSpacing, checker)
                recyclerGrid.addItemDecoration(gridDeco)
                mAdapter.notifyDataSetChanged()
            }
            false
        }

        switchGridEdge.setOnCheckedChangeListener { buttonView, isChecked ->
            recyclerGrid.removeItemDecoration(gridDeco2)
            val gridSpacing = editGridSpacing.text.toString().toIntOrNull() ?: 0
            if (recyclerGrid.itemDecorationCount > 0) {
                recyclerGrid.removeItemDecoration(gridDeco)
            }
            if (isChecked) {
                gridDeco = GridSpacingItemDecoration(SPAN_COUNT, gridSpacing, true)
            } else {
                gridDeco = GridSpacingItemDecoration(SPAN_COUNT, gridSpacing, false)
            }
            recyclerGrid.addItemDecoration(gridDeco)
            mAdapter.notifyDataSetChanged()
        }

        /**
         * GridDividerItemDecoration
         */
        gridDeco2 = GridDividerItemDecoration(0, Color.parseColor("#b2b2b2"))

        val editLineWidth = binding.editGridSpacing2
        editLineWidth.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                recyclerGrid.removeItemDecoration(gridDeco)
                val lineWidth = editLineWidth.text.toString().toIntOrNull() ?: 0
                if (recyclerGrid.itemDecorationCount > 0) {
                    recyclerGrid.removeItemDecoration(gridDeco2)
                }

                gridDeco2 = GridDividerItemDecoration(lineWidth, Color.parseColor("#b2b2b2"))
                recyclerGrid.addItemDecoration(gridDeco2)
                mAdapter.notifyDataSetChanged()
            }
            false
        }
    }

    companion object {
        const val SPAN_COUNT = 4
    }
}
