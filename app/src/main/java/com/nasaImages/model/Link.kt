package com.nasaImages.model

import com.squareup.moshi.Json

data class Link(
    @Json(name = "href") val href: String?
)