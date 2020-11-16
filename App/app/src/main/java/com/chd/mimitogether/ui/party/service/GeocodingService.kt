package com.chd.mimitogether.ui.party.service

import com.chd.mimitogether.ui.party.dto.geocoding.GeocodingResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GeocodingService {

    @Headers(
        "X-NCP-APIGW-API-KEY-ID: fgigu61u39",
        "X-NCP-APIGW-API-KEY: dcq1JvjqS2EJQr72oUjIHU1SNSeOkDdw5nFK3H6X"
    )
    @GET("map-geocode/v2/geocode")
    fun getLatLng(@Query("query") query: String) : Call<GeocodingResponse>

}