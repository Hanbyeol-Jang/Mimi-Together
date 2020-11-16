package com.chd.mimitogether.ui.party.dto.geocoding

data class Addresse(
    val addressElements: List<AddressElement>,
    val distance: Double,
    val englishAddress: String,
    val jibunAddress: String,
    val roadAddress: String,
    val x: String,
    val y: String
)