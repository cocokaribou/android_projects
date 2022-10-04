package com.youngin.lunch.model

import java.util.*

class Selected(val no: Int, val name: String)
object Category {
    private val categories by lazy {
        mapOf(
            1 to "한식",
            2 to "중식",
            3 to "양식",
            4 to "일식") }
    private val randIndex = Random().nextInt(4)+1
    val selectedCate = Selected(randIndex, categories[randIndex]!!)
}

object Store {
    var selectedStore : StoreDataList.StoreData? = null
}