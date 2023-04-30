package com.nasaImages.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "title") val title: String?,
    @Json(name = "description") val description: String?,
    @Json(name = "date_created") val dateCreated: String?
)