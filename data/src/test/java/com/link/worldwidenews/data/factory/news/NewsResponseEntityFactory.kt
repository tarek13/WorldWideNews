package com.link.worldwidenews.data.factory.news

import com.link.worldwidenews.data.entity.news.NewsResponseEntity


object NewsResponseEntityFactory {
    fun generateDummyNewsResponseEntity(): NewsResponseEntity {
        return NewsResponseEntity(
            listOf(ArticleEntityFactory.generateDummyArticleEntity()),
            "sortBy",
            "source",
            "status"
        )
    }

}