package com.money.biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object BiometricAuthHelper {

    fun authenticate(
        activity: FragmentActivity, title: String, subTitle: String, negativeButtonText: String
    ): Flow<AuthenticationState> {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subTitle)
            .setNegativeButtonText(negativeButtonText).build()

        return flow {
            emit(awaitAuthenticate(activity, promptInfo))
        }
    }

    private suspend fun awaitAuthenticate(
        activity: FragmentActivity, promptInfo: BiometricPrompt.PromptInfo
    ): AuthenticationState = suspendCancellableCoroutine { continuation ->
        val executor = ContextCompat.getMainExecutor(activity)
        val callback = object: BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                continuation.resume(AuthenticationState.OnError(errorCode, errString))
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                /*
                  因為可以在頁面不退出的情況下多次觸發onFailed，需要加上isActive來確保resume只執行一次
                  否則執行超過一次會跳出IllegalStateException
                 */
                if (continuation.isActive) {
                    continuation.resume(AuthenticationState.OnFailed())
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                continuation.resume(AuthenticationState.OnSuccess())
            }
        }
        val biometricPrompt = BiometricPrompt(activity, executor, callback)
        biometricPrompt.authenticate(promptInfo)
    }

    /**
     * 取得生物識別驗證功能在設備上的狀態
     */
    fun getBiometricAuthenticateState(context: Context): BiometricAuthState {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS -> BiometricAuthState.Success
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> BiometricAuthState.NoHardware
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> BiometricAuthState.Unavailable
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> BiometricAuthState.NonEnrolled
            else -> BiometricAuthState.OtherError
        }
    }

    fun isBiometricEnabled(context: Context) = getBiometricAuthenticateState(context) == BiometricAuthState.Success
}