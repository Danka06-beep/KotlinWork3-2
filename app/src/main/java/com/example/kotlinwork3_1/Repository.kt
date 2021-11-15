package com.example.kotlinwork3_1

import com.example.kotlinwork3_1.api.Api
import com.example.kotlinwork3_1.api.AuthRequestParams
import com.example.kotlinwork3_1.api.RegistrationRequestParams
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://127.6.0.8:8080").addConverterFactory(GsonConverterFactory.create()).build()
    }

    private val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
    suspend fun authenticate(login: String, password: String) = api.authenticate(AuthRequestParams(login, password))

    suspend fun register(login: String, password: String) =
        api.register(RegistrationRequestParams(login, password))
}