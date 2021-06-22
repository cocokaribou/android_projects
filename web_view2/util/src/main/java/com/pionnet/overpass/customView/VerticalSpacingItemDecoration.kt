package com.pionnet.overpass.customView

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Vertical 데코레이션
 * @param spacing : 간격
 * @param firstEndSpacing : 첫번째 마지막 간격
 * @param includeTopBottom : 첫번째 마지막 간격 포함여부
 */
class VerticalSpacingItemDecoration(private val spacing: Int,
                                      private val firstEndSpacing: Int,
                                      private val includeTopBottom: Boolean)
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

        if (includeTopBottom) {
            when (position) {
                0 -> {
                    outRect.top = firstEndSpacing
                    outRect.bottom = spacing
                }
                itemCount - 1 -> {
                    outRect.bottom = firstEndSpacing
                }
                else -> {
                    outRect.bottom = spacing
                }
            }
        } else {
            if (position != itemCount - 1) {
                outRect.bottom = spacing
            }
        }
    }
}