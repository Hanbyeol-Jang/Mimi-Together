package com.chd.mimitogether.ui.party.dto

data class PartyListItem(
    val id: String,
    val promiseStore: Any,
    val promiseTime: Any,
    val ptName: String,
    val recoList: Any,
    val userList: List<String>
)