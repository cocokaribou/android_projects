package com.example.aos_framework_demo.data


class UiModel {
    var tag: String
    var ctgPosition = 0
    var goodsPosition = 0

    constructor(tag: String) {
        this.tag = tag
    }

    constructor(tag: String, ctgPosition: Int) {
        this.tag = tag
        this.ctgPosition = ctgPosition
    }

    constructor(tag: String, ctgPosition: Int, goodsPosition: Int) {
        this.tag = tag
        this.ctgPosition = ctgPosition
        this.goodsPosition = goodsPosition
    }
}