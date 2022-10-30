package com.example.lab12.tools.retrofit

import com.example.lab12.fragment.currency.Currency
import com.example.lab12.tools.retrofit.request.CurrencyListRequestResult
import com.example.lab12.tools.retrofit.request.WeatherRequestResult
import retrofit2.Call
import retrofit2.http.GET


interface RetrofitServices {
    @GET("https://api.coincap.io/v2/assets?limit=20")
    fun getCurrencyList(): Call<CurrencyListRequestResult>
    
    
    @GET("https://api.open-meteo.com/v1/forecast?latitude=53.9006&longitude=27.5590&appid=c54bce6e66e82a1f877066eac1d20409&daily=temperature_2m_max,temperature_2m_min,weathercode&timezone=Europe/Minsk&current_weather=true")
    fun getWeather(): Call<WeatherRequestResult>
}
