package com.example.lab2

import android.widget.TextView
import androidx.databinding.BindingAdapter
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
//
//@BindingAdapter("android:text")
//fun setText(view: TextView, value: Float?) {
//    if (value == null) return
//    view.text = value.toString()
//}
//
//@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
//fun getTextString(view: TextView): Float? {
//    return view.text.toString().toFloatOrNull() ?: 0f
//}
