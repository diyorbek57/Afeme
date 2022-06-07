package com.ayizor.afeme.model.response

import com.ayizor.afeme.model.Category
import com.ayizor.afeme.model.SellType

data class SellResponse(
    val status: Boolean? = null,
    val message: String? = "",
    val data: ArrayList<SellType>? = null
)
