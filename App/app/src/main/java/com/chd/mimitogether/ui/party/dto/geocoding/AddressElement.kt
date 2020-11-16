package com.chd.mimitogether.ui.party.dto.geocoding

data class AddressElement(
    val code: String,
    val longName: String,
    val shortName: String,
    val types: List<String>
)