package com.ayizor.afeme.api

import com.ayizor.afeme.model.Post
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("post/{id}")
    fun getSinglePost(
        @Path("id") id: String
    ): Call<Post>

    @GET("posts")
    fun getPosts(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int?,
        @Query("order_by") orderBy: String
    ): Call<List<Post>>

    @GET("posts/curated")
    fun getCuratedPosts(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("order_by") orderBy: String
    ): Call<List<Post>>

    @GET("post/random")
    fun getRandomPost(
        @Query("collections") collections: String,
        @Query("featured") featured: Boolean,
        @Query("username") username: String,
        @Query("query") query: String,
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("orientation") orientation: String
    ): Call<Post>

    @GET("posts/random")
    fun getRandomPhotos(
        @Query("collections") collections: String,
        @Query("featured") featured: Boolean,
        @Query("username") username: String,
        @Query("query") query: String,
        @Query("w") width: Int,
        @Query("h") height: Int,
        @Query("orientation") orientation: String,
        @Query("count") count: Int
    ): Call<List<Post>>

//    @GET("photos/{id}/download")
//    fun getPhotoDownloadLink(@Path("id") id: String): Call<Download>

//    @GET("search/photos")
//    fun searchPhotos(
//        @Query("query") query: String,
//        @Query("page") page: Int,
//        @Query("per_page") perPage: Int,
//        @Query("orientation") orientation: String
//    ): Call<SearchResults>

//    @GET("topics")
//    fun getTopics(
//        @Query("page") page: Int,
//        @Query("per_page") perPage: Int?,
//        @Query("order_by") orderBy: String
//    ): Call<List<Topic>>

//    @GET("topics/{id}/photos")
//    fun getTopictPhotos(
//        @Path("id") id: String?,
//        @Query("page") page: Int,
//        @Query("per_page") perPage: Int?,
//        @Query("order_by") orderBy: String
//    ): Call<List<Topic>>

//    @GET("photos/{id}/related")
//    fun getRelatedPhotos(
//        @Path("id") id: String?,
//        @Query("page") page: Int,
//        @Query("per_page") perPage: Int?
//    ): Call<RelatedPhotos>

//    @GET("search/photos")
//    fun getSearchPhoto(
//        @Query("page") page: Int,
//        @Query("query") query: String,
//        @Query("per_page") perPage: Int
//    ): Call<Explore>
//
//
//    @GET("search/users")
//    fun getSearchProfile(
//        @Query("page") page: Int,
//        @Query("query") query: String,
//        @Query("per_page") perPage: Int
//    ): Call<ResultProfiles>

}