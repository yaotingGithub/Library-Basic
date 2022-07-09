package com.money.api.state

sealed class CheckLatestUserState {
    object IsLatest: CheckLatestUserState()
    object IsNotLatest: CheckLatestUserState()
    object TokenNotFound: CheckLatestUserState()
    data class OnError(val exception: Throwable): CheckLatestUserState()
}
