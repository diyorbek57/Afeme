package com.ayizor.afeme.api

import com.ayizor.afeme.model.response.CategoryResponse
import com.ayizor.afeme.model.Post
import com.ayizor.afeme.model.User
import com.ayizor.afeme.model.response.UserResponse
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    //Post Interface
    @GET("post/{id}")
    fun getSinglePost(@Path("id") id: String): Call<Post>

    @GET("post")
    fun getAllPosts(): Call<List<Post>>

    @POST("post")
    fun createPost(@Body post: Post): Call<Post>

    @PUT("post/{id}")
    fun updatePost(@Path("id") id: String): Call<List<Post>>

    @DELETE("post/{id}")
    fun deletePost(@Path("id") id: String): Call<List<Post>>

    //
    //auth
    @POST("register")
    fun register(@Body user: User): Call<User>

    @POST("login")
    fun login(): Call<User>

    @POST("logout")
    fun logout(): Call<User>

    //
    //user
    @GET("user")
    fun getAllUsers(): Call<UserResponse>

    @GET("user/{id}")
    fun getSingleUser(@Path("id") id: Int): Call<UserResponse>

    @PUT("user/{id}")
    fun updateSingleUser(@Path("id") id: String): Call<UserResponse>

    @DELETE("user/{id}")
    fun deleteSingleUser(@Path("id") id: String): Call<UserResponse>
    //
    //categores
    @GET("htype")
    fun getAllCategory(): Call<CategoryResponse>

}