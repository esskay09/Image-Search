package com.terranullius.task.framework.datasource.network.model

import com.squareup.moshi.Json

data class ImageDto(
    @Json(name = "id")
    val id: Int,
    @Json(name = "likes")
    val likes: Int,
    @Json(name= "pageURL")
    val pageUrl: String,
    @Json(name = "previewURL")
    val previewUrl: String,
    @Json(name = "user")
    val artist: String,
    @Json(name = "views")
    val views: Int,
    @Json(name = "webformatURL")
    val webImageUrl: String,
    @Json(name = "largeImageURL")
    val largeImageUrl: String
)