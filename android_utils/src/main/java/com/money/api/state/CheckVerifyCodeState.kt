package com.money.api.state

sealed class CheckVerifyCodeState {
    class OnCheckSuccess(): CheckVerifyCodeState()
    class RequestNotFound(): CheckVerifyCodeState()
    class OnCheckFailed(): CheckVerifyCodeState()
    data class OnError(val exception: Throwable): CheckVerifyCodeState()
}