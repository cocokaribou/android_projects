package com.example.elandmall_kotlin.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.elandmall_kotlin.BaseApplication

fun Int.dpToPx(): Int {
    val density = BaseApplication.context.resources.displayMetrics.density
    return (this * density).toInt()
}
fun Float.dpToPx(): Int {
    val density = BaseApplication.context.resources.displayMetrics.density
    return (this * density).toInt()
}

fun Int.pxToDp(): Int {
    val density = BaseApplication.context.resources.displayMetrics.density
    return (this / density).toInt()
}

class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }
}

class GridSideSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column

        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount

        outRect.top = 0
        outRect.bottom = 0
    }
}


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