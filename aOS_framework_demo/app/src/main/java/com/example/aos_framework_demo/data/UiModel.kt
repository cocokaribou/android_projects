package com.example.aos_framework_demo.data


class UiModel{
    var tag: String
    var position = 0

    constructor(tag: String){
        this.tag = tag
    }
    constructor(tag: String, position: Int){
        this.tag = tag
        this.position = position
    }
}