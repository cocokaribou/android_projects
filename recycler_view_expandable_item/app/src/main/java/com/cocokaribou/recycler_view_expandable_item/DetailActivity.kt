package com.cocokaribou.recycler_view_expandable_item

import android.os.Bundle
import android.os.PersistableBundle
import com.skydoves.transformationlayout.TransformationAppCompatActivity
import com.skydoves.transformationlayout.onTransformationStartContainer

class DetailActivity:TransformationAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        onTransformationStartContainer()
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_detail)
    }
}