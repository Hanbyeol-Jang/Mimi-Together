package com.chd.mimitogether.dto

import java.io.Serializable

data class UserRequest(
    val id: String,
    val uiName: String,
    val isSurvey: String,
    val uiProfile: String,
    val uiThumb: String,
    val uiToken: String
): Serializable