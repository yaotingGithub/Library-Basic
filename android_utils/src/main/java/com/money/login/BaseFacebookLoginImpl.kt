package com.money.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull
import org.json.JSONObject
import java.lang.IllegalStateException

class BaseFacebookLoginImpl: BaseFacebookLogin {

    private val callbackManager = CallbackManager.Factory.create()
    private lateinit var loginManager: LoginManager

    private val _loginState = MutableStateFlow<LoginState?>(null)
    override val loginFacebookFlow: Flow<LoginState> = _loginState.mapNotNull { it }

    override fun init() {
        loginManager = LoginManager.getInstance().apply {
            registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val fbAccessToken = result.accessToken
                    val authToken = fbAccessToken.token

                    val request = GraphRequest.newMeRequest(fbAccessToken) { jsonObject, _ ->
                        jsonObject?.let {
                            val data = handleSignInResult(it, authToken)
                            _loginState.value = LoginState.OnSuccess(data)
                        } ?: kotlin.run {
                            _loginState.value = LoginState.OnError(
                                IllegalStateException("FacebookCallback結果為onSuccess但jsonObject沒有資料")
                            )
                        }
                        loginManager.logOut()
                    }

                    val parameters = Bundle()
                    parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location")
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    _loginState.value = LoginState.OnCancel
                }

                override fun onError(error: FacebookException) {
                    _loginState.value = LoginState.OnError(error)
                }
            })
        }
    }

    override fun loginWithFacebook(activity: Activity) {
        loginManager.logInWithReadPermissions(activity, listOf("public_profile", "email"))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleSignInResult(
        jsonObject: JSONObject, authToken: String
    ): com.money.login.LoginResult {
        val email = jsonObject.get("email") as? String ?: ""
        val profile = Profile.getCurrentProfile()
        val avatar = profile?.getProfilePictureUri(200, 200).toString()
        return LoginResult(
            name = profile?.name ?: "",
            email = email,
            authToken = authToken,
            authID = profile?.id ?: "",
            avatar = avatar,
            loginType = "facebook"
        )
    }
}