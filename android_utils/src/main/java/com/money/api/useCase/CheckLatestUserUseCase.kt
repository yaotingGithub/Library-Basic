package com.money.api.useCase

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.money.api.BaseApiService
import com.money.api.state.ApiState
import com.money.api.state.CheckLatestUserState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.NullPointerException
import java.net.UnknownHostException

class CheckLatestUserUseCase(
    private val moneyApi: BaseApiService
) {
    private val tag = this::class.java.simpleName

    suspend operator fun invoke(agent: String): ApiState {
        try {
            val response = moneyApi.isLatestUser(agent)
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

            return when (val code = jsonObject.get("statusCode").asInt) {
                200 -> {
                    if (jsonObject.get("status").asInt == 1) {
                        CheckLatestUserState.IsLatest
                    } else {
                        CheckLatestUserState.IsNotLatest
                    }
                }
                401 -> { // {"statusCode":401,"message":"access token not found","data":""}
                    CheckLatestUserState.TokenNotFound
                }
                else -> {
                    ApiState.OnError(IllegalStateException("$tag -> unknown statusCode: $code"))
                }
            }
        } catch (exception: Exception) {
            return if (exception is UnknownHostException) {
                ApiState.OnNetworkDisconnected()
            } else {
                ApiState.OnError(IllegalStateException("$tag -> occur exception: $exception"))
            }
        }
    }
}