package com.chd.mimitogether.ui.party.dto

data class DateReq(
    var day: Int,
    var hour: Int,
    var min: Int,
    var month: Int,
    var pid: String,
    var storeid: String,
    var year: Int
)