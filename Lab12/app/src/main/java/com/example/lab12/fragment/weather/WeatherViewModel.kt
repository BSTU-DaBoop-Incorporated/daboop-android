package com.example.lab12.fragment.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {
    var dailyWeathers = MutableLiveData<List<DailyWeather>>()
}