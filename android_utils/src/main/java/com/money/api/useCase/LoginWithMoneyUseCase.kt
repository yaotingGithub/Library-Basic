package com.money.api.useCase

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.money.User
import com.money.api.ApiService
import com.money.login.LoginResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.NullPointerException

class LoginWithMoneyUseCase(
    private val moneyApi: ApiService
) {
    suspend operator fun invoke(loginResult: LoginResult): Result<User> {
        return kotlin.runCatching {
            val response = moneyApi.loginWithMoney(
                loginResult.appId,
                loginResult.authToken,
                loginResult.name,
                loginResult.email,
                loginResult.avatar
            )
            val gson = Gson()
            val jsonObject = if (response.isSuccessful) {
                response.body() ?: throw NullPointerException("response.body() return null.")
            } else {
                val errorString = withContext(Dispatchers.IO) {
                    response.errorBody()?.string()
                        ?: throw NullPointerException("response.errorBody() return null")
                }
                gson.fromJson<JsonObject>(
                    errorString,
                    object : TypeToken<JsonObject>() {}.type
                )
            }
            val token = jsonObject.get("id").asString
            val userId = jsonObject.get("userId").toString()

            User(
                name = loginResult.name,
                email = loginResult.email,
                authToken = loginResult.authToken,
                authID = loginResult.authID,
                avatar = loginResult.avatar,
                loginType = loginResult.loginType,
                moneyToken = token,
                moneyUserId = userId
            )
        }
    }
}