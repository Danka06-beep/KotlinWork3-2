package com.example.kotlinwork3_1

import android.util.Log
import com.example.kotlinwork3_1.api.Api
import com.example.kotlinwork3_1.api.AuthRequestParams
import com.example.kotlinwork3_1.api.RegistrationRequestParams
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {

    private val clinet: OkHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor({
                Log.d("MyLog", "$it")
            }).apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        ).build()
    }


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080").addConverterFactory(GsonConverterFactory.create())
            .client(clinet).build()
    }

    private val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
    suspend fun authenticate(login: String, password: String) = api.authenticate(AuthRequestParams(login, password))

    suspend fun register(login: String, password: String) =
        api.register(RegistrationRequestParams(login, password))
}