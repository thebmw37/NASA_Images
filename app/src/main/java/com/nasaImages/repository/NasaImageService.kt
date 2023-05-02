package com.nasaImages.repository

import com.nasaImages.model.SearchResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_ROOT = "https://images-api.nasa.gov"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(API_ROOT)
    .build()

interface NasaApiService {
    @GET("/search")
    suspend fun getData(
        @Query("q") searchQuery: String,
        @Query("page") page: String,
        @Query("media_type") mediaType: String = "image"): SearchResult
}

object NasaApi {
    val retrofitService: NasaApiService by lazy { retrofit.create(NasaApiService::class.java) }
}