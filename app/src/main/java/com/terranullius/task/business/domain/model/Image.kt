package com.terranullius.task.business.domain.model

import com.squareup.moshi.Json

/**
* Domain model
* */
data class Image(
    val id: Int,
    val likes: Int,
    val pageUrl: String,
    val previewUrl: String,
    val artist: String,
    val views: Int,
    val webImageUrl: String,
    val largeImageUrl: String
)