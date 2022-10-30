package com.example.lab12.fragment.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.lab12.R

class DailyWeatherListAdapter(private val lifecycleOwner: LifecycleOwner, private val DailyWeathers: 
LiveData<List<DailyWeather>>, private val clickListener: ( (DailyWeather, View) -> Unit)?
= null) : 
    RecyclerView
.Adapter<WeatherViewHolder>
    () {
    init {
        DailyWeathers.observe(lifecycleOwner) { it ->
            notifyDataSetChanged()
        }
    }
    
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.daily_weather_card,
            parent,
            false)
        return WeatherViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(DailyWeathers.value!![position])
        clickListener?.let { holder.setOnItemClickListener(it) }
    }

    override fun getItemCount(): Int {
        return DailyWeathers.value?.size ?: 0
    }

}