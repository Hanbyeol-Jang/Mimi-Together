package com.chd.mimitogether.service

import com.chd.mimitogether.dto.jusoSearch.JusoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JusoService {
    @GET("addrlink/addrLinkApi.do?confmKey=devU01TX0FVVEgyMDIwMTEwNzIwMTUzNDExMDM4NDM=&currentPage=1&countPerPage=10&resultType=json")
    fun getJuso(@Query("keyword") keyword: String) : Call<JusoResponse>
}