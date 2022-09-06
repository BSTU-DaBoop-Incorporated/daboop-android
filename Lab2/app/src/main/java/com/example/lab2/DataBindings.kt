package com.example.lab2

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.Slider


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