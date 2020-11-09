package com.chd.mimitogether.ui.party.dto

import com.chd.mimitogether.dto.UserRequest
import java.io.Serializable
import java.util.*

data class Party (
    var id: String,
    var userList: MutableList<UserRequest>,
    var ptName: String,
    var recoList: List<Int>,
    var promiseLocation: String,
    var promiseStore: Store,
    var promiseTime: Date
): Serializable