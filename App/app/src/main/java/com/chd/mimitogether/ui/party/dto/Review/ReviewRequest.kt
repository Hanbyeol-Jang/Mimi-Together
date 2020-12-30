package com.chd.mimitogether.ui.party.dto.Review

data class ReviewRequest(
    val rating: Float,
    val resId: Int,
    val resName: String,
    val review: String,
    val userId: String,
    val userName: String
)