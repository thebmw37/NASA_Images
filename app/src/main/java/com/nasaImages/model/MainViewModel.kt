package com.nasaImages.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasaImages.repository.NasaApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

enum class ApiStatus { IN_PROGRESS, COMPLETE, ERROR, NONE }

class MainViewModel : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    private val _apiStatus = MutableStateFlow(ApiStatus.NONE)
    val apiStatus: StateFlow<ApiStatus> = _apiStatus

    private var searchResult: SearchResult? = null

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun performSearch() {
        _apiStatus.value = ApiStatus.IN_PROGRESS

        viewModelScope.launch {
            try {
                searchResult = NasaApi.retrofitService.getData(searchQuery.value)
                _apiStatus.value = ApiStatus.COMPLETE
            } catch (e: Exception) {
                _apiStatus.value = ApiStatus.ERROR
            }
        }
    }
}