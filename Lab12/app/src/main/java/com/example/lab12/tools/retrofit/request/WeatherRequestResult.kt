package com.example.lab12.tools.retrofit.request
data class WeatherRequestResult (
    var daily: Daily
) {
    data class Daily(val time: List<String>, val temperature_2m_max: List<Float>, val 
    temperature_2m_min: List<Float>, val weathercode: List<Int>)
}