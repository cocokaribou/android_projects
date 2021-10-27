package com.example.sampleapp

import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener() : AbsListView.OnScrollListener {

    private var visibleThreshold = 5
    private var currentPage = 0
    private var prevTotalItemCount = 0
    private var isLoading = true
    private var startingPageIndex = 0

    constructor(visibleCount: Int) : this() {
        visibleThreshold = visibleCount
    }

    constructor(visibleCount: Int, startPage: Int) : this() {
        visibleThreshold = visibleCount
        startingPageIndex = startPage
        currentPage = startPage
    }

//    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//        Logger.e("newstate $newState")
//        super.onScrollStateChanged(recyclerView, newState)
//    }

//    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//        Logger.e("x $dx")
//        Logger.e("y $dy")
//        super.onScrolled(recyclerView, dx, dy)
//    }


    override fun onScroll(p0: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {

        // if the total item count is zero and the previous isn't
        // assume the list is invalidated and should be reset back to initial state
        if (totalItemCount < prevTotalItemCount) {
            currentPage = startingPageIndex
            prevTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                isLoading = true
            }
        }

        // if it's still loading, check if the dataset count has changed
        if (isLoading && (totalItemCount > prevTotalItemCount)) {
            isLoading = false
            prevTotalItemCount = totalItemCount
            currentPage++
        }

        // if it isn't currently loading, check if it has breached the visibleThreshold thus needed to reload more data
        if (!isLoading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount) {
            isLoading = onLoadMore(currentPage++, totalItemCount)
        }
    }

    abstract fun onLoadMore(currentPage: Int, totalItemCount: Int): Boolean
}