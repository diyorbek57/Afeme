package com.ayizor.afeme.api.rapid

import com.ayizor.afeme.api.main.Api
import com.ayizor.afeme.api.main.HeaderInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RapidClient {
    companion object {
        private var retrofit: Retrofit? = null
        fun getClient(): Retrofit? {
            if (retrofit == null) {
                val client = OkHttpClient.Builder()
                    .addInterceptor(RapidHeaderInterceptor(RapidApi.ACCESS_KEY)).build()
                retrofit = Retrofit.Builder()
                    .baseUrl(RapidApi.BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }

}