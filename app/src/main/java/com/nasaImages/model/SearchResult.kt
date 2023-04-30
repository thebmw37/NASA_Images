package com.nasaImages.model

import com.squareup.moshi.Json

data class SearchResult(
    @Json(name = "collection") val collection: Any,
)