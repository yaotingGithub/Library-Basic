package com.money.testapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.money.android_utils.log
import com.money.api.AppClientManager
import com.money.biometric.BiometricAuthHelper
import com.money.testapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (BiometricAuthHelper.isBiometricEnabled(this)) {
            binding.btBiometric.alpha = 1f
        } else {
            binding.btBiometric.alpha = 0.5f
        }

        binding.btBiometric.setOnClickListener {
            lifecycleScope.launch {
                BiometricAuthHelper.authenticate(
                    this@MainActivity,
                    "Biometric login for my app",
                    "Log in using your biometric credential",
                    "Use account password").collect {
                        log("result", it)
                }
            }
        }
    }
}