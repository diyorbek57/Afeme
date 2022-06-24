package com.ayizor.afeme.model.response

import com.ayizor.afeme.model.post.Post

data class PostResponse(
    val status: Boolean? = null,
    val message: String? = "",
    val data: Post? = null
)