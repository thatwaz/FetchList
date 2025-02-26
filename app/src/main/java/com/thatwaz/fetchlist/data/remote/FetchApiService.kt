package com.thatwaz.fetchlist.data.remote

import com.thatwaz.fetchlist.data.model.FetchItem
import retrofit2.http.GET

interface FetchApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<FetchItem>
}
