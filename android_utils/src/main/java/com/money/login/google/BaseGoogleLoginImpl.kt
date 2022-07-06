package com.money.login.google

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.money.login.LoginResult
import com.money.login.LoginState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.mapNotNull

class BaseGoogleLoginImpl: BaseGoogleLogin {

    private var mGoogleSignInClient: GoogleSignInClient? = null

    private val _loginState = MutableStateFlow<LoginState?>(null)
    override val loginGoogleFlow: Flow<LoginState> = _loginState.mapNotNull { it }

    private lateinit var appId: String

    override fun init(activity: Activity, serverClientId: String) {
        appId = serverClientId
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().requestIdToken(serverClientId).build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    override fun loginWithGoogle(activity: Activity) {
        val signInIntent = mGoogleSignInClient?.signInIntent
        signInIntent?.let {
            activity.startActivityForResult(it, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task).fold({
                _loginState.value = LoginState.OnSuccess(it)
            }, {
                _loginState.value = LoginState.OnError(it)
            })
            // 注意原本做法在登入後的最後都會登出
            mGoogleSignInClient?.signOut()
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>): Result<LoginResult> {
        return kotlin.runCatching {
            val account = task.getResult(ApiException::class.java)
            val email = account.email ?: ""
            val authToken = account.idToken ?: ""
            val authId = account.id ?: ""
            val name = account.displayName ?: email
            val avatar = account.photoUrl.toString()
            LoginResult(
                name = name,
                email = email,
                authToken = authToken,
                authID = authId,
                avatar = avatar,
                loginType = "google",
                appId = appId
            )
        }
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }
}