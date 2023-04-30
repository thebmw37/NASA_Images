package com.nasaImages.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "links") val links: List<Link>?,
    @Json(name = "data") val data: List<Data>?
)