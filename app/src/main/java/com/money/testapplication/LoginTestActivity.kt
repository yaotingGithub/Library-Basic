package com.money.testapplication

import android.view.View
import com.money.android_utils.BaseLoginActivity
import com.money.android_utils.User
import com.money.testapplication.databinding.ActivityLoginTestBinding

class LoginTestActivity(override val serverClientId: String) : BaseLoginActivity() {

    private lateinit var binding: ActivityLoginTestBinding

    override fun getLayoutView(): View {
        binding = ActivityLoginTestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun init() {
        binding.signInWithGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }

    override fun handleGoogleSignInResult(result: Result<User>) {
        TODO("Not yet implemented")
    }
}