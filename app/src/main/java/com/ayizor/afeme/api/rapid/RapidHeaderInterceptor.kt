package com.ayizor.afeme.api.rapid

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RapidHeaderInterceptor(private val accessKey: String) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        request = request.newBuilder()
            .addHeader("X-RapidAPI-Key", accessKey)
            .build()
        return chain.proceed(request)
    }
}