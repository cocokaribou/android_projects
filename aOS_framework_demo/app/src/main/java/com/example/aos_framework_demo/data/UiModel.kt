package com.example.aos_framework_demo.data


class UiModel(
    private var tag: String,
    private var position: Int
) {
    fun setModel(tag:String, position: Int){
        this.tag = tag
        this.position = position
    }

    fun getTag(): String{
        return this.tag
    }

    fun getPosition():Int {
        return this.position
    }

}