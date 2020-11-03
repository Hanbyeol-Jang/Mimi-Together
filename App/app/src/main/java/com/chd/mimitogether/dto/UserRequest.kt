package com.chd.mimitogether.dto

data class UserRequest(
    val id: String,
    val uname: String,
    val isSurvey: String,
    val uprofile: String,
    val uthumb: String,
    val utoken: String
)