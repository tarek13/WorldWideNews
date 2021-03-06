package com.link.worldwidenews.domain.model.news

data class ArticleModel(
    var author: String?,
    var description: String?,
    var publishedAt: String?,
    var title: String?,
    var url: String?,
    var urlToImage: String?
)