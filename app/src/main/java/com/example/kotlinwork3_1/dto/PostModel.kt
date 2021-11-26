package com.example.kotlinwork3_1.dto

data class PostModel(val id: Long = 0,
                     val author: String? = null,
                     val data: Long = 0,
                     val txt: String? = null,
                     var like: Boolean = false,
                     val comment: Boolean = false,
                     val share: Boolean = false,
                     var likeTxt: Int = 0,
                     val commentTxt: Int = 0,
                     val shareTxt: Int = 0,
                     val adress: String? = null,
                     val coordinates: Pair<Double, Double>? = null,
                     val type: PostTypes = PostTypes.REPOST,
                     val url: String? = null,
                     val dateRepost: Long? = null,
                     val autorRepost: String? = null,
                     var hidePost: Boolean = false,
                     var viewPost: Long = 0
) {
}
enum class PostTypes {
    POST, REPOST
}
enum class AttachmentType {
    IMAGE, AUDIO, VIDEO
}
data class AttachmentModel(
    val id: String,
    val url: String,
    val type: AttachmentType
)