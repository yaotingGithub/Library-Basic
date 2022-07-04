package com.money.login

import android.app.Activity
import android.content.Intent
import kotlinx.coroutines.flow.Flow

interface BaseGoogleLogin {

    val loginGoogleFlow: Flow<LoginState>

    fun init(activity: Activity, serverClientId: String)

    fun loginWithGoogle(activity: Activity)

    fun onActivityResult(requestCode: Int, data: Intent?)
}