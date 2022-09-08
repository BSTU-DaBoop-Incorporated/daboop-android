package com.example.lab2

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.Slider
import java.text.NumberFormat
import java.util.*


@InverseBindingAdapter(attribute = "android:value")
fun getSliderValue(slider: Slider) = slider.value

@BindingAdapter("android:valueAttrChanged")
fun setSliderListeners(slider: Slider, attrChange: InverseBindingListener) {
    slider.addOnChangeListener { _, _, _ ->
        attrChange.onChange()
    }
}


val dwellingTypeMap = mapOf(
    "Новостройка" to DwellingType.New,
    "Вторичное" to DwellingType.Resell,
    "Строится" to DwellingType.Building,
)
val reverseDwellingTypeMap = dwellingTypeMap.entries.associate { (k, v) -> v to k }

@BindingConversion
fun convertDwellingTypeToString(dwellingType: DwellingType): String? {
    return reverseDwellingTypeMap[dwellingType];
}
@BindingConversion
fun convertStringToDwellingType(dwellingType: String): DwellingType {
    return dwellingTypeMap[dwellingType] ?: DwellingType.New
}

@BindingConversion
fun floatToStringConversion(value: Float): String {
    return StringToFloatConverter.floatToString(value)
}

@BindingConversion
fun booleanToVisibility(value: Boolean): Int {
    return if (value) View.VISIBLE else View.GONE
}