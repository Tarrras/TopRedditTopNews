package com.testapp.topredditnews.data.network

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.testapp.topredditnews.data.response.Response
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://www.reddit.com/"

interface RedditApiService {
    @GET("top.json")
    suspend fun getTopPosts(
        @Query("count") count: Int = 25,
        @Query("after") after: String = ""
    ): Response

    companion object {
        operator fun invoke(): RedditApiService {
            val logInterceptor= HttpLoggingInterceptor()
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(logInterceptor)
                .build()

            val gsonBuilder = GsonBuilder().create()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                .build()
                .create(RedditApiService::class.java)
        }
    }
}