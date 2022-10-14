package com.example.lab5.model

@kotlinx.serialization.Serializable
data class Todo(
    var task: String? = null, var difficulty: Int? = null, var id: Long? = null,
    var isDone:
    Boolean? = null,
) : java.io.Serializable