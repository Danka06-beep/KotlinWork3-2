package com.example.kotlinwork3_1.api

import android.app.Application
import android.content.Context
import com.example.kotlinwork3_1.API_SHARED_FILE
import com.example.kotlinwork3_1.AUTHENTICATED_SHARED_KEY
import com.example.kotlinwork3_1.InjectAuthTokenInterceptor
import com.example.kotlinwork3_1.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    companion object {
        lateinit var repository: Repository
            private set
    }
    override fun onCreate() {
        super.onCreate()

        val httpLoggerInterceptor = HttpLoggingInterceptor()

        httpLoggerInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(InjectAuthTokenInterceptor {
                getSharedPreferences(API_SHARED_FILE, MODE_PRIVATE).getString(
                    AUTHENTICATED_SHARED_KEY, null
                )
            })
            .addInterceptor(httpLoggerInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .client(client).
            addConverterFactory(GsonConverterFactory.create())
            .build()




    }
}