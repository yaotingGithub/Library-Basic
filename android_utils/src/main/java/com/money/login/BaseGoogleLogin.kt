package com.money.login

import android.app.Activity
import android.content.Intent

interface BaseGoogleLogin {

    fun init(activity: Activity, serverClientId: String)

    fun signIn(activity: Activity)

    fun onActivityResult(requestCode: Int, data: Intent?, callback: (Result<LoginResult>) -> Unit)
}