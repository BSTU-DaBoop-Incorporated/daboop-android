package com.example.lab2

import androidx.databinding.InverseMethod

object StringToFloatConverter {
    @InverseMethod("floatToString")
    @JvmStatic
    public fun stringToFloat(value: String): Float {
        return value.toFloatOrNull() ?: 0f
    }


    @JvmStatic
    public fun floatToString(value: Float): String {
        return value.toString()
    }
}