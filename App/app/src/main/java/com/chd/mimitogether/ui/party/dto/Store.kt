package com.chd.mimitogether.ui.party.dto

data class Store(
    val id: Int,
    val boID: String,
    val name: String,
    val address: String,
    val tel: String,
    val category: String,
    val mainMn: String,
    val price: String,
    val menu: String,
    val rating: Double,
    val rvwCnt: Int,
    val img: String,
    val tags: String
)