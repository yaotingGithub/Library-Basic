package com.money.android_utils

data class User(
    var name: String,
    var email: String,
    var authToken: String,
    var authID: String,
    var avatar: String,
    var loginType: String,
    var moneyToken: String = "",
    var moneyUserId: String = "",
    var isVip: Boolean = false,
    var vipEndTime: Long = 0L
)
