package com.ssafy.frontend

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance : Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    fun getInstnace() : Retrofit {
        if(instance == null){
            instance = Retrofit.Builder()
                .baseUrl("https://swapi.dev/api/people/") // baseUrl 설정하기
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }
}