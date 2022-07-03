package com.link.worldwidenews.factory.news

import com.link.worldwidenews.domain.model.news.ArticleModel
import com.link.worldwidenews.model.news.Article

object ArticleFactory {
    fun generateDummyArticle(): Article {
        return Article(
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

    fun generateDummyArticleWithDifferentTitle(): Article {
        return Article(
            1,
            "author",
            "description",
            "publishedAt",
            "tarek",
            "url",
            "urlToImage"
        )
    }
}