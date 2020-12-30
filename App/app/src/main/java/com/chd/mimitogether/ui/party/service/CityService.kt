package com.chd.mimitogether.ui.party.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CityService {

    @GET("city")
    fun getSiList(): Call<List<String>>

    @GET("city/{si}")
    fun getGuList(@Path("si") si : String): Call<List<String>>

    @GET("city/{si}/{gun}")
    fun getDongList(@Path("si") si : String,
                    @Path("gun") gun : String): Call<List<String>>


}