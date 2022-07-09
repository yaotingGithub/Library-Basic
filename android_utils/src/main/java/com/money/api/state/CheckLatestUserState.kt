package com.money.api.state

sealed class CheckLatestUserState: ApiState() {
    object IsLatest: CheckLatestUserState()
    object IsNotLatest: CheckLatestUserState()
    object TokenNotFound: CheckLatestUserState()
}
