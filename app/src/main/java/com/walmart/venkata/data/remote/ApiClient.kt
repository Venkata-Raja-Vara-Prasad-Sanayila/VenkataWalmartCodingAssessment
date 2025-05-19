package com.walmart.venkata.data.remote

import com.walmart.venkata.data.common.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object to provide the Retrofit client for API calls.
 */
object ApiClient {

    val retrofit: Retrofit by lazy {
        // Create an HTTP logging interceptor to log request and response bodies
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Build OkHttpClient and attach the logging interceptor
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        // Build and return the Retrofit instance
        Retrofit.Builder().apply {
            baseUrl(Constants.BASE_URL_COUNTRIES)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }
}