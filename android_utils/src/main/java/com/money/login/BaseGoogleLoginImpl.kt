package com.money.login

import android.app.Activity
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class BaseGoogleLoginImpl: BaseGoogleLogin {

    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun init(activity: Activity, serverClientId: String) {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().requestIdToken(serverClientId).build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    }

    override fun signIn(activity: Activity) {
        val signInIntent = mGoogleSignInClient?.signInIntent
        signInIntent?.let {
            activity.startActivityForResult(it, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, data: Intent?, callback: (Result<LoginResult>) -> Unit) {
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            callback(handleSignInResult(task))
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
                loginType = "google"
            )
        }
    }

    companion object {
        const val RC_SIGN_IN = 9001
    }
}