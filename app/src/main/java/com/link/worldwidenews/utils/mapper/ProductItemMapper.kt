package com.link.worldwidenews.utils.mapper


import com.link.worldwidenews.domain.model.news.ArticleModel
import com.link.worldwidenews.model.news.Article

fun ArticleModel?.mapToView(): Article? {
    return this?.run {
        Article(
            author,
            description,
            publishedAt,
            title,
            url,
            urlToImage,
        )
    }
}

fun List<ArticleModel?>?.mapToViewList(): List<Article?> {
    val viewList = mutableListOf<Article?>()
    this?.forEach { model ->
        viewList.add(model.mapToView())
    }
    return viewList
}

