package com.link.worldwidenews.domain.repository

import com.link.worldwidenews.domain.model.news.ArticleModel
import io.reactivex.Completable
import io.reactivex.Single

interface IRoomDBRepository {
    fun saveArticles(listArticles: List<ArticleModel?>): Completable
    fun getArticles(): Single<List<ArticleModel?>>
    fun isCached(): Single<Boolean>
    fun clearArticles(): Completable
    fun setLastCacheTime(lastCache: Long)
    fun isExpired(): Boolean
}