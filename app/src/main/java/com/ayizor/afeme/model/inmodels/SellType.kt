package com.ayizor.afeme.model.inmodels

import com.google.gson.annotations.SerializedName

data class SellType(
    @SerializedName("id")
    val category_id: Int? = null,
    @SerializedName("name_uz")
    val post_type_name_uz: String? = "",
    @SerializedName("name_ru")
    val post_type_name_ru: String? = "",
    @SerializedName("name_en")
    val post_type_name_en: String? = "",
    @SerializedName("icon")
    val category_icon: String? = "",
    @SerializedName("created_at")
    val category_created_at: String? = "",
    @SerializedName("updated_at")
    val category_updated_at: String? = ""
)
