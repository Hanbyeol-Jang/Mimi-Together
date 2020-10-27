package com.ssafy.frontend

import retrofit2.Call
import retrofit2.http.GET

interface TestService {

    @GET("temp")
    fun users(): Call<List<User>>

}