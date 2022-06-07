package com.ayizor.afeme.model

import com.google.gson.annotations.SerializedName

data class SellType(
    @SerializedName("id")
    val category_id: Int? = null,
    @SerializedName("name")
    val category_name: String? = "",
    @SerializedName("icon")
    val category_icon: String? = "",
    @SerializedName("created_at")
    val category_created_at: String? = "",
    @SerializedName("updated_at")
    val category_updated_at: String? = ""
)
