package com.thatwaz.fetchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.thatwaz.fetchlist.data.FetchRepository
import com.thatwaz.fetchlist.data.remote.FetchApiService
import com.thatwaz.fetchlist.ui.screens.FetchScreen
import com.thatwaz.fetchlist.ui.theme.FetchListTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setup Retrofit API Service BEFORE calling setContent
        val apiService = Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FetchApiService::class.java)

        // Create Repository
        val repository = FetchRepository(apiService)

        // Now call setContent ONCE with FetchListTheme
        setContent {
            FetchListTheme {
                FetchScreen(repository)
            }
        }
    }
}

