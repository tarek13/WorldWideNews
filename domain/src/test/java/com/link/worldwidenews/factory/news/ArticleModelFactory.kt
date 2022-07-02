package com.link.worldwidenews.factory.news

import com.link.worldwidenews.domain.model.news.ArticleModel


object ArticleModelFactory {
    fun generateDummyArticleDomain(): ArticleModel {
        return ArticleModel(
            "author",
            "description",
            "publishedAt",
            "title",
            "url",
            "urlToImage"
        )
    }
}