package com.nasaImages.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Collection(
    @Json(name = "items") val items: List<Item>
)