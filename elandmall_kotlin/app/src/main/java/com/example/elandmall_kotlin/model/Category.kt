package com.example.elandmall_kotlin.model

data class Category(
    val image: String?,
    val title: String?,
    val payload1: String? = null,
    val payload2: String? = null
){
    constructor(title: String, payload2: Int) : this(image = "", title = title, payload1 = "", payload2 = payload2.toString())
}
