package com.terranullius.task.framework.datasource.network.implementation

import com.terranullius.task.framework.datasource.network.model.ImageDto
import com.terranullius.task.framework.datasource.network.model.PixelBayResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun searchImages(
        @Query("key") key: String = "23053573-5c2c92d83274d064e4e19a789",
        @Query("image_type") type: String = "photo",
        @Query("orientation") orientation: String = "horizontal",
        @Query("q") searchString: String
    ) : PixelBayResponse

}