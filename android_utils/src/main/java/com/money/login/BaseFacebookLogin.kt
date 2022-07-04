package com.money.login

import android.app.Activity
import android.content.Intent

interface BaseFacebookLogin {

    fun doOnFacebookLoginFinish(state: LoginState) {}

    fun init()

    fun loginWithFacebook(activity: Activity)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}