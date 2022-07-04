package com.money.android_utils

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.gms.common.api.ApiException


abstract class BaseLoginActivity: AppCompatActivity() {

    abstract val serverClientId: String

    abstract fun getLayoutView(): View

    abstract fun init()
    /**
     * google登入結束後的狀態處理
     */
    abstract fun handleGoogleSignInResult(result: Result<User>)

    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutView())

        init()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().requestIdToken(serverClientId).build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(handleSignInResult(task))
            // 注意原本做法在登入後的最後都會登出
            mGoogleSignInClient?.signOut()
        }
    }

    fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        signInIntent?.let {
            startActivityForResult(it, RC_SIGN_IN)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>): Result<User> {
        return kotlin.runCatching {
            val account = task.getResult(ApiException::class.java)
            val email = account.email ?: ""
            val authToken = account.idToken ?: ""
            val authId = account.id ?: ""
            val name = account.displayName ?: email
            val avatar = account.photoUrl.toString()
            User(
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