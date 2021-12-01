package com.example.kotlinwork3_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.kotlinwork3_1.api.App
import com.example.kotlinwork3_1.dto.PostModel
import kotlinx.android.synthetic.main.activity_one_post.*
import kotlinx.coroutines.launch

class OnePostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_post)
        val id = intent.getStringExtra("id")
        lifecycleScope.launch {
            val resp = id?.let { App.repository.getPostId(it.toLong()) }
            val post = resp?.body()
            print(post)
            authorTv.text = post?.author
            contentTv.text = post?.txt
            likesTv.text = post?.like.toString()
            repostsTv.text = post?.share.toString()
            when {
                post?.likeActionPerforming!! -> likeBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                post?.like -> {
                    likeBtn.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    likesTv.setTextColor(
                        ContextCompat.getColor(
                            this@OnePostActivity,
                            R.color.red
                        )
                    )
                }
                else -> {
                    likeBtn.setImageResource(R.drawable.ic_baseline_no_favorite_border_24)
                    likesTv.setTextColor(
                        ContextCompat.getColor(
                            this@OnePostActivity,
                            R.color.grey
                        )
                    )
                }
            }
            when {
                post?.repostActionPerforming!! -> {
                    shareBtn.setImageResource(R.drawable.ic_baseline_share_24)
                }
                post?.share!! -> {
                    shareBtn.setImageResource(R.drawable.ic_baseline_share_24)
                    repostsTv.setTextColor(
                        ContextCompat.getColor(
                            this@OnePostActivity,
                            R.color.grey
                        )
                    )
                }
                else -> {
                    shareBtn.setImageResource(R.drawable.ic_baseline_no_share_24)
                    repostsTv.setTextColor(
                        ContextCompat.getColor(
                            this@OnePostActivity,
                            R.color.grey
                        )
                    )
                }
            }
            when (post.attachment?.mediaType) {
                PostModel.AttachmentType.IMAGE -> loadImage(photoImg, post?.attachment.url)
            }
        }
    }
    private fun loadImage(photoImg: ImageView, imageUrl: String) {
        Glide.with(photoImg.context)
            .load(imageUrl)
            .into(photoImg)
    }

}
