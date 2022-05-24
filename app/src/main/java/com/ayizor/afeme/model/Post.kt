package com.ayizor.afeme.model

data class Post(
    val post_images: Int? = null,
    val post_name: String? = "",
    val post_id: String? = "",
    val user_id: String? = "",
    val post_created_at: String? = "",
    val post_views: Int? = null,
    val post_price: String? = null,
    val post_type: String? = "",
    val post_building_type: String? = "",
    val post_period: String? = "",
    val post_latitude: String? = "",
    val post_longitude: String? = "",
    val post_location: String? = "",
    val post_rating: Double? = null,
    val model_type: Int? = null
)
