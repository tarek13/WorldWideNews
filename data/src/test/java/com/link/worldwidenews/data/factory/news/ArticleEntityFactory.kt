package com.link.worldwidenews.data.factory.news

import com.link.worldwidenews.data.entity.news.ArticleEntity
import com.link.worldwidenews.domain.model.news.ArticleModel

object ArticleEntityFactory {
    fun generateDummyArticleEntity(): ArticleEntity {
        return ArticleEntity(
            1,
            "author",
            "description",
            "publishedAt",
            "title",
            "url",
            "urlToImage"
        )
    }

    fun generateDummyArticleDomain(): ArticleModel {
        return ArticleModel(
            1,
            "author",
            "description",
            "publishedAt",
            "title",
            "url",
            "urlToImage"
        )
    }
}