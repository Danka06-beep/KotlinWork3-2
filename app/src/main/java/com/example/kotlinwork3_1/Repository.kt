package com.example.kotlinwork3_1

import android.graphics.Bitmap
import android.util.Log
import com.example.kotlinwork3_1.api.*
import com.example.kotlinwork3_1.dto.PostModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface Repository {

    suspend fun getPosts(): Response<List<PostModel>>
    suspend fun likedByMe(id: Long): Response<PostModel>
    suspend fun cancelMyLike(id: Long): Response<PostModel>
    suspend fun createPost(content: String, attachmentModel: PostModel.AttachmentModel?):Response<Void>
    suspend fun createRepost(content: String, contentRepost:PostModel): Response<Void>
    suspend fun getPostsAfter(id: Long):Response<List<PostModel>>
    suspend fun getPostsOld(id: Long):Response<List<PostModel>>
    suspend fun upload(bitmap: Bitmap): Response<PostModel.AttachmentModel>
    suspend fun registerPushToken(token: String) : Response<User>
    suspend fun getPostId(id: Long): Response<PostModel>

    suspend fun authenticate(login: String, password: String, tokenDevice: String):Response<Token>

    suspend fun register(login: String, password: String):Response<Token>
}