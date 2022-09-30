package com.example.lab5.model

@kotlinx.serialization.Serializable
class Todo : java.io.Serializable {
    var task: String? = null
    var difficulty: Int? = null
    var id: String? = null
    var isDone: Boolean? = null
}