package com.chd.mimitogether.service

import com.chd.mimitogether.ui.auction.dto.Auction
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AuctionService {

    @GET("/dining/list/{id}")
    fun getDiningList(@Path("id") id: String) : Call<List<Auction>>

}