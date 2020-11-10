package com.chd.mimitogether.ui.party.dto.Review

data class Content(
    val id: String,
    val rating: Float,
    val resId: Int,
    val resName: String,
    val review: String,
    val userName: String,
    val userId: String
)