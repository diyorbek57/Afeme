package com.ayizor.afeme.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {
    companion object{
        private var retrofit: Retrofit? = null
        fun getClient(): Retrofit? {
            if (retrofit == null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(HeaderInterceptor(Api.ACCESS_KEY)).build()
                retrofit = Retrofit.Builder()
                    .baseUrl(Api.BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }

}