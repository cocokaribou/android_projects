package com.pionnet.overpass.customView

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

/**
 * sticky header 데코레이션
 * isHeader : false -> showHeader, true -> hideHeader
 */
class StickyHeaderItemDecoration(private val mListener: StickyHeaderInterface) : ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val topChild = parent.getChildAt(0) ?: return

        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        val currentHeader = getHeaderViewForItem(topChildPosition, parent)
        fixLayoutSize(parent, currentHeader)
        val contactPoint = currentHeader.bottom
        val childInContact: View = getChildInContact(parent, contactPoint) ?: return

        if (mListener.isHeader(parent.getChildAdapterPosition(childInContact))) {
            mListener.hideHeader()
            return
        }

        mListener.showHeader()
    }

    private fun getHeaderViewForItem(itemPosition: Int, parent: RecyclerView): View {
        val headerPosition = mListener.getHeaderPositionForItem(itemPosition)
        val layoutResId: Int = mListener.getHeaderLayout(headerPosition)
        return LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child.bottom > contactPoint) {
                if (child.top <= contactPoint) {
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    private fun fixLayoutSize(parent: ViewGroup, view: View) {

        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)
        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    interface StickyHeaderInterface {
        fun getHeaderPositionForItem(itemPosition: Int): Int
        fun getHeaderLayout(headerPosition: Int): Int
        fun isHeader(itemPosition: Int): Boolean
        fun hideHeader()
        fun showHeader()
    }
}