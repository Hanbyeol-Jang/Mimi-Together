package com.chd.mimitogether.ui.party.service

import com.chd.mimitogether.ui.party.dto.*
import retrofit2.Call
import retrofit2.http.*

interface StoreService {

    @GET("store")
    fun getStoreList(@Query("pageno") pageno: Int): Call<StorePageDto>

    @GET("store/{id}")
    fun getStoreDetail(@Path("id") id: String): Call<Store>

    @GET("recom/multi")
    fun getRecommandStoreList(@Query("PartyId") PartyId : String): Call<List<MultiStore>>

}