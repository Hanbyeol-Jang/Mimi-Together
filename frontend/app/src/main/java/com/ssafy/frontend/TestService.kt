package com.ssafy.frontend

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TestService {

    @GET("temp")
    fun users(): Call<List<User>>

    @POST("inputTest/post")
    fun input(@Body store: Store): Call<Void>
}