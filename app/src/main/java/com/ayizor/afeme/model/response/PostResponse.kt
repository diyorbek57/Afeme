package com.ayizor.afeme.model.response

import com.ayizor.afeme.model.post.GetPost
import com.ayizor.afeme.model.post.Post

data class PostResponse(
    val status: Boolean? = null,
    val message: String? = "",
    val data: GetPost? = null
)