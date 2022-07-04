package com.money.testapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.money.login.BaseGoogleLoginImpl
import com.money.login.BaseGoogleLogin
import com.money.testapplication.databinding.ActivityLoginTestBinding

class LoginTestActivity(
    private val baseGoogleLogin: BaseGoogleLogin = BaseGoogleLoginImpl()
) : AppCompatActivity(), BaseGoogleLogin by baseGoogleLogin {

    private lateinit var binding: ActivityLoginTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        baseGoogleLogin.init(this, "")

        binding.signInWithGoogle.setOnClickListener {
            baseGoogleLogin.signIn(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        baseGoogleLogin.onActivityResult(requestCode, data) {

        }
    }
}