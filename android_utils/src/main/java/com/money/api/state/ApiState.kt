package com.money.api.state

sealed class ApiState {
    class OnNetworkDisconnected: ApiState()
    data class OnError(val exception: Throwable): ApiState()
}