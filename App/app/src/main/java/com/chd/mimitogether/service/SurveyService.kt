package com.chd.mimitogether.service

import com.chd.mimitogether.dto.SurveyScoreRequest
import com.chd.mimitogether.dto.SurveyStoreResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SurveyService{

    @GET("store/surbey")
    fun getStoreList(): Call<List<SurveyStoreResponse>>

    @POST("recom/survey")
    fun surveyComplete(@Body survey_list: List<SurveyScoreRequest>): Call<Void>

}