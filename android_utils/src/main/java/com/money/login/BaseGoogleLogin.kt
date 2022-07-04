package com.money.login

import android.app.Activity
import android.content.Intent

interface BaseGoogleLogin {

    fun doOnGoogleLoginFinish(state: LoginState) {}

    fun init(activity: Activity, serverClientId: String)

    fun loginWithGoogle(activity: Activity)

    fun onActivityResult(requestCode: Int, data: Intent?)
}