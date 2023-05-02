package com.nasaImages.repository

import com.nasaImages.model.Collection
import com.nasaImages.model.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface NasaImagesRepository {
    fun search(query: String, pageNumber: String): Flow<SearchResult?>
}

class NasaImagesRepositoryImpl : NasaImagesRepository {
    override fun search(query: String, pageNumber: String) = flow {
        var resultToEmit: SearchResult? = null
        var errorToEmit = ""

        try {
            resultToEmit = NasaApi.retrofitService.getData(query, pageNumber)
        } catch (e: Exception) {
            errorToEmit = e.toString()
        }

        emit(resultToEmit ?: throw Exception(errorToEmit))
    }
}

class MockNasaImagesRepository: NasaImagesRepository {
    override fun search(query: String, pageNumber: String) = flow {
        emit(SearchResult(Collection(listOf())))
    }
}