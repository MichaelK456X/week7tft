package com.example.pokemonmusicapp.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val pokemonApiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestUrl = originalRequest.url.toString()

        // Jika request menuju ke Pokemon Price Tracker, suntikkan API Key ke Header
        val newRequest = if (requestUrl.contains("pokemonpricetracker.com")) {
            originalRequest.newBuilder()
                .header("X-Api-Key", pokemonApiKey) // Wajib mencantumkan API Key sesuai Soal 1
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(newRequest)
    }
}
