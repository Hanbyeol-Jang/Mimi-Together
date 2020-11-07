package com.chd.mimitogether.ui.party.dto.geocoding

data class GeocodingResponse(
    val addresses: List<Addresse>,
    val errorMessage: String,
    val meta: Meta,
    val status: String
)