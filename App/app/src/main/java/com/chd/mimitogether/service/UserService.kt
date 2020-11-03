package com.chd.mimitogether.service

import com.chd.mimitogether.dto.UserRequest
import com.chd.mimitogether.dto.UserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("user/login")
    fun userJoin(@Body user: UserRequest): Call<UserResponse>

}