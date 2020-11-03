package com.chd.mimitogether.dto

data class UserRequest(
    val id: String,
    val uiName: String,
    val isSurvey: String,
    val uiProfile: String,
    val uiThumb: String,
    val uiToken: String
)