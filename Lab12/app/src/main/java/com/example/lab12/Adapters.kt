package com.example.lab12

import androidx.databinding.BindingConversion

@BindingConversion
fun floatToCelsius(f: Float): String {
    val result = f.toString()
    return "$result°C"
}