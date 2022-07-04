package com.money.testapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.money.login.*
import com.money.testapplication.databinding.ActivityLoginTestBinding

class LoginTestActivity(
    private val baseGoogleLogin: BaseGoogleLogin = BaseGoogleLoginImpl(),
    private val baseFacebookLogin: BaseFacebookLogin = BaseFacebookLoginImpl()
) : AppCompatActivity(), BaseGoogleLogin by baseGoogleLogin, BaseFacebookLogin by baseFacebookLogin {

    private lateinit var binding: ActivityLoginTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        baseGoogleLogin.init(this, "")
        baseFacebookLogin.init()

        binding.signInWithGoogle.setOnClickListener {
            baseGoogleLogin.loginWithGoogle(this)
        }
        binding.signInWithFacebook.setOnClickListener {
            baseFacebookLogin.loginWithFacebook(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        baseGoogleLogin.onActivityResult(requestCode, data)
        baseFacebookLogin.onActivityResult(requestCode, resultCode, data)
    }

    override fun doOnGoogleLoginFinish(state: LoginState) {
        super.doOnGoogleLoginFinish(state)
    }

    override fun doOnFacebookLoginFinish(state: LoginState) {
        super.doOnFacebookLoginFinish(state)
    }
}