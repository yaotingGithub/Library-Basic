package com.money.testapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.money.android_utils.LoginActivity
import com.money.android_utils.User
import com.money.testapplication.databinding.ActivityLoginTestBinding

class LoginTestActivity(override val serverClientId: String) : LoginActivity() {

    private lateinit var binding: ActivityLoginTestBinding

    override fun getLayoutView(): View {
        binding = ActivityLoginTestBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initView() {
        binding.signInWithGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }

    override fun handleGoogleSignInResult(result: Result<User>) {
        TODO("Not yet implemented")
    }
}