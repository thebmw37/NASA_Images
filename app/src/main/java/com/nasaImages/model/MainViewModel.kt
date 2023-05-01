package com.nasaImages.model

import android.media.Image
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
    private val _searchResult: MutableStateFlow<SearchResult?> = MutableStateFlow(null)
    val searchResult: StateFlow<SearchResult?> = _searchResult
    private val _progressIndicatorVisible = MutableStateFlow(false)
    val progressIndicatorVisible: StateFlow<Boolean> = _progressIndicatorVisible
    private val _imageInfoVisible = MutableStateFlow(false)
    val imageInfoVisible: StateFlow<Boolean> = _imageInfoVisible

    private val _image = MutableStateFlow("")
    val image: StateFlow<String> = _image
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title
    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description
    private val _date = MutableStateFlow("")
    val date: StateFlow<String> = _date

    private val _errorModalVisible = MutableStateFlow(false)
    val errorModalVisible = _errorModalVisible
    private val _errorCode = MutableStateFlow("")
    val errorCode: StateFlow<String> = _errorCode

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun performSearch() {
        _apiStatus.value = ApiStatus.IN_PROGRESS
        toggleProgressIndicatorVisibility()

        viewModelScope.launch {
            try {
                _searchResult.value = NasaApi.retrofitService.getData(searchQuery.value)
                _apiStatus.value = ApiStatus.COMPLETE
                toggleProgressIndicatorVisibility()
            } catch (e: Exception) {
                _apiStatus.value = ApiStatus.ERROR
                _errorCode.value = e.toString()
                toggleErrorModalVisibility()
                toggleProgressIndicatorVisibility()
            }
        }
    }

    fun updateImageInfo(imageHref: String, title: String, description: String, date: String) {
        _image.value = imageHref
        _title.value = title
        _description.value = description
        _date.value = date
    }

    fun setImageInfoVisible(isVisible: Boolean) {
        _imageInfoVisible.value = isVisible
    }

    fun setErrorModalVisible(isVisible: Boolean) {
        _errorModalVisible.value = isVisible
    }

    fun setErrorCode(code: String) {
        _errorCode.value = code
    }

    private fun toggleProgressIndicatorVisibility() {
        _progressIndicatorVisible.value = !_progressIndicatorVisible.value
    }

    private fun toggleErrorModalVisibility() {
        _errorModalVisible.value = !_errorModalVisible.value
    }
}