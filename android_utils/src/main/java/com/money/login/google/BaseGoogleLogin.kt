package com.money.login.google

import android.app.Activity
import android.content.Intent
import com.money.login.LoginState
import kotlinx.coroutines.flow.Flow

interface BaseGoogleLogin {

    val loginGoogleFlow: Flow<LoginState>

    fun init(activity: Activity, serverClientId: String)

    fun loginWithGoogle(activity: Activity)

    fun onActivityResult(requestCode: Int, data: Intent?)
}