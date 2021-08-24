package com.terranullius.task.framework.datasource.network.mappers

import com.terranullius.task.business.domain.model.Image
import com.terranullius.task.business.domain.util.EntityMapper
import com.terranullius.task.framework.datasource.network.model.ImageDto

/**
 * Class to map ImageDto to Image domain model and vice-versa
 * */

class NetworkMapper: EntityMapper<ImageDto, Image> {

    override fun mapFromEntity(entity: ImageDto): Image {

        return Image(
            id = entity.id,
            likes = entity.likes,
            pageUrl = entity.pageUrl,
            previewUrl = entity.previewUrl,
            artist = entity.artist,
            views = entity.views,
            webImageUrl = entity.webImageUrl,
            largeImageUrl = entity.largeImageUrl
            )

    }

    override fun mapToEntity(domainModel: Image): ImageDto {
            return ImageDto(
                id = domainModel.id,
                likes = domainModel.likes,
                pageUrl = domainModel.pageUrl,
                previewUrl = domainModel.previewUrl,
                artist = domainModel.artist,
                views = domainModel.views,
                webImageUrl = domainModel.webImageUrl,
                largeImageUrl = domainModel.largeImageUrl
            )
    }
}

fun List<ImageDto>.toImageList() = this.map {
    Image(
        id = it.id,
        likes = it.likes,
        pageUrl = it.pageUrl,
        previewUrl = it.previewUrl,
        artist = it.artist,
        views = it.views,
        webImageUrl = it.webImageUrl,
        largeImageUrl = it.largeImageUrl
    )
}

fun List<Image>.toImageDtoList() = this.map {
    ImageDto(
        id = it.id,
        likes = it.likes,
        pageUrl = it.pageUrl,
        previewUrl = it.previewUrl,
        artist = it.artist,
        views = it.views,
        webImageUrl = it.webImageUrl,
        largeImageUrl = it.largeImageUrl
    )
}