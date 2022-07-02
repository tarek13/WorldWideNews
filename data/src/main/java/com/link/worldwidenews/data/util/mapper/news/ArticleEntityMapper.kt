package com.link.worldwidenews.data.util.mapper.news

import com.link.worldwidenews.data.entity.news.ArticleEntity
import com.link.worldwidenews.domain.model.news.ArticleModel


fun ArticleEntity?.mapToDomain(): ArticleModel? {
    return this?.run {
        ArticleModel(
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
