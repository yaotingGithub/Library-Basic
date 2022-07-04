package com.money.login

import android.app.Activity
import android.content.Intent
import kotlinx.coroutines.flow.Flow

interface BaseFacebookLogin {

    val loginFacebookFlow: Flow<LoginState>

    fun init()

    fun loginWithFacebook(activity: Activity)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}