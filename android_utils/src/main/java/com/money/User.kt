package com.money

data class User(
    val name: String,
    val email: String,
    val authToken: String,
    val authID: String,
    val avatar: String,
    val loginType: String,
    var moneyToken: String = "",
    var moneyUserId: String = "",
    var isVip: Boolean = false,
    var vipEndTime: Long = 0L
) {
}