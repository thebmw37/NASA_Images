package com.nasaImages.model

import androidx.lifecycle.ViewModel
import com.nasaImages.repository.NasaImagesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class MainViewModel(
        private val repository: NasaImagesRepository,
        private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery
    private val _searchResult: MutableStateFlow<SearchResult?> = MutableStateFlow(null)
    val searchResult: StateFlow<SearchResult?> = _searchResult
    private val _infoTextVisible = MutableStateFlow(false)
    val infoTextVisible: StateFlow<Boolean> = _infoTextVisible
    private val _progressIndicatorVisible = MutableStateFlow(false)
    val progressIndicatorVisible: StateFlow<Boolean> = _progressIndicatorVisible
    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> = _currentPage
    private val _navigationVisible = MutableStateFlow(false)
    val navigationVisible: StateFlow<Boolean> = _navigationVisible

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
    val errorModalVisible: StateFlow<Boolean> = _errorModalVisible
    private val _errorCode = MutableStateFlow("")
    val errorCode: StateFlow<String> = _errorCode

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun performSearch(pageNumber: Int) {
        if (pageNumber < 1 || pageNumber > 100) return

        _infoTextVisible.value = false
        _searchResult.value = null
        toggleProgressIndicatorVisibility()

        CoroutineScope(coroutineDispatcher).launch {
            repository.search(searchQuery.value, pageNumber.toString())
                .catch {
                    _errorCode.value = it.toString()
                    _navigationVisible.value = false
                    toggleErrorModalVisibility()
                    toggleProgressIndicatorVisibility()
                }
                .collect {
                    _searchResult.value = it
                    _currentPage.value = pageNumber

                    if (searchResult.value?.collection?.items?.isEmpty() == true) {
                        _infoTextVisible.value = true
                        _navigationVisible.value = false
                    } else {
                        _navigationVisible.value = true
                    }

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

    private fun toggleProgressIndicatorVisibility() {
        _progressIndicatorVisible.value = !_progressIndicatorVisible.value
    }

    private fun toggleErrorModalVisibility() {
        _errorModalVisible.value = !_errorModalVisible.value
    }
}