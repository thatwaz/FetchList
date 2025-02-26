package com.thatwaz.fetchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thatwaz.fetchlist.data.FetchRepository

class FetchViewModelFactory(private val repository: FetchRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FetchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FetchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
