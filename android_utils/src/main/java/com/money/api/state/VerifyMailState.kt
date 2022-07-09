package com.money.api.state

sealed class VerifyMailState: ApiState() {
    class OnReadyToSend(): VerifyMailState()
    class OnSendAlready(): VerifyMailState()
}
