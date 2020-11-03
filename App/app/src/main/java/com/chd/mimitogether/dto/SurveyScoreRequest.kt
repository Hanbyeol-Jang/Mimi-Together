package com.chd.mimitogether.dto

data class SurveyScoreRequest(
    var rating: Float,
    var rid: Int,
    var uid: String
)