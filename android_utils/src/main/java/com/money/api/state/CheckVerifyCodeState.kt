package com.money.api.state

sealed class CheckVerifyCodeState: ApiState() {
    class OnCheckSuccess(): CheckVerifyCodeState()
    class RequestNotFound(): CheckVerifyCodeState()
    class OnCheckFailed(): CheckVerifyCodeState()
}