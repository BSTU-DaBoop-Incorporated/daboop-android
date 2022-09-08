package com.example.lab2

import androidx.databinding.InverseMethod
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

object StringToFloatConverter {
    @InverseMethod("floatToString")
    @JvmStatic
    public fun stringToFloat(value: String): Float {
        val current: Locale = Locale.getDefault()
        val numberFormat = NumberFormat.getNumberInstance(current)
        numberFormat.maximumFractionDigits = 1
        numberFormat.minimumFractionDigits = 1
        return numberFormat.parse(value)?.toFloat() ?: 0f
    }


    @JvmStatic
    public fun floatToString(value: Float): String {
        val current: Locale = Locale.getDefault()
        val numberFormat = DecimalFormat.getNumberInstance(current)
        numberFormat.maximumFractionDigits = 1
        numberFormat.minimumFractionDigits = 1
        numberFormat.isGroupingUsed = false

        return numberFormat.format(value)
    }
    
    
    @JvmStatic
    public fun floatToStringBeautified(value: Float): String {
        val current: Locale = Locale.getDefault()
        val numberFormat = DecimalFormat.getNumberInstance(current)
        numberFormat.maximumFractionDigits = 1
        numberFormat.minimumFractionDigits = 1
        return numberFormat.format(value)
    }
}