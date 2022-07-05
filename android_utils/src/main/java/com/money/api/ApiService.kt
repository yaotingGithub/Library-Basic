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
}