package com.nasaImages.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Link(
    @Json(name = "href") val href: String?
)