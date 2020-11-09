package com.chd.mimitogether.ui.party.service

import com.chd.mimitogether.ui.party.dto.Review.ReviewList
import com.chd.mimitogether.ui.party.dto.Review.ReviewRequest
import retrofit2.Call
import retrofit2.http.*

interface ReviewService {


    @GET("review/{id}")
    fun getStoreview(@Path("id") id: Int,
                     @Query("pageno") pageno: Int): Call<ReviewList>


    @POST("review/write")
    fun writeReview(@Body reviewreq : ReviewRequest): Call<Void>


}