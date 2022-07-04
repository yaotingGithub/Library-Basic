package com.money.login

sealed class LoginState {
    data class OnSuccess(val result: LoginResult): LoginState()
    object OnCancel: LoginState()
    data class OnError(val exception: Throwable): LoginState()
}
