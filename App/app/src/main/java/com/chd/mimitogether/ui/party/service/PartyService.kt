package com.chd.mimitogether.ui.party.service

import com.chd.mimitogether.ui.party.dto.Party
import com.chd.mimitogether.ui.party.dto.PartyCreate
import retrofit2.Call
import retrofit2.http.*


interface PartyService {

    //모임 생성
    @POST("party/create")
    fun createParty(@Body partyCreate : PartyCreate): Call<Party>

    //유저 id로 party 정보 목록 가져오기
    @GET("party/list/{id}")
    fun getPartyList(@Path("id") id: String): Call<List<Party>>

    @POST("party/join")
    fun joinParty(@Query("partyId") partyId: String,
                  @Query("userId") userId: String): Call<Party>

    @GET("party/{id}")
    fun getPartyDetail(@Path("id") id : String): Call<Party>

}