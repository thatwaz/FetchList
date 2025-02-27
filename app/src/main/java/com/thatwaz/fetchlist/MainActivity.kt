package com.thatwaz.fetchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.thatwaz.fetchlist.data.remote.NetworkModule
import com.thatwaz.fetchlist.ui.screens.FetchScreen
import com.thatwaz.fetchlist.ui.theme.FetchListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FetchListTheme {
                FetchScreen(NetworkModule.repository) // Use the singleton repository
            }
        }
    }
}


