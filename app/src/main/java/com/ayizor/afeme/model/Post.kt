package com.ayizor.afeme.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id")
    val post_id: Int? = null,
    @SerializedName("images")
    val post_images: ArrayList<Image>? = null,
    @SerializedName("user_id")
    val user_id: String? = "",
    @SerializedName("videos")
    val post_videos: ArrayList<Video>? = null,
    @SerializedName("htype_id")
    val post_building_type: String? = null,
    @SerializedName("sale_id")
    val post_type: Int? = null,
    @SerializedName("latitude")
    val post_latitude: String? = "",
    @SerializedName("longitude")
    val post_longitude: String? = "",
    @SerializedName("price_som")
    val post_price_uzs: String? = null,
    @SerializedName("price_usd")
    val post_price_usd: String? = null,
    @SerializedName("area")
    val post_area: Area? = null,
    @SerializedName("date")
    val post_built_year: String? = "",
    @SerializedName("room")
    val post_rooms: String? = "",
    @SerializedName("repair_id")
    val post_repair: String? = "",
    @SerializedName("documents")
    val post_documents: String? = null,
    @SerializedName("description")
    val post_description: String? = "",
    @SerializedName("material_id")
    val post_material: Material? = null,
    @SerializedName("region_id")
    val post_region_id: String? = null,
    @SerializedName("city_id")
    val post_city_id: String? = null,
    @SerializedName("street")
    val post_street: String? = "",
    @SerializedName("house")
    val post_house_number: String? = "",
    @SerializedName("floor")
    val post_floor: String? = "",
    @SerializedName("flat")
    val post_flat: String? = "",
    @SerializedName("like")
    val post_liked: Boolean? = null,
    @SerializedName("created_at")
    val post_created_at: String? = "",
    @SerializedName("updated_at")
    val post_updated_at: String? = ""

)
