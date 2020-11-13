package com.chd.mimitogether.ui.auction.dto

data class Auction(
    val dnDate: String,
    val dnLocation: String,
    val dnMenu: List<String>,
    val dnName: String,
    val dnPeople: Int,
    val dnPrice: Int,
    val dnStatus: Int,
    val id: String,
    val soldID: Any,
    val storeList: List<Any>,
    val user: User
)