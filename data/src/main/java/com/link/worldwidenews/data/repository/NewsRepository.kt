package com.link.worldwidenews.data.repository

import com.link.worldwidenews.data.entity.news.ArticleEntity
import com.link.worldwidenews.data.source.remote.NewsApis
import com.link.worldwidenews.data.util.HandleApiErrors
import com.link.worldwidenews.data.util.mapper.error.mapToDomain
import com.link.worldwidenews.data.util.mapper.news.mapToDomainList
import com.link.worldwidenews.domain.model.ErrorResponseModel
import com.link.worldwidenews.domain.model.news.ArticleModel
import com.link.worldwidenews.domain.repository.INewsRepository
import com.link.worldwidenews.domain.util.AppConstants
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class NewsRepository @Inject constructor(
    private val newsApis: NewsApis,
    @param:Named("current_language")
    private val currentLanguage: String,
    private val handleApiErrors: HandleApiErrors,
    private val dbRepository: RoomDBRepository
) : INewsRepository {

    override fun getNews(): Single<List<ArticleModel?>?> {

        return dbRepository.isCached()
            .flatMap { isCached ->
                val isExpired=dbRepository.isExpired()
                if (isCached && !isExpired) {
                    dbRepository.getArticles()
                } else {
                    getNewsRemotely().flatMap {
                        dbRepository.saveArticles(it).doOnComplete{
                            dbRepository.setLastCacheTime(System.currentTimeMillis())
                        }.toSingle { it }
                    }
                }
            }
    }

    fun getNewsRemotely() :Single<List<ArticleModel?>?>{
        return Single.zip(
            newsApis.getNews("associated-press", AppConstants.API_KEY),
            newsApis.getNews("the-next-web", AppConstants.API_KEY)
        ) { associatedPressNews, theNextWebNews ->
            val result = mutableListOf<ArticleEntity?>()
            result.apply {
                associatedPressNews.articles?.toMutableList()?.let {
                    result.addAll(it)
                }
                theNextWebNews.articles?.toMutableList()?.let {
                    result.addAll(it)
                }
            }
        }.map { articles ->
            articles.mapToDomainList()
        }
    }

    override fun handleLoginErrorResponse(errorBody: String?): ErrorResponseModel? {
        return handleApiErrors.handleErrorResponse(errorBody).mapToDomain()
    }
}