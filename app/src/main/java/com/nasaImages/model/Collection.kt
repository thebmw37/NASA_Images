package com.nasaImages.model

import com.squareup.moshi.Json

data class Collection(
    @Json(name = "items") val items: List<Item>
)