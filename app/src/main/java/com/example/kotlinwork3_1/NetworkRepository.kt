package com.example.kotlinwork3_1

import android.graphics.Bitmap
import com.example.kotlinwork3_1.api.*
import com.example.kotlinwork3_1.dto.PostModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.ByteArrayOutputStream

class NetworkRepository(private val api: Api): Repository {
    private var token: String? = null
    override suspend fun authenticate(login: String, password: String): Response<Token> {
        token = api.authenticate(AuthRequestParams(username = login, password = password)).body()?.token
        return api.authenticate(AuthRequestParams(username = login, password = password))
    }

    override suspend fun register(login: String, password: String): Response<Token> =
        api.register(RegistrationRequestParams(username = login, password = password))

    override suspend fun getPosts(): Response<List<PostModel>> =
        api.getPosts()

    override suspend fun likedByMe(id: Long): Response<PostModel> =
        api.likedByMe(id)

    override suspend fun cancelMyLike(id: Long): Response<PostModel> =
        api.cancelMyLike(id)

    override suspend fun createPost(content: String, attachmentModel: PostModel.AttachmentModel?): Response<Void> =
        api.createPost(CreatePostRequest(txt = content, attachment = attachmentModel))

    override suspend fun createRepost(content: String, contentRepost: PostModel): Response<Void> =
        api.createRepost(CreateRepostRequest(txt = content, repostTxt = contentRepost,id = contentRepost.id))

    override suspend fun getPostsAfter(id: Long): Response<List<PostModel>> =
        api.getPostsAfter(id)

    override suspend fun getPostsOld(id: Long): Response<List<PostModel>> =
        api.getPostsOld(id)

    override suspend fun upload(bitmap: Bitmap): Response<PostModel.AttachmentModel> {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val reqFIle =
            RequestBody.create("image/jpeg".toMediaTypeOrNull(), bos.toByteArray())
        val body =
            MultipartBody.Part.createFormData("file", "image.jpg", reqFIle)
        return api.uploadImage(body)
    }

    override suspend fun registerPushToken(token: String): Response<User> = api.registerPushToken(this.token!!, PushRequestParams(token))
    override suspend fun getPostId(id: Long): Response<PostModel> =  api.getPostId(id)
}