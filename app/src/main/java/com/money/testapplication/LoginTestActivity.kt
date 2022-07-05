package com.money.testapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.money.login.facebook.BaseFacebookLogin
import com.money.login.facebook.BaseFacebookLoginImpl
import com.money.login.google.BaseGoogleLogin
import com.money.login.google.BaseGoogleLoginImpl
import com.money.testapplication.databinding.ActivityLoginTestBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
        lifecycleScope.launch {
            baseGoogleLogin.loginGoogleFlow.collect {
            }
            baseFacebookLogin.loginFacebookFlow.collect {
            }
        }

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
}