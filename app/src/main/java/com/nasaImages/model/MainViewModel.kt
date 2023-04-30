package com.nasaImages.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasaImages.repository.NasaApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun performSearch() {
        viewModelScope.launch {
            try {
                val searchResult = NasaApi.retrofitService.getData(searchQuery.value)
                println(searchResult)
            } catch (e: Exception) {
                println(e)
            }
        }
    }
}