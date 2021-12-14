package com.example.app5.data

data class User(
    var id: String,
    var nm: String
) {
    constructor():this("", "")

    override fun toString(): String {
        return "{\"id\" = $id, \"name\" = $nm}"
    }

}
