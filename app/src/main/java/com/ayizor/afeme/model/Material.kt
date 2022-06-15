package com.ayizor.afeme.model

import com.google.gson.annotations.SerializedName

data class Material(
    @SerializedName("id")
    val material_id: Int? = null,
    @SerializedName("name")
    val material_name: String? = "",
    @SerializedName("created_at")
    val material_created_at: String? = "",
    @SerializedName("updated_at")
    val material_updated_at: String? = ""
)