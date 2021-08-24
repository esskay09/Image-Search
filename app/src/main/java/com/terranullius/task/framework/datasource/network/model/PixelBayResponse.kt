package com.terranullius.task.framework.datasource.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PixelBayResponse(
    @Json(name = "hits") val imageDtoList: List<ImageDto>
)