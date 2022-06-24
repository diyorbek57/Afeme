package com.ayizor.afeme.api.main

import android.content.Context
import com.ayizor.afeme.manager.PrefsManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class Client {
    companion object {
        private var retrofit: Retrofit? = null

        fun getClient(context: Context): Retrofit? {
            if (retrofit == null) {
                val client = OkHttpClient.Builder().addInterceptor(
                    HeaderInterceptor(PrefsManager(context).loadUserRegisteredToken().toString())).build()
                retrofit = Retrofit.Builder()
                    .baseUrl(Api.BASE_URL).client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }


    }

}