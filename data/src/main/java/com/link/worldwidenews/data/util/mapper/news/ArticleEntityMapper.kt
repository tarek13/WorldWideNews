package com.link.worldwidenews.data.util.mapper.news

import com.link.worldwidenews.data.entity.news.ArticleEntity
import com.link.worldwidenews.domain.model.news.ArticleModel


fun ArticleEntity?.mapToDomain(): ArticleModel? {
    return this?.run {
        ArticleModel(
            id,
            author,
            description,
            publishedAt,
            title,
            url,
            urlToImage,
        )
    }
}

fun List<ArticleEntity?>?.mapToDomainList(): List<ArticleModel?> {
    val domainList = mutableListOf<ArticleModel?>()
    this?.forEach { entity ->
        domainList.add(entity.mapToDomain())
    }
    return domainList
}

fun ArticleModel?.mapToEntity(): ArticleEntity? {
    return this?.run {
        ArticleEntity(
            id,
            author,
            description,
            publishedAt,
            title,
            url,
            urlToImage,
        )
    }
}

fun List<ArticleModel?>?.mapToEntityList(): List<ArticleEntity?> {
    val entityList = mutableListOf<ArticleEntity?>()
    this?.forEach { entity ->
        entityList.add(entity.mapToEntity())
    }
    return entityList
}