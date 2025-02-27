package com.thatwaz.fetchlist.data.remote


import com.thatwaz.fetchlist.data.FetchRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: FetchApiService by lazy {
        retrofit.create(FetchApiService::class.java)
    }

    val repository: FetchRepository by lazy {
        FetchRepository(apiService)
    }
}
