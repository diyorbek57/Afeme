package com.ayizor.afeme.api.rapid

import com.ayizor.afeme.model.rapidmodels.Currency
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RapidApiInterface {

    //Post Interface
    @GET("currency/convert?")
    fun getSinglePost(
        @Query("format") format: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: String
    ): Call<Currency>
}