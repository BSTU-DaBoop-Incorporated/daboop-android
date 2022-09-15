package com.example.lab3

import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.databinding.ObservableInt
import java.io.Serializable

class Game : Serializable {
    val title = ObservableField<String>()
    val releaseYear = ObservableInt()
    val genre = ObservableField<String>()
    val rating = ObservableFloat()
    val publisher = ObservableField<String>()
    val developer = ObservableField<String>()
}