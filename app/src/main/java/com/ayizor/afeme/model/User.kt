package com.ayizor.afeme.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val user_id: String? = "",
    @SerializedName("name")
    val user_name: String? = "",
    @SerializedName("lastname")
    val user_last_name: String? = "",
    @SerializedName("image")
    val user_photo: String? = "",
    @SerializedName("email")
    val user_email: String? = "",
    @SerializedName("phone")
    val user_phone_number: String? = "",
    @SerializedName("pasport")
    val user_passport_number: String? = "",
    @SerializedName("user_type")
    val user_type: String? = "",
    @SerializedName("devays_id")
    val user_device_id: String? = "",
    @SerializedName("devays_type")
    val user_device_type: String? = "",
    @SerializedName("password")
    val user_password: String? = "",
//    @SerializedName("region_id")
//    val user_region: String? = "",
    @SerializedName("longitude")
    val user_longitude: String? = "",
    @SerializedName("latitude")
    val user_latitude: String? = "",
    @SerializedName("posts")
    val user_posts: ArrayList<Post>? = null,
    @SerializedName("favorites")
    val user_favorites: ArrayList<Post>? = null,
    @SerializedName("created_at")
    val user_created_at: String? = "",
    @SerializedName("updated_at")
    val user_updated_at: String? = "",
)
