package com.terranullius.task.business.data.network.implementation

import com.terranullius.task.business.data.network.abstraction.ImageNetworkDataSource
import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.framework.datasource.network.abstraction.ImageNetworkService


class ImageNetworkDataSourceImpl(private val imageNetworkService: ImageNetworkService): ImageNetworkDataSource{

    override suspend fun getAllImages(): List<Image> {
       return imageNetworkService.getAllImages()
    }
}