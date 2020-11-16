package com.chd.mimitogether.ui.auction.dto

data class User(
    val device: String,
    val diningList: List<String>,
    val id: String,
    val isSurvey: String,
    val partyList: List<String>,
    val uiName: String,
    val uiProfile: String,
    val uiThumb: String,
    val uiToken: String
)