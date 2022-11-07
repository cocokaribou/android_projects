package com.example.elandmall_kotlin.ui

sealed class ModuleData {
    abstract fun clone(): ModuleData

}
