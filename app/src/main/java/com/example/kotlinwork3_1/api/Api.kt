package com.example.kotlinwork3_1.api


import com.example.kotlinwork3_1.dto.PostModel
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


data class AuthRequestParams(val username: String, val password: String)

data class Token(val token: String)

data class RegistrationRequestParams(val username: String, val password: String)

interface Api {

    @POST("api/v1/authentication")
    suspend fun authenticate(@Body authRequestParams: AuthRequestParams): Response<Token>

    @POST("api/v1/registration")
    suspend fun register(@Body registrationRequestParams: RegistrationRequestParams): Response<Token>

    @POST("api/v1/media")
    suspend fun uploadImage(@Part file: MultipartBody.Part): Response<PostModel.AttachmentModel>

    @GET("api/v1/posts/recent")
    suspend fun getPosts(): Response<List<PostModel>>
    @POST("api/v1/posts/{id}/likes")
    suspend fun likedByMe(@Path("id") id: Long): Response<PostModel>
    @DELETE("api/v1/posts/{id}/likes")
    suspend fun cancelMyLike(@Path("id") id: Long): Response<PostModel>
    @POST("api/v1/posts/new")
    suspend fun createPost(@Body createPostRequest: CreatePostRequest): Response<Void>
    @POST("api/v1/repost")
    suspend fun createRepost(@Body createRepostRequest: CreateRepostRequest): Response<Void>
    @POST("api/v1/posts/After")
    suspend fun getPostsAfter(@Body id:Long): Response<List<PostModel>>
    @POST("api/v1/posts/old")
    suspend fun getPostsOld(@Body id:Long): Response<List<PostModel>>
    @POST("api/v1/push")
    suspend fun registerPushToken(@Header("Authorization") token: String, @Body pushRequestParams: PushRequestParams): Response<User>
    @GET("api/v1//posts/{id}")
    suspend fun getPostId(@Path("id") id: Long): Response<PostModel>
}
data class CreatePostRequest(
    val id: Long = 0,
    val postResurse: String,
    val attachment: PostModel.AttachmentModel? = null
)
data class CreateRepostRequest(val id: Long = 0, val postResurse: String, val repostResurs: PostModel)

data class PushRequestParams(val token: String)

data class User(val username: String)