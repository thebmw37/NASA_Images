package com.nasaImages.repository

import com.nasaImages.model.*
import com.nasaImages.model.Collection
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

    var valuteToEmit: SearchResult? = SearchResult(Collection(listOf()))

    fun setNormalSearchResult() {
        valuteToEmit =
            SearchResult(
                Collection(
                    listOf(
                        Item(listOf(Link("href")),
                             listOf(Data("title", "description", "dateCreated"))
                        )
                    )
                )
            )
    }

    fun setEmptySearchResult() {
        valuteToEmit = SearchResult(Collection(listOf()))
    }

    fun setErrorResult() {
        valuteToEmit = null
    }

    override fun search(query: String, pageNumber: String) = flow {
        emit(valuteToEmit ?: throw Exception(""))
    }
}