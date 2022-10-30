package com.example.lab12.tools.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null
    val client: Retrofit get() {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("http://localhost/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }


    val service: RetrofitServices
        get() = client.create(RetrofitServices::class.java)
    
}