package com.pionnet.overpass.customView.itemDecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Horizontal 데코레이션
 * @param spacing : 간격
 * @param edgeSpacing : 처음과 끝 간격
 * @param includeEdge : 처음과 끝 포함시킬지 유무
 */
class HorizontalSpacingItemDecoration(private val spacing: Int,
                                  private val edgeSpacing: Int,
                                  private val includeEdge: Boolean)
    : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        if (position == RecyclerView.NO_POSITION) {
            return
        }
        val itemCount = state.itemCount

        if (includeEdge) {
            when (position) {
                0 -> {
                    outRect.left = edgeSpacing
                    outRect.right = spacing
                }
                itemCount - 1 -> {
                    outRect.right = edgeSpacing
                }
                else -> {
                    outRect.right = spacing
                }
            }
        } else {
            if (position != itemCount - 1) {
                outRect.right = spacing
            }
        }
    }
}