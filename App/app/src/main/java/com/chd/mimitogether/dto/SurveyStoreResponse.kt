package com.chd.mimitogether.dto

data class SurveyStoreResponse(
    val address: String,
    val bid: Int,
    val category: String,
    val id: Int,
    val img: String,
    val mainMn: String,
    val menu: String,
    val name: String,
    val price: String,
    val rating: Double,
    val rvwCnt: Int,
    val tags: String,
    val tel: String
)