package com.example.lab12.fragment.weather

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab12.R
import com.example.lab12.databinding.FragmentWeatherBinding
import com.example.lab12.tools.retrofit.RetrofitClient
import com.example.lab12.tools.retrofit.request.WeatherRequestResult
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeatherFragment : Fragment() {

    lateinit var binding: FragmentWeatherBinding
    var dailyWeatherCall: Call<WeatherRequestResult>? = null


    companion object {
        fun newInstance() = WeatherFragment()
    }

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        binding.weatherRecycler.adapter = DailyWeatherListAdapter(viewLifecycleOwner, viewModel
            .dailyWeathers)
        binding.weatherRecycler.layoutManager = LinearLayoutManager(context)

        dailyWeatherCall = RetrofitClient.service.getWeather()
        dailyWeatherCall!!.enqueue(object :
            retrofit2.Callback<WeatherRequestResult> {
            override fun onFailure(call: Call<WeatherRequestResult>, t: Throwable) {
                Toast.makeText(context, "Fetch error", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.GONE
            }

            override fun onResponse(
                call: Call<WeatherRequestResult>,
                response: Response<WeatherRequestResult>,
            ) {
                if (response.isSuccessful) {
                    val dailyWeathers = response.body()!!.daily
                    viewModel.dailyWeathers.value = dailyWeathers.time.mapIndexed() { index, time ->
                        val localDate: LocalDate = LocalDate.parse(time, DateTimeFormatter
                            .ofPattern("yyyy-MM-dd"))
                        val formattedTime = "${localDate.month} ${localDate.dayOfMonth}"
                        val weatherIcon = when (dailyWeathers.weathercode[index]) {
                            0 -> com.github.pwittchen.weathericonview.library.R.string.wi_day_sunny
                            in 1..2 -> com.github.pwittchen.weathericonview.library.R.string
                                .wi_day_cloudy
                            3 -> com.github.pwittchen.weathericonview.library.R.string.wi_day_sunny_overcast
                            in 11..20 -> com.github.pwittchen.weathericonview.library.R.string.wi_day_cloudy
                            in 21..30 -> com.github.pwittchen.weathericonview.library.R.string.wi_day_cloudy_gusts
                            in 31..40 -> com.github.pwittchen.weathericonview.library.R.string.wi_day_cloudy_windy
                            in 41..50 -> com.github.pwittchen.weathericonview.library.R.string.wi_day_fog
                            in 51..60 -> com.github.pwittchen.weathericonview.library.R.string
                                .wi_day_rain
                            in 61..70 -> com.github.pwittchen.weathericonview.library.R.string.wi_day_rain
                            in 71..80 -> com.github.pwittchen.weathericonview.library.R.string.wi_day_snow
                            in 81..90 -> com.github.pwittchen.weathericonview.library.R.string.wi_day_hail
                            in 90..100 -> com.github.pwittchen.weathericonview.library.R.string
                                .wi_day_thunderstorm

                            else -> {
                                com.github.pwittchen.weathericonview.library.R.string.wi_day_sunny
                            }
                        }
                        DailyWeather(
                            formattedTime,
                            dailyWeathers.temperature_2m_max[index],
                            dailyWeathers.temperature_2m_min[index],
                            weatherIcon
                        )
                    }
                }
                binding.progressBar.visibility = View.GONE
            }
        })
        binding.progressBar.visibility = View.VISIBLE
    }


}