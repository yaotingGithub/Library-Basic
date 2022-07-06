package com.money.biometric

sealed class BiometricAuthState {
    object Success: BiometricAuthState()
    /**
     * 硬體不支持生物辨識功能
     */
    object NoHardware: BiometricAuthState()
    /**
     * 目前無法使用
     */
    object Unavailable: BiometricAuthState()
    /**
     * 尚未設置生物辨識
     */
    object NonEnrolled: BiometricAuthState()
    /**
     * STATUS_UNKNOWN, ERROR_SECURITY_UPDATE_REQUIRED
     */
    object OtherError: BiometricAuthState()
}
