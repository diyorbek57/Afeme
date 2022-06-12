package com.ayizor.afeme.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("images")
    val post_images: ArrayList<Image>? = null,
    @SerializedName("video")
    val post_videos: ArrayList<Video>? = null,
    @SerializedName("documents")
    val post_documents: ArrayList<Document>? = null,
    //null
    @SerializedName("id")
    val post_id: String? = "",
    //null
    @SerializedName("user_id")
    val user_id: String? = "",
    //null
    @SerializedName("created_at")
    val post_created_at: String? = "",
    //null
//    @SerializedName("")
//    val post_views: Int? = null,
    @SerializedName("price_som")
    val post_price_uzs: String? = null,
    @SerializedName("price_usd")
    val post_price_usd: String? = null,
    @SerializedName("sale_id")
    val post_type: Int? = null,
    @SerializedName("htype_id")
    val post_building_type: String? = null,
    @SerializedName("material_id")
    val post_material: Int? = null,
    @SerializedName("date")
    val post_built_year: String? = "",
    @SerializedName("latitude")
    val post_latitude: String? = "",
    @SerializedName("longitude")
    val post_longitude: String? = "",
    @SerializedName("region_id")
    val post_region_id: Int? = null,
    @SerializedName("city_id")
    val post_city_id: Int? = null,
    @SerializedName("street")
    val post_street: String? = "",
    @SerializedName("room")
    val post_rooms: String? = "",
    @SerializedName("house")
    val post_house_number: String? = "",
    @SerializedName("floor")
    val post_floor: String? = "",
    @SerializedName("flat")
    val post_flat: String? = "",
    @SerializedName("area")
    val post_area: Area? = null,
    //null
//    @SerializedName("")
//    val post_rating: Double? = null,
    @SerializedName("like")
    val post_liked: Boolean? = null
)
