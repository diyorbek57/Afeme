package com.ayizor.afeme.model.response

import com.ayizor.afeme.model.Post
import com.ayizor.afeme.model.SellType

data class PostResponse(
    val status: Boolean? = null,
    val message: String? = "",
    val data: Post? = null
)