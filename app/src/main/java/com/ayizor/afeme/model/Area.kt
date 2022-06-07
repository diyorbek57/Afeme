package com.ayizor.afeme.model

import com.google.gson.annotations.SerializedName

data class Area(
    @SerializedName("id")
    val total_area: String? = "",
    @SerializedName("name")
    val living_area: String? = "",
    @SerializedName("icon")
    val kitchen_area: String? = ""
)
