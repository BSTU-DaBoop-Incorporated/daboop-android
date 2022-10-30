package com.example.lab12.fragment.weather

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.lab12.BR

class WeatherViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    lateinit var dailyWeather: DailyWeather
    
    fun setOnItemClickListener(listener: (DailyWeather, View) -> Unit) {
        itemView.setOnClickListener { listener(dailyWeather, itemView) }
    }
    fun bind(DailyWeather: DailyWeather) {
        binding.setVariable(BR.dailyWeather, DailyWeather)
        this.dailyWeather = DailyWeather
    }
}
