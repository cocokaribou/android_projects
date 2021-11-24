package com.pionnet.overpass.customView.itemDecoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * 자주에서 사용 - 슬라이드 메뉴 -> 헬스뷰티 클릭 시 나옴
 * width 가 없을 때 height 값으로 전체 두께 정해짐
 * @param height : 가로 선 두께
 * @param width : 세로 선 두께
 * @param color : 선 색깔
 */
class GridDividerItemDecoration : ItemDecoration {
    private var mPaint: Paint?
    private var mDividerWidth: Int
    private var mDividerHeight: Int

    constructor(height: Int) {
        mDividerWidth = height
        mDividerHeight = height
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = Color.TRANSPARENT
        mPaint!!.style = Paint.Style.FILL
    }

    constructor(height: Int, @ColorInt color: Int) {
        mDividerWidth = height
        mDividerHeight = height
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = color
        mPaint!!.style = Paint.Style.FILL
    }

    constructor(width: Int, height: Int, @ColorInt color: Int) {
        mDividerWidth = width
        mDividerHeight = height
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint!!.color = color
        mPaint!!.style = Paint.Style.FILL
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        val spanCount = getSpanCount(parent)
        val childCount = parent.adapter!!.itemCount
        val isLastRow = isLastRow(parent, itemPosition, spanCount, childCount)
        val top = 0
        val left: Int
        val right: Int
        var bottom: Int
        val eachWidth = (spanCount - 1) * mDividerWidth / spanCount
        val dl = mDividerWidth - eachWidth
        left = itemPosition % spanCount * dl
        right = eachWidth - left
        bottom = mDividerHeight
        if (isLastRow) {
            bottom = 0
        }
        outRect[left, top, right] = bottom
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        draw(c, parent)
    }

    private fun draw(canvas: Canvas, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val layoutParams = child.layoutParams as RecyclerView.LayoutParams
            val isLastRow = isLastRow(parent, i, getSpanCount(parent), childCount)
            var left = child.left
            var right = child.right
            var top = child.bottom + layoutParams.bottomMargin
            var bottom = top + mDividerHeight
            if (isLastRow) bottom = top
            if (mPaint != null) {
                canvas.drawRect(
                    left.toFloat(),
                    top.toFloat(),
                    right.toFloat(),
                    bottom.toFloat(),
                    mPaint!!
                )
            }

            top = child.top
            bottom = child.bottom + mDividerHeight
            left = child.right + layoutParams.rightMargin
            right = left + mDividerWidth
            if (isLastRow) bottom = child.bottom
            if (mPaint != null) {
                canvas.drawRect(
                    left.toFloat(),
                    top.toFloat(),
                    right.toFloat(),
                    bottom.toFloat(),
                    mPaint!!
                )
            }
        }
    }

    private fun isLastColumn(
        parent: RecyclerView, pos: Int, spanCount: Int,
        childCount: Int
    ): Boolean {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) {
                return true
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                .orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0)
                {
                    return true
                }
            } else {
                childCount -= childCount % spanCount
                if (pos >= childCount)
                    return true
            }
        }
        return false
    }

    private fun isLastRow(
        parent: RecyclerView, pos: Int, spanCount: Int,
        childCount: Int
    ): Boolean {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            val lines =
                if (childCount % spanCount == 0) childCount / spanCount else childCount / spanCount + 1
            return lines == pos / spanCount + 1
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                .orientation
            // StaggeredGridLayoutManager
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount -= childCount % spanCount
                if (pos >= childCount) return true
            } else {
                if ((pos + 1) % spanCount == 0) {
                    return true
                }
            }
        }
        return false
    }

    private fun isFirstRow(
        parent: RecyclerView, pos: Int, spanCount: Int,
        childCount: Int
    ): Boolean {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            return pos / spanCount + 1 == 1
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                .orientation
            // StaggeredGridLayoutManager
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount -= childCount % spanCount
                if (pos >= childCount) return true
            } else {
                if ((pos + 1) % spanCount == 0) {
                    return true
                }
            }
        }
        return false
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager
                .spanCount
        }
        return spanCount
    }
}