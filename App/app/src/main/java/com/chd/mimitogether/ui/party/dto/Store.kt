package com.chd.mimitogether.ui.party.dto

import java.io.Serializable

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
): Serializable