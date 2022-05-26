package com.ayizor.afeme.api

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeaderInterceptor(private val accessKey: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        var request: Request = chain.request()
        request = request.newBuilder()
            .addHeader("Authorization", "Client-ID $accessKey")
            .build()
        return chain.proceed(request)
    }
}