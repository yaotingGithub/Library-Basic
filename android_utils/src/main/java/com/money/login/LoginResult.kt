package com.money.login

data class LoginResult (
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