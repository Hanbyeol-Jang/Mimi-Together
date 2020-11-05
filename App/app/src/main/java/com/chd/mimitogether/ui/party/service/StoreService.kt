package com.chd.mimitogether.ui.party.service

import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.dto.PartyCreate
import com.chd.mimitogether.ui.party.dto.Store
import com.chd.mimitogether.ui.party.dto.StorePageDto
import retrofit2.Call
import retrofit2.http.*

interface StoreService {

    @GET("store")
    fun getStoreList(@Query("pageno") pageno: Int): Call<StorePageDto>


}