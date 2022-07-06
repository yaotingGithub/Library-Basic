package com.money.biometric

sealed class AuthenticationState {
    class OnSuccess: AuthenticationState()
    /**
     * 注意只會在第一次生物辨識失敗時呼叫
     */
    class OnFailed: AuthenticationState()
    /**
     * 用戶點選negativeButton時會回傳此狀態，此時errString會是設定的按鈕名稱
     */
    data class OnError(val errorCode: Int, val errString: CharSequence): AuthenticationState()
}
