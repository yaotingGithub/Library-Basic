package com.money.api

import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("a/auth")
    suspend fun loginWithMoney(
        @Field("app_id") appId: String,
        @Field("auth_token") authToken: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("avatar") avatar: String
    ): Response<JsonObject>

    /**
     * 請求驗證碼
     */
    @FormUrlEncoded
    @POST("/smoney/ask-verify-code")
    suspend fun askVerifyCode(
        @Field("agent") agent: String,
        @Field("os") os: String
    ): Response<JsonObject>

    /**
     * 檢查驗證碼
     */
    @FormUrlEncoded
    @POST("/smoney/check-verify-code")
    suspend fun checkVerifyCode(
        @Field("agent") agent: String,
        @Field("verify_code") verify_code: String
    ): Response<JsonObject>
}