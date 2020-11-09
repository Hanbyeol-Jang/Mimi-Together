package com.chd.mimitogether.ui.party.dto.Review

data class Content(
    val id: String,
    val rating: Int,
    val resId: Int,
    val resName: String,
    val review: String,
    val userName: String
)