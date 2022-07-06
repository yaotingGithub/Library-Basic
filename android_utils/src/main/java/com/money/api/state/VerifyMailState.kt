package com.money.api.state

sealed class VerifyMailState {
    class OnReadyToSend(): VerifyMailState()
    class OnSendAlready(): VerifyMailState()
    data class OnError(val exception: Throwable): VerifyMailState()
}
