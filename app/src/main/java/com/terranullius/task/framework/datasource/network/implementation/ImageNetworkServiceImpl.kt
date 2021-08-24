package com.terranullius.task.framework.datasource.network.implementation

import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.framework.datasource.network.abstraction.ImageNetworkService
import com.terranullius.task.framework.datasource.network.mappers.NetworkMapper
import com.terranullius.task.framework.datasource.network.mappers.toImageList
import javax.inject.Inject


class ImageNetworkServiceImpl @Inject constructor(
    private val networkMapper: NetworkMapper,
    private val apiService: ApiService
) : ImageNetworkService {

    override suspend fun searchImages(searchString: String): List<Image> {
        return apiService.searchImages(searchString = searchString).imageDtoList.toImageList()
    }
}