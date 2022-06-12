package com.ayizor.afeme.model.response

import com.ayizor.afeme.model.Post

data class GetPostResponse(
    val status: Boolean? = null,
    val message: String? = "",
    val data: ArrayList<Post>? = null
)