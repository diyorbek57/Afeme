package com.ayizor.afeme.api.main

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class Client {
    companion object{
        private var retrofit: Retrofit? = null
        fun getClient(): Retrofit? {
            if (retrofit == null) {
                val gson = GsonBuilder().setLenient().create()
                val client = OkHttpClient.Builder().addInterceptor(HeaderInterceptor(Api.ACCESS_KEY)).build()
                retrofit = Retrofit.Builder()
                    .baseUrl(Api.BASE_URL).client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            }
            return retrofit
        }
    }

}